package io.github.dunwu.common.dal.generator.config.querys;

/**
 * 达梦数据库（DM）元数据查询实现类。
 * <p>
 * 提供了针对达梦数据库的表及字段元数据查询 SQL 语句，
 * 以及结果集中各列名的映射定义。
 *
 * @author halower
 * @since 2019-06-27
 */
public class DMQuery extends AbstractDbQuery {

    /**
     * 获取查询所有表的 SQL 语句
     *
     * @return 查询表的 SQL 语句模板
     */
    @Override
    public String tablesSql() {
        return "SELECT DISTINCT T1.TABLE_NAME,T2.COMMENTS AS TABLE_COMMENT FROM USER_TAB_COLUMNS T1 " +
            "INNER JOIN USER_TAB_COMMENTS T2 ON T1.TABLE_NAME = T2.TABLE_NAME WHERE 1=1 ";
    }

    /**
     * 获取查询指定表字段信息的 SQL 语句
     *
     * @return 查询表字段的 SQL 语句模板
     */
    @Override
    public String tableFieldsSql() {
        return
            "SELECT T2.COLUMN_NAME,T1.COMMENTS,T2.DATA_TYPE ," +
                "CASE WHEN CONSTRAINT_TYPE='P' THEN 'PRI' END AS KEY " +
                "FROM USER_COL_COMMENTS T1, USER_TAB_COLUMNS T2, " +
                "(SELECT T4.TABLE_NAME, T4.COLUMN_NAME ,T5.CONSTRAINT_TYPE " +
                "FROM USER_CONS_COLUMNS T4, USER_CONSTRAINTS T5 " +
                "WHERE T4.CONSTRAINT_NAME = T5.CONSTRAINT_NAME " +
                "AND T5.CONSTRAINT_TYPE = 'P')T3 " +
                "WHERE T1.TABLE_NAME = T2.TABLE_NAME AND " +
                "T1.COLUMN_NAME=T2.COLUMN_NAME AND " +
                "T1.TABLE_NAME = T3.TABLE_NAME(+) AND " +
                "T1.COLUMN_NAME=T3.COLUMN_NAME(+)   AND " +
                "T1.TABLE_NAME = '%s' " +
                "ORDER BY T2.TABLE_NAME,T2.COLUMN_ID";
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
     * @return 列名 "TABLE_COMMENT"
     */
    @Override
    public String tableComment() {
        return "TABLE_COMMENT";
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

}
