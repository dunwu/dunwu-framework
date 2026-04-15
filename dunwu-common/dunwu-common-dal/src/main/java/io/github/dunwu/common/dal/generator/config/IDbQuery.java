package io.github.dunwu.common.dal.generator.config;

import com.baomidou.mybatisplus.annotation.DbType;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 数据库表元数据查询接口。
 * <p>
 * 定义了代码生成器查询数据库元数据（如表名、字段名、类型、注释、主键等）所需的方法。
 * 针对不同数据库（MySQL, Oracle, PostgreSQL 等）的实现类应提供对应的 SQL 查询语句及结果集映射。
 *
 * @author hubin
 * @since 2018-01-16
 */
public interface IDbQuery {

    /**
     * 获取数据库类型
     *
     * @return 数据库类型枚举
     * @deprecated 3.3.1 请使用 {@link DataSourceConfig#dbType}
     */
    @Deprecated
    default DbType dbType() {
        return null;
    }

    /**
     * 获取查询数据库中所有表信息的 SQL 语句模板。
     * <p>
     * 用于查询数据库中所有表的列表信息，通常包含表名和表注释。
     *
     * @return SQL 语句模板
     */
    String tablesSql();

    /**
     * 获取查询指定表字段详细信息的 SQL 语句模板。
     * <p>
     * 用于查询指定表的所有字段详细信息，包括字段名、类型、注释、主键标识等。
     *
     * @return SQL 语句模板
     */
    String tableFieldsSql();

    /**
     * 获取结果集中表示表名称的列名。
     *
     * @return 列名
     */
    String tableName();

    /**
     * 获取结果集中表示表注释的列名。
     *
     * @return 列名
     */
    String tableComment();

    /**
     * 获取结果集中表示字段名称的列名。
     *
     * @return 列名
     */
    String fieldName();

    /**
     * 获取结果集中表示字段类型的列名。
     *
     * @return 列名
     */
    String fieldType();

    /**
     * 获取结果集中表示字段注释的列名。
     *
     * @return 列名
     */
    String fieldComment();

    /**
     * 获取结果集中表示主键标识的列名。
     *
     * @return 列名
     */
    String fieldKey();

    /**
     * 判断当前表的主键是否为自增类型（Identity）。
     * <p>
     * 通过检查结果集来判断当前表的主键是否配置为自增。
     * 目前主要用于 MySQL 数据库检查主键是否自增。
     *
     * @param results 查询结果集
     * @return 如果是自增主键返回 true，否则返回 false
     * @throws SQLException 如果发生数据库访问异常
     */
    boolean isKeyIdentity(ResultSet results) throws SQLException;

    /**
     * 获取需要额外提取的自定义字段名称列表。
     * <p>
     * 用于扩展查询结果中的额外字段，实现类可返回需要额外提取的列名。
     *
     * @return 自定义字段名称数组
     */
    String[] fieldCustom();

    /**
     * 获取结果集中表示字段是否允许为空的列名。
     *
     * @return 列名
     */
    String nullAble();

}
