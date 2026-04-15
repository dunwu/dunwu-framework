package io.github.dunwu.common.dal.mybatis;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.Assert;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import com.baomidou.mybatisplus.core.toolkit.GlobalConfigUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.reflection.ExceptionUtil;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.MyBatisExceptionTranslator;
import org.mybatis.spring.SqlSessionHolder;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * DAO 基础实现类
 * <p>
 * 实现了 {@link IDao} 接口，提供了通用的 CRUD 操作及批量处理功能。
 * 泛型参数：
 * <ul>
 * <li>M: Mapper 接口类型，继承自 {@link BaseMapper}</li>
 * <li>T: 实体类型</li>
 * </ul>
 *
 * @author hubin
 * @since 2018-06-23
 */
@SuppressWarnings("unchecked")
public class DaoImpl<M extends BaseMapper<T>, T> implements IDao<T> {

    /**
     * 日志记录器
     */
    protected Log log = LogFactory.getLog(getClass());

    /**
     * Mapper 实例，由 Spring 自动注入
     */
    @Autowired
    protected M baseMapper;

    /**
     * 实体类 Class 对象
     */
    protected Class<?> entityClass = currentModelClass();

    /**
     * 获取 Mapper 实例
     *
     * @return Mapper 实例
     */
    @Override
    public M getBaseMapper() {
        return baseMapper;
    }

    /**
     * 判断数据库操作是否成功
     *
     * @param result 数据库操作返回影响条数
     * @return 如果 result 大于 0 返回 true，否则返回 false
     * @deprecated 3.3.1 请使用 {@link SqlHelper#retBool(Integer)}
     */
    @Deprecated
    protected boolean retBool(Integer result) {
        return SqlHelper.retBool(result);
    }

    /**
     * 通过反射获取实体对象指定字段的值
     * <p>
     * 支持从当前类及父类中查找字段。
     *
     * @param entity    实体对象
     * @param fieldName 字段名称
     * @return 字段值
     * @throws RuntimeException 如果字段不存在或无法访问
     */
    private static Object getFieldValue(Object entity, String fieldName) {
        try {
            java.lang.reflect.Field field = entity.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(entity);
        } catch (NoSuchFieldException e) {
            // 尝试从父类查找
            Class<?> clazz = entity.getClass().getSuperclass();
            while (clazz != null && clazz != Object.class) {
                try {
                    java.lang.reflect.Field field = clazz.getDeclaredField(fieldName);
                    field.setAccessible(true);
                    return field.get(entity);
                } catch (NoSuchFieldException | IllegalAccessException ex) {
                    clazz = clazz.getSuperclass();
                }
            }
            throw new RuntimeException("Field not found: " + fieldName, e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Cannot access field: " + fieldName, e);
        }
    }

    /**
     * 解析当前类泛型中的实体类型
     *
     * @return 实体类型的 Class 对象
     * @throws IllegalStateException 如果无法解析实体类型
     */
    protected Class<T> currentModelClass() {
        java.lang.reflect.Type superClass = getClass().getGenericSuperclass();
        if (superClass instanceof java.lang.reflect.ParameterizedType) {
            java.lang.reflect.Type[] types = ((java.lang.reflect.ParameterizedType) superClass).getActualTypeArguments();
            if (types.length > 1) {
                return (Class<T>) types[1];
            }
        }
        throw new IllegalStateException("Cannot resolve entity type for " + getClass().getName());
    }

    /**
     * 获取批量操作的 SqlSession
     *
     * @return 批量 SqlSession
     * @deprecated 3.3.0 请使用 {@link SqlHelper#sqlSessionBatch(Class)}
     */
    @Deprecated
    protected SqlSession sqlSessionBatch() {
        return SqlHelper.sqlSessionBatch(entityClass);
    }

    /**
     * 释放 SqlSession
     *
     * @param sqlSession 需要释放的 session
     * @deprecated 3.3.0 请使用 {@link SqlSessionUtils#closeSqlSession(SqlSession, SqlSessionFactory)}
     */
    @Deprecated
    protected void closeSqlSession(SqlSession sqlSession) {
        SqlSessionUtils.closeSqlSession(sqlSession, GlobalConfigUtils.currentSessionFactory(entityClass));
    }

    /**
     * 获取指定 SQL 方法的完整语句标识
     *
     * @param sqlMethod SQL 方法枚举
     * @return 完整的 SQL 语句标识（如 "xxx.insert"）
     */
    protected String sqlStatement(SqlMethod sqlMethod) {
        return SqlHelper.table(entityClass).getSqlStatement(sqlMethod.getMethod());
    }

    /**
     * 批量插入实体
     *
     * @param entityList 实体集合
     * @param batchSize  批次大小
     * @return 如果执行成功返回 true，否则返回 false
     */
    @Override
    public boolean insertBatch(Collection<T> entityList, int batchSize) {
        String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> sqlSession.insert(sqlStatement, entity));
    }

    /**
     * 保存实体：如果 ID 为空或不存在则插入，否则更新
     *
     * @param entity 实体对象
     * @return 如果执行成功返回 true，否则返回 false
     */
    @Override
    public boolean save(T entity) {
        if (null != entity) {
            Class<?> cls = entity.getClass();
            TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
            Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
            String keyProperty = tableInfo.getKeyProperty();
            Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
            Object idVal = getFieldValue(entity, tableInfo.getKeyProperty());
            if (Objects.isNull(getById((Serializable) idVal))) {
                return SqlHelper.retBool(getBaseMapper().insert(entity));
            } else {
                return SqlHelper.retBool(getBaseMapper().updateById(entity));
            }
        }
        return false;
    }

    /**
     * 批量保存实体：根据 ID 判断执行插入或更新
     *
     * @param entityList 实体集合
     * @param batchSize  批次大小
     * @return 如果执行成功返回 true，否则返回 false
     */
    @Override
    public boolean saveBatch(Collection<T> entityList, int batchSize) {
        TableInfo tableInfo = TableInfoHelper.getTableInfo(entityClass);
        Assert.notNull(tableInfo, "error: can not execute. because can not find cache of TableInfo for entity!");
        String keyProperty = tableInfo.getKeyProperty();
        Assert.notEmpty(keyProperty, "error: can not execute. because can not find column for id from entity!");
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            Object idVal = getFieldValue(entity, keyProperty);
            if (StringUtils.checkValNull(idVal) || Objects.isNull(getById((Serializable) idVal))) {
                sqlSession.insert(tableInfo.getSqlStatement(SqlMethod.INSERT_ONE.getMethod()), entity);
            } else {
                MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
                param.put(Constants.ENTITY, entity);
                sqlSession.update(tableInfo.getSqlStatement(SqlMethod.UPDATE_BY_ID.getMethod()), param);
            }
        });
    }

    /**
     * 根据 ID 批量更新实体
     *
     * @param entityList 实体集合
     * @param batchSize  批次大小
     * @return 如果执行成功返回 true，否则返回 false
     */
    @Override
    public boolean updateBatchById(Collection<T> entityList, int batchSize) {
        String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
        return executeBatch(entityList, batchSize, (sqlSession, entity) -> {
            MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
            param.put(Constants.ENTITY, entity);
            sqlSession.update(sqlStatement, param);
        });
    }

    /**
     * 根据 Wrapper 条件查询单条记录
     *
     * @param queryWrapper 查询条件包装器
     * @param throwEx      如果查询结果多于一条是否抛出异常
     * @return 查询到的实体对象，如果未找到返回 null
     */
    @Override
    public T getOne(Wrapper<T> queryWrapper, boolean throwEx) {
        if (throwEx) {
            return baseMapper.selectOne(queryWrapper);
        }
        return SqlHelper.getObject(log, baseMapper.selectList(queryWrapper));
    }

    /**
     * 根据 Wrapper 条件查询单条记录并返回 Map
     *
     * @param queryWrapper 查询条件包装器
     * @return 查询到的 Map 对象，如果未找到返回 null
     */
    @Override
    public Map<String, Object> getMap(Wrapper<T> queryWrapper) {
        return SqlHelper.getObject(log, baseMapper.selectMaps(queryWrapper));
    }

    /**
     * 根据 Wrapper 条件查询单条记录并应用转换函数
     *
     * @param queryWrapper 查询条件包装器
     * @param mapper       结果转换函数
     * @param <V>          返回值类型
     * @return 转换后的结果对象
     */
    @Override
    public <V> V getObj(Wrapper<T> queryWrapper, Function<? super Object, V> mapper) {
        return SqlHelper.getObject(log, listObjs(queryWrapper, mapper));
    }

    /**
     * 执行批量操作（使用 Consumer）
     *
     * @param consumer 执行逻辑
     * @return 如果执行成功返回 true，否则返回 false
     * @deprecated 3.3.1 后续版本可能移除，请使用 {@link #executeBatch(Collection, int, BiConsumer)}
     */
    @Deprecated
    protected boolean executeBatch(Consumer<SqlSession> consumer) {
        SqlSessionFactory sqlSessionFactory = SqlHelper.sqlSessionFactory(entityClass);
        SqlSessionHolder sqlSessionHolder =
            (SqlSessionHolder) TransactionSynchronizationManager.getResource(sqlSessionFactory);
        boolean transaction = TransactionSynchronizationManager.isSynchronizationActive();
        if (sqlSessionHolder != null) {
            SqlSession sqlSession = sqlSessionHolder.getSqlSession();
            // 原生无法支持执行器切换，当存在批量操作时，会嵌套两个 session 的，优先 commit 上一个 session
            // 按道理来说，这里的值应该一直为 false。
            sqlSession.commit(!transaction);
        }
        SqlSession sqlSession = sqlSessionFactory.openSession(ExecutorType.BATCH);
        if (!transaction) {
            log.warn("SqlSession ["
                + sqlSession
                + "] was not registered for synchronization because DataSource is not transactional");
        }
        try {
            consumer.accept(sqlSession);
            // 非事务情况下，强制 commit。
            sqlSession.commit(!transaction);
            return true;
        } catch (Throwable t) {
            sqlSession.rollback();
            Throwable unwrapped = ExceptionUtil.unwrapThrowable(t);
            if (unwrapped instanceof RuntimeException) {
                MyBatisExceptionTranslator myBatisExceptionTranslator
                    = new MyBatisExceptionTranslator(
                    sqlSessionFactory.getConfiguration().getEnvironment().getDataSource(), true);
                throw Objects.requireNonNull(
                    myBatisExceptionTranslator.translateExceptionIfPossible((RuntimeException) unwrapped));
            }
            throw ExceptionUtils.mpe(unwrapped);
        } finally {
            sqlSession.close();
        }
    }

    /**
     * 执行批量操作（使用 BiConsumer）
     *
     * @param list      数据集合
     * @param batchSize 批量大小
     * @param consumer  执行逻辑，接收 SqlSession 和当前元素
     * @param <E>       元素类型
     * @return 如果执行成功返回 true，否则返回 false
     * @since 3.3.1
     */
    protected <E> boolean executeBatch(Collection<E> list, int batchSize, BiConsumer<SqlSession, E> consumer) {
        Assert.isFalse(batchSize < 1, "batchSize must not be less than one");
        return !CollectionUtils.isEmpty(list) && executeBatch(sqlSession -> {
            int size = list.size();
            int i = 1;
            for (E element : list) {
                consumer.accept(sqlSession, element);
                if ((i % batchSize == 0) || i == size) {
                    sqlSession.flushStatements();
                }
                i++;
            }
        });
    }

    /**
     * 执行批量操作（默认批次提交数量）
     *
     * @param list     数据集合
     * @param consumer 执行逻辑
     * @param <E>      元素类型
     * @return 如果执行成功返回 true，否则返回 false
     * @since 3.3.1
     */
    protected <E> boolean executeBatch(Collection<E> list, BiConsumer<SqlSession, E> consumer) {
        return executeBatch(list, DEFAULT_BATCH_SIZE, consumer);
    }

}
