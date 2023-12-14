package io.github.dunwu.tool.data.mybatis;

import lombok.Data;

/**
 * Dao 信息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-12-14
 */
@Data
public class DaoInfo {

    private String schemaName;

    private String tableName;

    private String description;

    private Class<?> entityClass;

    private String entityClassName;

    private Class<?> daoClass;

    private String daoClassName;

    private Class<?> mapperClass;

    private String mapperClassName;

}
