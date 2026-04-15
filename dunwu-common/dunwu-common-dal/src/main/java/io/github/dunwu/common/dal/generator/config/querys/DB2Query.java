package io.github.dunwu.common.dal.generator.config.querys;

/**
 * DB2 数据库元数据查询实现类。
 * <p>
 * 提供了针对 IBM DB2 数据库的表及字段元数据查询 SQL 语句，
 * 以及结果集中各列名的映射定义。
 *
 * @author zhanyao
 * @since 2018-05-16
 */
public class DB2Query extends AbstractDbQuery {

    /**
     * 获取查询所有表的 SQL 语句
     *
     * @return 查询表的 SQL 语句模板
     */
    @Override
    public String tablesSql() {
        return "SELECT * FROM SYSCAT.TABLES where tabschema=%s";
    }

    /**
     * 获取查询指定表字段信息的 SQL 语句
     *
     * @return 查询表字段的 SQL 语句模板
     */
    @Override
    public String tableFieldsSql() {
        return "SELECT * FROM syscat.columns WHERE tabschema=%s AND tabname='%s'";
    }

    /**
     * 获取结果集中表名称对应的列名
     *
     * @return 列名 "TABNAME"
     */
    @Override
    public String tableName() {
        return "TABNAME";
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
     * @return 列名 "COLNAME"
     */
    @Override
    public String fieldName() {
        return "COLNAME";
    }

    /**
     * 获取结果集中字段类型对应的列名
     *
     * @return 列名 "TYPENAME"
     */
    @Override
    public String fieldType() {
        return "TYPENAME";
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
     * @return 列名 "IDENTITY"
     */
    @Override
    public String fieldKey() {
        return "IDENTITY";
    }

}
