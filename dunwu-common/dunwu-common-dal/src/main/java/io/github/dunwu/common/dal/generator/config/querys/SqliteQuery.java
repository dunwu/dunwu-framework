package io.github.dunwu.common.dal.generator.config.querys;

/**
 * SQLite 数据库元数据查询实现
 * <p>
 * 提供了针对 SQLite 数据库的表及字段元数据查询 SQL 语句，
 * 以及结果集中各列名的映射定义。
 * 由于 SQLite 的特性，部分注释信息可能无法直接获取。
 *
 * @author chen
 * @since 2019-05-08
 */
public class SqliteQuery extends AbstractDbQuery {

    /**
     * 获取查询所有表的 SQL 语句
     *
     * @return 查询表的 SQL 语句
     */
    @Override
    public String tablesSql() {
        return "select * from sqlite_master where type='table'";
    }

    /**
     * 获取查询指定表字段信息的 SQL 语句
     *
     * @return 查询表字段的 SQL 语句
     */
    @Override
    public String tableFieldsSql() {
        return "pragma table_info('%s');";
    }

    /**
     * 获取结果集中表名称对应的列名
     *
     * @return 列名 "name"
     */
    @Override
    public String tableName() {
        return "name";
    }

    /**
     * 获取结果集中表注释对应的列名
     * <p>
     * SQLite 默认不支持通过此方式获取表注释，返回空字符串。
     *
     * @return 空字符串
     */
    @Override
    public String tableComment() {
        return "";
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
     * <p>
     * SQLite 默认不支持通过此方式获取字段注释，返回空字符串。
     *
     * @return 空字符串
     */
    @Override
    public String fieldComment() {
        return "";
    }

    /**
     * 获取结果集中主键字段对应的列名
     *
     * @return 列名 "pk"
     */
    @Override
    public String fieldKey() {
        return "pk";
    }

}
