package io.github.dunwu.common.dal.generator.config.querys;

/**
 * PostgreSQL 数据库元数据查询实现
 * <p>
 * 提供了针对 PostgreSQL 数据库的表及字段元数据查询 SQL 语句，
 * 以及结果集中各列名的映射定义。
 *
 * @author hubin
 * @since 2018-01-16
 */
public class PostgreSqlQuery extends AbstractDbQuery {

    /**
     * 获取查询所有表的 SQL 语句
     *
     * @return 查询表的 SQL 语句
     */
    @Override
    public String tablesSql() {
        return "SELECT A.tablename, obj_description(relfilenode, 'pg_class') AS comments FROM pg_tables A, pg_class B WHERE A.schemaname='%s' AND A.tablename = B.relname";
    }

    /**
     * 获取查询指定表字段信息的 SQL 语句
     *
     * @return 查询表字段的 SQL 语句
     */
    @Override
    public String tableFieldsSql() {
        return
            "SELECT A.attname AS name, format_type(A.atttypid, A.atttypmod) AS type,col_description(A.attrelid, A.attnum) AS comment, (CASE C.contype WHEN 'p' THEN 'PRI' ELSE '' END) AS key "
                +
                "FROM pg_attribute A LEFT JOIN pg_constraint C ON A.attnum = C.conkey[1] AND A.attrelid = C.conrelid "
                +
                "WHERE  A.attrelid = '%s.%s'::regclass AND A.attnum > 0 AND NOT A.attisdropped ORDER  BY A.attnum";
    }

    /**
     * 获取结果集中表名称对应的列名
     *
     * @return 列名 "tablename"
     */
    @Override
    public String tableName() {
        return "tablename";
    }

    /**
     * 获取结果集中表注释对应的列名
     *
     * @return 列名 "comments"
     */
    @Override
    public String tableComment() {
        return "comments";
    }

    /**
     * 获取结果集中字段名称对应的列名
     *
     * @return 列名 "name"
     */
    @Override
    public String fieldName() {
        return "name";
    }

    /**
     * 获取结果集中字段类型对应的列名
     *
     * @return 列名 "type"
     */
    @Override
    public String fieldType() {
        return "type";
    }

    /**
     * 获取结果集中字段注释对应的列名
     *
     * @return 列名 "comment"
     */
    @Override
    public String fieldComment() {
        return "comment";
    }

    /**
     * 获取结果集中主键字段对应的列名
     *
     * @return 列名 "key"
     */
    @Override
    public String fieldKey() {
        return "key";
    }

}
