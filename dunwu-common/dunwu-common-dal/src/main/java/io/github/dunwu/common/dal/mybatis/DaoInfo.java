package io.github.dunwu.common.dal.mybatis;

import lombok.Data;

/**
 * DAO 信息实体
 * <p>
 * 用于封装 DAO、Mapper 及其对应实体类和数据库表的元数据信息。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-12-14
 */
@Data
public class DaoInfo {

    /**
     * 数据库模式名（Schema Name）
     */
    private String schemaName;

    /**
     * 数据库表名
     */
    private String tableName;

    /**
     * 表描述或备注
     */
    private String description;

    /**
     * 实体类的 Class 对象
     */
    private Class<?> entityClass;

    /**
     * 实体类的全限定名
     */
    private String entityClassName;

    /**
     * DAO 接口的 Class 对象
     */
    private Class<?> daoClass;

    /**
     * DAO 接口的全限定名
     */
    private String daoClassName;

    /**
     * Mapper 接口的 Class 对象
     */
    private Class<?> mapperClass;

    /**
     * Mapper 接口的全限定名
     */
    private String mapperClassName;

}
