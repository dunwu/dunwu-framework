package io.github.dunwu.data.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.enums.SqlMethod;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.TableInfo;
import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.*;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import io.github.dunwu.core.*;
import io.github.dunwu.data.entity.BaseEntity;
import io.github.dunwu.data.util.PageUtil;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-06
 */
public class ServiceImpl<M extends BaseMapper<T>, T extends BaseEntity>
	implements IService<T> {

	protected Log log = LogFactory.getLog(getClass());

	@Autowired
	protected M baseMapper;

	@Override
	public M getBaseMapper() {
		return baseMapper;
	}

	@Override
	public DataResult<Integer> save(T entity) {
		boolean result = retBool(baseMapper.insert(entity));
		if (result) {
			return ResultUtil.successDataResult(entity.getId());
		} else {
			return ResultUtil.failDataResult(AppCode.ERROR_DB);
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResult saveBatch(Collection<T> entityList, int batchSize) {
		String sqlStatement = sqlStatement(SqlMethod.INSERT_ONE);
		try (SqlSession batchSqlSession = sqlSessionBatch()) {
			int i = 0;
			for (T anEntityList : entityList) {
				batchSqlSession.insert(sqlStatement, anEntityList);
				if (i >= 1 && i % batchSize == 0) {
					batchSqlSession.flushStatements();
				}
				i++;
			}
			batchSqlSession.flushStatements();
		}

		return ResultUtil.successBaseResult();
	}

	/**
	 * 获取 SqlStatement
	 *
	 * @param sqlMethod ignore
	 * @return ignore
	 */
	protected String sqlStatement(SqlMethod sqlMethod) {
		return SqlHelper.table(currentModelClass())
			.getSqlStatement(sqlMethod.getMethod());
	}

	/**
	 * 批量操作 SqlSession
	 */
	protected SqlSession sqlSessionBatch() {
		return SqlHelper.sqlSessionBatch(currentModelClass());
	}

	@Override
	public BaseResult removeById(Serializable id) {
		boolean result = SqlHelper.retBool(baseMapper.deleteById(id));
		if (result) {
			return ResultUtil.successBaseResult();
		} else {
			return ResultUtil.failBaseResult(AppCode.ERROR_DB);
		}
	}

	@Override
	public BaseResult removeByMap(Map<String, Object> columnMap) {
		Assert.notEmpty(columnMap, "error: columnMap must not be empty");
		boolean result = SqlHelper.retBool(baseMapper.deleteByMap(columnMap));
		if (result) {
			return ResultUtil.successBaseResult();
		} else {
			return ResultUtil.failBaseResult(AppCode.ERROR_DB);
		}
	}

	@Override
	public BaseResult remove(Wrapper<T> wrapper) {
		boolean result = SqlHelper.retBool(baseMapper.delete(wrapper));
		if (result) {
			return ResultUtil.successBaseResult();
		} else {
			return ResultUtil.failBaseResult(AppCode.ERROR_DB);
		}
	}

	@Override
	public BaseResult removeByIds(Collection<? extends Serializable> idList) {
		boolean result = SqlHelper.retBool(baseMapper.deleteBatchIds(idList));
		if (result) {
			return ResultUtil.successBaseResult();
		} else {
			return ResultUtil.failBaseResult(AppCode.ERROR_DB);
		}
	}

	@Override
	public BaseResult updateById(T entity) {
		boolean result = retBool(baseMapper.updateById(entity));
		if (result) {
			return ResultUtil.successBaseResult();
		} else {
			return ResultUtil.failBaseResult(AppCode.ERROR_DB);
		}
	}

	@Override
	public BaseResult update(T entity, Wrapper<T> updateWrapper) {
		boolean result = retBool(baseMapper.update(entity, updateWrapper));
		if (result) {
			return ResultUtil.successBaseResult();
		} else {
			return ResultUtil.failBaseResult(AppCode.ERROR_DB);
		}
	}

	/**
	 * 判断数据库操作是否成功
	 *
	 * @param result 数据库操作返回影响条数
	 * @return boolean
	 */
	protected boolean retBool(Integer result) {
		return SqlHelper.retBool(result);
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public BaseResult updateBatchById(Collection<T> entityList, int batchSize) {
		Assert.notEmpty(entityList, "error: entityList must not be empty");
		String sqlStatement = sqlStatement(SqlMethod.UPDATE_BY_ID);
		try (SqlSession batchSqlSession = sqlSessionBatch()) {
			int i = 0;
			for (T anEntityList : entityList) {
				MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
				param.put(Constants.ENTITY, anEntityList);
				batchSqlSession.update(sqlStatement, param);
				if (i >= 1 && i % batchSize == 0) {
					batchSqlSession.flushStatements();
				}
				i++;
			}
			batchSqlSession.flushStatements();
		}

		return ResultUtil.successBaseResult();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResult saveOrUpdate(T entity) {
		if (null != entity) {
			Class<?> cls = entity.getClass();
			TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
			Assert.notNull(tableInfo,
				"error: can not execute. because can not find cache of TableInfo for entity!");
			String keyProperty = tableInfo.getKeyProperty();
			Assert.notEmpty(keyProperty,
				"error: can not execute. because can not find column for id from entity!");
			Object idVal = ReflectionKit.getMethodValue(cls, entity,
				tableInfo.getKeyProperty());
			return StringUtils.checkValNull(idVal)
				|| Objects.isNull(getById((Serializable) idVal)) ? save(entity)
				: updateById(entity);
		}

		return ResultUtil.successBaseResult();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public BaseResult saveOrUpdateBatch(Collection<T> entityList, int batchSize) {
		Assert.notEmpty(entityList, "error: entityList must not be empty");
		Class<?> cls = currentModelClass();
		TableInfo tableInfo = TableInfoHelper.getTableInfo(cls);
		Assert.notNull(tableInfo,
			"error: can not execute. because can not find cache of TableInfo for entity!");
		String keyProperty = tableInfo.getKeyProperty();
		Assert.notEmpty(keyProperty,
			"error: can not execute. because can not find column for id from entity!");
		try (SqlSession batchSqlSession = sqlSessionBatch()) {
			int i = 0;
			for (T entity : entityList) {
				Object idVal = ReflectionKit.getMethodValue(cls, entity, keyProperty);
				if (StringUtils.checkValNull(idVal)
					|| Objects.isNull(getById((Serializable) idVal))) {
					batchSqlSession.insert(sqlStatement(SqlMethod.INSERT_ONE), entity);
				} else {
					MapperMethod.ParamMap<T> param = new MapperMethod.ParamMap<>();
					param.put(Constants.ENTITY, entity);
					batchSqlSession.update(sqlStatement(SqlMethod.UPDATE_BY_ID), param);
				}
				// 不知道以后会不会有人说更新失败了还要执行插入 😂😂😂
				if (i >= 1 && i % batchSize == 0) {
					batchSqlSession.flushStatements();
				}
				i++;
			}
			batchSqlSession.flushStatements();
		}

		return ResultUtil.successBaseResult();
	}

	@Override
	public DataResult<T> getById(Serializable id) {
		return ResultUtil.successDataResult(baseMapper.selectById(id));
	}

	@Override
	public DataListResult<T> listByIds(Collection<? extends Serializable> idList) {
		return ResultUtil.successDataListResult(baseMapper.selectBatchIds(idList));
	}

	@Override
	public DataListResult<T> listByMap(Map<String, Object> columnMap) {
		return ResultUtil.successDataListResult(baseMapper.selectByMap(columnMap));
	}

	@Override
	public DataResult<T> getOne(Wrapper<T> queryWrapper, boolean throwEx) {
		if (throwEx) {
			return ResultUtil.successDataResult(baseMapper.selectOne(queryWrapper));
		}
		T entity = SqlHelper.getObject(log, baseMapper.selectList(queryWrapper));
		return ResultUtil.successDataResult(entity);
	}

	@Override
	public DataResult<Map<String, Object>> getMap(Wrapper<T> queryWrapper) {
		Map<String, Object> map = SqlHelper.getObject(log,
			baseMapper.selectMaps(queryWrapper));
		return ResultUtil.successDataResult(map);
	}

	@Override
	public <V> DataResult<V> getObj(Wrapper<T> queryWrapper,
		Function<? super Object, V> mapper) {
		List<V> list = (List<V>) listObjs(queryWrapper, mapper).getData();
		V entity = SqlHelper.getObject(log, list);
		return ResultUtil.successDataResult(entity);
	}

	@Override
	public DataResult<Integer> count(Wrapper<T> queryWrapper) {
		return ResultUtil.successDataResult(
			SqlHelper.retCount(baseMapper.selectCount(queryWrapper)));
	}

	@Override
	public DataListResult<T> list(Wrapper<T> queryWrapper) {
		return ResultUtil.successDataListResult(baseMapper.selectList(queryWrapper));
	}

	@Override
	public PageResult<T> page(Pagination<T> pagination, Wrapper<T> queryWrapper) {
		IPage<T> resultPage = baseMapper
			.selectPage(PageUtil.transToMybatisPlusPage(pagination), queryWrapper);
		return ResultUtil.successPageResult(PageUtil.transToPagination(resultPage));
	}

	@Override
	public DataListResult<Map<String, Object>> listMaps(Wrapper<T> queryWrapper) {
		return ResultUtil.successDataListResult(baseMapper.selectMaps(queryWrapper));
	}

	@Override
	public <V> DataListResult<V> listObjs(Wrapper<T> queryWrapper,
		Function<? super Object, V> mapper) {
		List<V> list = baseMapper.selectObjs(queryWrapper).stream()
			.filter(Objects::nonNull).map(mapper).collect(Collectors.toList());
		return ResultUtil.successDataListResult(list);
	}

	@Override
	public PageResult<Map<String, Object>> pageMaps(Pagination<T> pagination,
		Wrapper<T> queryWrapper) {
		IPage<Map<String, Object>> resultPage = baseMapper.selectMapsPage(
			PageUtil.transToMybatisPlusPage(pagination), queryWrapper);
		return ResultUtil.successPageResult(PageUtil.transToPagination(resultPage));
	}

	/**
	 * 释放sqlSession
	 *
	 * @param sqlSession session
	 */
	protected void closeSqlSession(SqlSession sqlSession) {
		SqlSessionUtils.closeSqlSession(sqlSession,
			GlobalConfigUtils.currentSessionFactory(currentModelClass()));
	}

	protected Class<T> currentModelClass() {
		return (Class<T>) ReflectionKit.getSuperClassGenericType(getClass(), 1);
	}

}