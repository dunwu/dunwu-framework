package io.github.dunwu.common.dal.generator.config.querys;

/**
 * Oracle 数据库元数据查询实现类。
 * <p>
 * 提供了针对 Oracle 数据库的表及字段元数据查询 SQL 语句，
 * 以及结果集中各列名的映射定义。
 * 支持处理 Oracle 特有的数据类型精度及主键约束查询。
 *
 * @author hubin
 * @since 2018-01-16
 */
public class OracleQuery extends AbstractDbQuery {

    /**
     * 获取查询所有表的 SQL 语句
     *
     * @return 查询表的 SQL 语句模板
     */
    @Override
    public String tablesSql() {
        return "SELECT * FROM ALL_TAB_COMMENTS WHERE OWNER='%s'";
    }

    /**
     * 获取查询指定表字段信息的 SQL 语句
     *
     * @return 查询表字段的 SQL 语句模板
     */
    @Override
    public String tableFieldsSql() {
        return "SELECT A.COLUMN_NAME, CASE WHEN A.DATA_TYPE='NUMBER' THEN "
            + "(CASE WHEN A.DATA_PRECISION IS NULL THEN A.DATA_TYPE "
            + "WHEN NVL(A.DATA_SCALE, 0) > 0 THEN A.DATA_TYPE||'('||A.DATA_PRECISION||','||A.DATA_SCALE||')' "
            + "ELSE A.DATA_TYPE||'('||A.DATA_PRECISION||')' END) "
            + "ELSE A.DATA_TYPE END DATA_TYPE, B.COMMENTS,DECODE(C.POSITION, '1', 'PRI') KEY "
            + "FROM ALL_TAB_COLUMNS A "
            + " INNER JOIN ALL_COL_COMMENTS B ON A.TABLE_NAME = B.TABLE_NAME AND A.COLUMN_NAME = B.COLUMN_NAME AND B.OWNER = '#schema'"
            + " LEFT JOIN ALL_CONSTRAINTS D ON D.TABLE_NAME = A.TABLE_NAME AND D.CONSTRAINT_TYPE = 'P' AND D.OWNER = '#schema'"
            + " LEFT JOIN ALL_CONS_COLUMNS C ON C.CONSTRAINT_NAME = D.CONSTRAINT_NAME AND C.COLUMN_NAME=A.COLUMN_NAME AND C.OWNER = '#schema'"
            + "WHERE A.OWNER = '#schema' AND A.TABLE_NAME = '%s' ORDER BY A.COLUMN_ID ";
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

}
