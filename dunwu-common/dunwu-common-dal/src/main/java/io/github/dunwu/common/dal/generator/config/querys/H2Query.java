package io.github.dunwu.common.dal.generator.config.querys;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * H2 数据库元数据查询实现类。
 * <p>
 * 提供了针对 H2 数据库的表及字段元数据查询 SQL 语句，
 * 以及结果集中各列名的映射定义。
 *
 * @author yuxiaobin
 * @since 2019-01-8
 */
public class H2Query extends AbstractDbQuery {

    /**
     * 查询主键信息的 SQL 模板
     */
    public static final String PK_QUERY_SQL = "select * from INFORMATION_SCHEMA.INDEXES WHERE TABLE_NAME = '%s'";

    /**
     * 获取查询所有表的 SQL 语句
     *
     * @return 查询表的 SQL 语句模板
     */
    @Override
    public String tablesSql() {
        return "SELECT * FROM INFORMATION_SCHEMA.TABLES WHERE 1=1 ";
    }

    /**
     * 获取查询指定表字段信息的 SQL 语句
     *
     * @return 查询表字段的 SQL 语句模板
     */
    @Override
    public String tableFieldsSql() {
        return "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME= '%s' ";
    }

    /**
     * 获取结果集中表名称对应的列名
     *
     * @return 列名 "TABLE_NAME"
     */
    @Override
    public String tableName() {
        return "TABLE_NAME";
    }

    /**
     * 获取结果集中表注释对应的列名
     *
     * @return 列名 "REMARKS"
     */
    @Override
    public String tableComment() {
        return "REMARKS";
    }

    /**
     * 获取结果集中字段名称对应的列名
     *
     * @return 列名 "COLUMN_NAME"
     */
    @Override
    public String fieldName() {
        return "COLUMN_NAME";
    }

    /**
     * 获取结果集中字段类型对应的列名
     *
     * @return 列名 "TYPE_NAME"
     */
    @Override
    public String fieldType() {
        return "TYPE_NAME";
    }

    /**
     * 获取结果集中字段注释对应的列名
     *
     * @return 列名 "REMARKS"
     */
    @Override
    public String fieldComment() {
        return "REMARKS";
    }

    /**
     * 获取结果集中主键字段对应的列名
     *
     * @return 列名 "PRIMARY_KEY"
     */
    @Override
    public String fieldKey() {
        return "PRIMARY_KEY";
    }

    /**
     * 判断主键是否为自增。
     * <p>
     * H2 数据库通过结果集中的 "Extra" 字段判断是否为 auto_increment。
     *
     * @param results 查询结果集
     * @return 如果是自增主键返回 true，否则返回 false
     * @throws SQLException 如果发生数据库访问异常
     */
    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return "auto_increment".equals(results.getString("Extra"));
    }

}
