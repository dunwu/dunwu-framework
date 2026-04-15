package io.github.dunwu.common.dal.generator.config;

import com.baomidou.mybatisplus.core.metadata.TableInfoHelper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import io.github.dunwu.common.dal.generator.config.po.LikeTable;
import io.github.dunwu.common.dal.generator.config.po.TableFill;
import io.github.dunwu.common.dal.generator.config.rules.NamingStrategy;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * 代码生成器策略配置
 * <p>
 * 用于配置数据库表到 Java 实体类及 Mapper、Service、Controller 等层的映射策略。
 * 支持命名策略、表/字段前缀处理、父类继承、Lombok 集成等功能。
 *
 * @author YangHu, tangguo, hubin
 * @since 2016/8/30
 */
@Data
@Accessors(chain = true)
public class StrategyConfig {

    /**
     * 是否开启大写命名模式
     * <p>
     * 如果开启，字段名和表名中的大写字母将被特殊处理
     */
    private boolean isCapitalMode = false;

    /**
     * 是否跳过视图
     * <p>
     * 如果开启，代码生成时将忽略数据库视图，仅处理表
     */
    private boolean skipView = false;

    /**
     * 名称转换接口
     * <p>
     * 用于自定义表名、字段名到类名、属性名的转换逻辑
     */
    private INameConvert nameConvert;

    /**
     * 数据库表映射到实体的命名策略
     * <p>
     * 默认为 {@link NamingStrategy#underline_to_camel}（下划线转驼峰）
     */
    private NamingStrategy naming = NamingStrategy.underline_to_camel;

    /**
     * 数据库表字段映射到实体的命名策略
     * <p>
     * 如果未指定，则默认跟随 {@link #naming} 策略执行
     */
    private NamingStrategy columnNaming = NamingStrategy.underline_to_camel;

    /**
     * 表前缀列表
     * <p>
     * 用于在生成实体类时去除表名前缀，例如 "sys_" -> "Sys"
     */
    @Setter(AccessLevel.NONE)
    private String[] tablePrefix;

    /**
     * 字段前缀列表
     * <p>
     * 用于在生成实体属性时去除字段名前缀
     */
    @Setter(AccessLevel.NONE)
    private String[] fieldPrefix;

    /**
     * 自定义继承的 Entity 类全限定名
     * <p>
     * 如果设置，生成的实体类将继承该类
     */
    @Setter(AccessLevel.NONE)
    private String superEntityClass;

    /**
     * 自定义基础的 Entity 类中的公共字段列表
     * <p>
     * 用于标识哪些字段已经在父类中定义，生成时不再重复生成
     */
    @Setter(AccessLevel.NONE)
    private String[] superEntityColumns;

    /**
     * 自定义继承的 Mapper 接口全限定名
     * <p>
     * 默认为 {@link ConstVal#SUPER_MAPPER_CLASS}
     */
    private String superMapperClass = ConstVal.SUPER_MAPPER_CLASS;

    /**
     * 自定义继承的 Dao 接口全限定名
     * <p>
     * 默认为 {@link ConstVal#SUPER_DAO_CLASS}
     */
    private String superDaoClass = ConstVal.SUPER_DAO_CLASS;

    /**
     * 自定义继承的 Dao 实现类全限定名
     * <p>
     * 默认为 {@link ConstVal#SUPER_DAO_IMPL_CLASS}
     */
    private String superDaoImplClass = ConstVal.SUPER_DAO_IMPL_CLASS;

    /**
     * 自定义继承的 Service 接口全限定名
     * <p>
     * 默认为 {@link ConstVal#SUPER_SERVICE_CLASS}
     */
    private String superServiceClass = ConstVal.SUPER_SERVICE_CLASS;

    /**
     * 自定义继承的 Service 实现类全限定名
     * <p>
     * 默认为 {@link ConstVal#SUPER_SERVICE_IMPL_CLASS}
     */
    private String superServiceImplClass = ConstVal.SUPER_SERVICE_IMPL_CLASS;

    /**
     * 自定义继承的 Controller 类全限定名
     * <p>
     * 如果设置，生成的 Controller 类将继承该类，可用于统一处理请求或响应格式。
     */
    private String superControllerClass;

    /**
     * 需要包含的表名列表
     * <p>
     * 与 {@link #exclude} 互斥，仅生成列表中的表对应的代码
     *
     * @since 3.3.0 正则匹配不再支持，请使用 {@link #setLikeTable(LikeTable)}
     */
    @Setter(AccessLevel.NONE)
    private String[] include = null;

    /**
     * 需要排除的表名列表
     * <p>
     * 与 {@link #include} 互斥，忽略列表中的表
     *
     * @since 3.3.0 正则匹配不再支持，请使用 {@link #setNotLikeTable(LikeTable)}
     */
    @Setter(AccessLevel.NONE)
    private String[] exclude = null;

    /**
     * 实体类是否生成 serialVersionUID
     */
    private boolean entitySerialVersionUID = true;

    /**
     * 实体类是否生成字段常量
     * <p>
     * 例如：public static final String ID = "test_id";
     */
    private boolean entityColumnConstant = false;

    /**
     * 实体类是否使用构建者模型（Builder Pattern）
     * <p>
     * 例如：public User setName(String name) { this.name = name; return this; }
     */
    private boolean entityBuilderModel = true;

    /**
     * 实体类是否使用 Lombok 模型
     * <p>
     * 如果开启，将生成 @Data, @Builder 等 Lombok 注解
     */
    private boolean entityLombokModel = true;

    /**
     * Boolean 类型字段是否移除 is 前缀
     * <p>
     * 例如：数据库字段 is_deleted -> 实体属性 deleted
     */
    private boolean entityBooleanColumnRemoveIsPrefix = true;

    /**
     * 是否生成 @RestController 控制器
     * <p>
     * 如果开启，生成的 Controller 将使用 @RestController 替代 @Controller
     */
    private boolean restControllerStyle = true;

    /**
     * 控制器映射路径是否使用连字符风格
     * <p>
     * 例如：@RequestMapping("/managerUserActionHistory") -> @RequestMapping("/manager-user-action-history")
     */
    private boolean controllerMappingHyphenStyle = false;

    /**
     * 生成实体时是否生成字段注解
     * <p>
     * 例如：@TableField("user_name")
     */
    private boolean entityTableFieldAnnotationEnable = true;

    /**
     * 乐观锁字段名称
     * <p>
     * 用于在实体类中生成 {@link com.baomidou.mybatisplus.annotation.Version} 注解，支持乐观锁机制。
     */
    private String versionFieldName;

    /**
     * 逻辑删除字段名称
     * <p>
     * 用于在实体类中生成 {@link com.baomidou.mybatisplus.annotation.TableLogic} 注解，支持逻辑删除机制。
     */
    private String logicDeleteFieldName;

    /**
     * 表填充字段列表
     * <p>
     * 用于配置自动填充的字段，如 create_time, update_time 等
     */
    private List<TableFill> tableFillList = null;

    /**
     * 是否启用 SQL 过滤
     * <p>
     * 如果数据库不支持特定的 SQL 过滤语法（如某些微软系数据库），可考虑关闭此开关。
     * 关闭后将使用内存过滤方式。
     *
     * @since 3.3.1
     */
    private boolean enableSqlFilter = true;

    /**
     * 包含表名匹配规则
     *
     * @since 3.3.0
     */
    private LikeTable likeTable;

    /**
     * 排除表名匹配规则
     *
     * @since 3.3.0
     */
    private LikeTable notLikeTable;

    /**
     * 判断是否为符合大写命名模式
     *
     * @param word 待判断的字符串
     * @return 如果开启大写模式且字符串符合大写命名规则则返回 true
     */
    public boolean isCapitalModeNaming(String word) {
        return isCapitalMode && StringUtils.isCapitalMode(word);
    }

    /**
     * 判断表名是否包含指定的前缀
     *
     * @param tableName 表名称
     * @return 如果包含前缀返回 true
     */
    public boolean containsTablePrefix(String tableName) {
        if (null != tableName) {
            String[] tps = getTablePrefix();
            if (null != tps) {
                return Arrays.stream(tps).anyMatch(tableName::contains);
            }
        }
        return false;
    }

    /**
     * 获取字段命名策略
     * <p>
     * 如果未设置字段命名策略，则默认返回表命名策略
     *
     * @return 字段命名策略
     */
    public NamingStrategy getColumnNaming() {
        if (null == columnNaming) {
            return naming;
        }
        return columnNaming;
    }

    /**
     * 设置表前缀
     *
     * @param tablePrefix 表前缀数组
     * @return 当前 StrategyConfig 实例
     */
    public StrategyConfig setTablePrefix(String... tablePrefix) {
        this.tablePrefix = tablePrefix;
        return this;
    }

    /**
     * 判断字段是否属于父类公共字段
     *
     * @param fieldName 字段名
     * @return 如果属于公共字段返回 true
     */
    public boolean includeSuperEntityColumns(String fieldName) {
        if (null != superEntityColumns) {
            // 公共字段判断忽略大小写【 部分数据库大小写不敏感 】
            return Arrays.stream(superEntityColumns).anyMatch(e -> e.equalsIgnoreCase(fieldName));
        }
        return false;
    }

    /**
     * 设置父类公共字段
     *
     * @param superEntityColumns 公共字段数组
     * @return 当前 StrategyConfig 实例
     */
    public StrategyConfig setSuperEntityColumns(String... superEntityColumns) {
        this.superEntityColumns = superEntityColumns;
        return this;
    }

    /**
     * 设置需要包含的表名
     *
     * @param include 表名数组
     * @return 当前 StrategyConfig 实例
     */
    public StrategyConfig setInclude(String... include) {
        this.include = include;
        return this;
    }

    /**
     * 设置需要排除的表名
     *
     * @param exclude 表名数组
     * @return 当前 StrategyConfig 实例
     */
    public StrategyConfig setExclude(String... exclude) {
        this.exclude = exclude;
        return this;
    }

    /**
     * 设置字段前缀
     *
     * @param fieldPrefixs 字段前缀数组
     * @return 当前 StrategyConfig 实例
     */
    public StrategyConfig setFieldPrefix(String... fieldPrefixs) {
        this.fieldPrefix = fieldPrefixs;
        return this;
    }

    /**
     * 设置自定义实体父类全限定名
     *
     * @param superEntityClass 父类全限定名
     * @return 当前 StrategyConfig 实例
     */
    public StrategyConfig setSuperEntityClass(String superEntityClass) {
        this.superEntityClass = superEntityClass;
        return this;
    }

    /**
     * 设置自定义实体父类 Class
     * <p>
     * 该设置会自动识别父类中的公共字段，无需再手动配置 {@link #superEntityColumns}。
     * 注意：字段策略配置必须在调用此方法之前完成。
     *
     * @param clazz 实体父类 Class
     * @return 当前 StrategyConfig 实例
     */
    public StrategyConfig setSuperEntityClass(Class<?> clazz) {
        return setSuperEntityClass(clazz, null);
    }

    /**
     * 设置自定义实体父类 Class 及字段命名策略
     * <p>
     * 该设置会自动识别父类中的公共字段。
     *
     * @param clazz        实体父类 Class
     * @param columnNaming 字段命名策略
     * @return 当前 StrategyConfig 实例
     */
    public StrategyConfig setSuperEntityClass(Class<?> clazz, NamingStrategy columnNaming) {
        if (null != columnNaming) {
            this.columnNaming = columnNaming;
        }
        this.superEntityClass = clazz.getName();
        convertSuperEntityColumns(clazz);
        return this;
    }

    /**
     * 通过反射获取父类属性并转换为公共字段列表
     *
     * @param clazz 实体父类 Class
     */
    protected void convertSuperEntityColumns(Class<?> clazz) {
        List<Field> fields = TableInfoHelper.getAllFields(clazz);
        this.superEntityColumns = fields.stream().map(field -> {
            if (null == columnNaming || columnNaming == NamingStrategy.no_change) {
                return field.getName();
            }
            return StringUtils.camelToUnderline(field.getName());
        }).distinct().toArray(String[]::new);
    }

    /**
     * 设置自定义 Controller 父类 Class
     *
     * @param clazz Controller 父类 Class
     * @return 当前 StrategyConfig 实例
     */
    public StrategyConfig setSuperControllerClass(Class<?> clazz) {
        this.superControllerClass = clazz.getName();
        return this;
    }

    /**
     * 设置自定义 Controller 父类全限定名
     *
     * @param superControllerClass 父类全限定名
     * @return 当前 StrategyConfig 实例
     */
    public StrategyConfig setSuperControllerClass(String superControllerClass) {
        this.superControllerClass = superControllerClass;
        return this;
    }

    /**
     * @deprecated 请使用 {@link #setEntityTableFieldAnnotationEnable(boolean)}
     */
    @Deprecated
    public StrategyConfig entityTableFieldAnnotationEnable(boolean isEnableAnnotation) {
        entityTableFieldAnnotationEnable = isEnableAnnotation;
        return this;
    }

}
