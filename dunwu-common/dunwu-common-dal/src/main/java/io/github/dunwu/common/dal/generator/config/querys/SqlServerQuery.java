package io.github.dunwu.common.dal.generator.config.querys;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * SQL Server 数据库表数据查询实现
 * <p>
 * 提供了针对 SQL Server 数据库的元数据查询 SQL 及字段映射，
 * 包含对自增主键的特殊处理。
 *
 * @author hubin
 * @since 2018-01-16
 */
public class SqlServerQuery extends AbstractDbQuery {

    /**
     * 获取查询所有表的 SQL 语句
     *
     * @return 查询表的 SQL
     */
    @Override
    public String tablesSql() {
        return "select cast(so.name as varchar(500)) as TABLE_NAME, " +
            "cast(sep.value as varchar(500)) as COMMENTS from sysobjects so " +
            "left JOIN sys.extended_properties sep on sep.major_id=so.id and sep.minor_id=0 " +
            "where (xtype='U' or xtype='v')";
    }

    /**
     * 获取查询指定表字段信息的 SQL 语句
     *
     * @return 查询字段的 SQL
     */
    @Override
    public String tableFieldsSql() {
        return "SELECT  cast(a.name AS VARCHAR(500)) AS TABLE_NAME,cast(b.name AS VARCHAR(500)) AS COLUMN_NAME, "
            + "cast(c.VALUE AS NVARCHAR(500)) AS COMMENTS,cast(sys.types.name AS VARCHAR (500)) AS DATA_TYPE,"
            + "(" + " SELECT CASE count(1) WHEN 1 then 'PRI' ELSE '' END"
            + " FROM syscolumns,sysobjects,sysindexes,sysindexkeys,systypes "
            + " WHERE syscolumns.xusertype = systypes.xusertype AND syscolumns.id = object_id (a.name) AND sysobjects.xtype = 'PK'"
            + " AND sysobjects.parent_obj = syscolumns.id " + " AND sysindexes.id = syscolumns.id "
            + " AND sysobjects.name = sysindexes.name AND sysindexkeys.id = syscolumns.id "
            + " AND sysindexkeys.indid = sysindexes.indid "
            + " AND syscolumns.colid = sysindexkeys.colid AND syscolumns.name = b.name) as 'KEY',"
            + "  b.is_identity isIdentity "
            + " FROM ( select name,object_id from sys.tables UNION all select name,object_id from sys.views ) a "
            + " INNER JOIN sys.columns b ON b.object_id = a.object_id "
            + " LEFT JOIN sys.types ON b.user_type_id = sys.types.user_type_id   "
            + " LEFT JOIN sys.extended_properties c ON c.major_id = b.object_id AND c.minor_id = b.column_id "
            + " WHERE a.name = '%s' and sys.types.name !='sysname' ";
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
     * @return 列名 "COMMENTS"
     */
    @Override
    public String tableComment() {
        return "COMMENTS";
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
     * @return 列名 "DATA_TYPE"
     */
    @Override
    public String fieldType() {
        return "DATA_TYPE";
    }

    /**
     * 获取结果集中字段注释对应的列名
     *
     * @return 列名 "COMMENTS"
     */
    @Override
    public String fieldComment() {
        return "COMMENTS";
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
     * 判断当前字段是否为自增主键
     *
     * @param results 查询结果集
     * @return 如果是自增主键返回 true，否则返回 false
     * @throws SQLException 如果发生数据库访问错误
     */
    @Override
    public boolean isKeyIdentity(ResultSet results) throws SQLException {
        return 1 == results.getInt("isIdentity");
    }

}
