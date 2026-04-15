package io.github.dunwu.common.dal.generator.config.querys;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * MariaDB 数据库元数据查询实现类。
 * <p>
 * 提供了针对 MariaDB 数据库的表及字段元数据查询 SQL 语句，
 * 以及结果集中各列名的映射定义。由于 MariaDB 与 MySQL 高度兼容，其实现方式与 {@link MySqlQuery} 类似。
 *
 * @author hubin
 * @since 2018-01-16
 */
public class MariadbQuery extends AbstractDbQuery {

    /**
     * 获取查询所有表的 SQL 语句
     *
     * @return 查询表的 SQL 语句模板
     */
    @Override
    public String tablesSql() {
        return "show table status WHERE 1=1 ";
    }

    /**
     * 获取查询指定表字段信息的 SQL 语句
     *
     * @return 查询表字段的 SQL 语句模板
     */
    @Override
    public String tableFieldsSql() {
        return "show full fields from `%s`";
    }

    /**
     * 获取结果集中表名称对应的列名
     *
     * @return 列名 "NAME"
     */
    @Override
    public String tableName() {
        return "NAME";
    }

    /**
     * 获取结果集中表注释对应的列名
     *
     * @return 列名 "COMMENT"
     */
    @Override
    public String tableComment() {
        return "COMMENT";
    }

    /**
     * 获取结果集中字段名称对应的列名
     *
     * @return 列名 "FIELD"
     */
    @Override
    public String fieldName() {
        return "FIELD";
    }

    /**
     * 获取结果集中字段类型对应的列名
     *
     * @return 列名 "TYPE"
     */
    @Override
    public String fieldType() {
        return "TYPE";
    }

    /**
     * 获取结果集中字段注释对应的列名
     *
     * @return 列名 "COMMENT"
     */
    @Override
    public String fieldComment() {
        return "COMMENT";
    }

    /**
     * 获取结果集中主键字段对应的列名
     *
     * @return 列名 "KEY"
     */
    @Override
    public String fieldKey() {
        return "KEY";
    }

    /**
     * 判断主键是否为自增。
     * <p>
     * MariaDB 通过结果集中的 "Extra" 字段判断是否为 auto_increment。
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
