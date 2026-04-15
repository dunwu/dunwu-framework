package io.github.dunwu.common.dal.generator.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.core.toolkit.ExceptionUtils;
import io.github.dunwu.common.dal.generator.config.converts.TypeConvertRegistry;
import io.github.dunwu.common.dal.generator.config.querys.DbQueryRegistry;
import lombok.Data;
import lombok.experimental.Accessors;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Optional;

/**
 * 数据库连接配置
 * <p>
 * 用于配置代码生成器连接数据库所需的信息，如 URL、驱动、用户名、密码等。
 * 同时提供自动识别数据库类型 ({@link DbType}) 和获取对应查询实现 ({@link IDbQuery})
 * 及类型转换器 ({@link ITypeConvert}) 的功能。
 *
 * @author YangHu
 * @since 2016/8/30
 */
@Data
@Accessors(chain = true)
public class DataSourceConfig {

    /**
     * 数据库信息查询接口实现
     */
    private IDbQuery dbQuery;

    /**
     * 数据库类型枚举
     */
    private DbType dbType;

    /**
     * Schema 名称（数据库名）
     */
    private String schemaName;

    /**
     * 数据库类型转换器实现
     */
    private ITypeConvert typeConvert;

    /**
     * 数据库连接 URL
     */
    private String url;

    /**
     * 数据库驱动类名称
     */
    private String driverName;

    /**
     * 数据库连接用户名
     */
    private String username;

    /**
     * 数据库连接密码
     */
    private String password;

    /**
     * 是否支持注释
     * <p>
     * 部分数据库可能不支持获取表或字段注释，此标志用于标识。
     */
    private boolean commentSupported;

    /**
     * 默认构造方法
     */
    public DataSourceConfig() { }

    /**
     * 构造方法
     *
     * @param url        数据库连接 URL
     * @param driverName 驱动类名称
     * @param username   用户名
     * @param password   密码
     */
    public DataSourceConfig(String url, String driverName, String username, String password) {
        this(url, driverName, username, password, null);
    }

    /**
     * 构造方法
     *
     * @param url        数据库连接 URL
     * @param driverName 驱动类名称
     * @param username   用户名
     * @param password   密码
     * @param schemaName Schema 名称
     */
    public DataSourceConfig(String url, String driverName, String username, String password, String schemaName) {
        this.url = url;
        this.driverName = driverName;
        this.username = username;
        this.password = password;
        this.schemaName = schemaName;
    }

    /**
     * 获取数据库查询接口实现
     * <p>
     * 如果未手动设置 {@link #dbQuery}，则根据 {@link #getDbType()} 自动从注册表中获取默认实现。
     * 若未找到匹配的实现，默认返回 MySQL 的查询实现。
     *
     * @return 数据库查询接口 {@link IDbQuery}
     */
    public IDbQuery getDbQuery() {
        if (null == dbQuery) {
            DbType dbType = getDbType();
            DbQueryRegistry dbQueryRegistry = new DbQueryRegistry();
            // 默认 MYSQL
            dbQuery = Optional.ofNullable(dbQueryRegistry.getDbQuery(dbType))
                              .orElseGet(() -> dbQueryRegistry.getDbQuery(DbType.MYSQL));
        }
        return dbQuery;
    }

    /**
     * 获取数据库类型
     * <p>
     * 自动从驱动名称或 URL 中识别数据库类型。
     *
     * @return 数据库类型枚举 {@link DbType}
     * @throws RuntimeException 如果无法识别数据库类型
     */
    public DbType getDbType() {
        if (null == this.dbType) {
            this.dbType = this.getDbType(this.driverName);
            if (null == this.dbType) {
                this.dbType = this.getDbType(this.url.toLowerCase());
                if (null == this.dbType) {
                    throw ExceptionUtils.mpe("Unknown type of database!");
                }
            }
        }

        return this.dbType;
    }

    /**
     * 根据字符串特征判断数据库类型
     *
     * @param str 用于寻找特征的字符串，可以是 driverName 或小写后的 url
     * @return 数据库类型枚举，如果未匹配到则返回 null
     */
    private DbType getDbType(String str) {
        if (str.contains("mysql")) {
            return DbType.MYSQL;
        } else if (str.contains("oracle")) {
            return DbType.ORACLE;
        } else if (str.contains("postgresql")) {
            return DbType.POSTGRE_SQL;
        } else if (str.contains("sqlserver")) {
            return DbType.SQL_SERVER;
        } else if (str.contains("db2")) {
            return DbType.DB2;
        } else if (str.contains("mariadb")) {
            return DbType.MARIADB;
        } else if (str.contains("sqlite")) {
            return DbType.SQLITE;
        } else if (str.contains("h2")) {
            return DbType.H2;
        } else if (str.contains("kingbase") || str.contains("kingbase8")) {
            return DbType.KINGBASE_ES;
        } else {
            return null;
        }
    }

    /**
     * 获取数据库类型转换器
     * <p>
     * 如果未手动设置 {@link #typeConvert}，则根据 {@link #getDbType()} 自动从注册表中获取默认实现。
     * 若未找到匹配的实现，默认返回 MySQL 的类型转换器。
     *
     * @return 类型转换器 {@link ITypeConvert}
     */
    public ITypeConvert getTypeConvert() {
        if (null == typeConvert) {
            DbType dbType = getDbType();
            TypeConvertRegistry typeConvertRegistry = new TypeConvertRegistry();
            // 默认 MYSQL
            typeConvert = Optional.ofNullable(typeConvertRegistry.getTypeConvert(dbType))
                                  .orElseGet(() -> typeConvertRegistry.getTypeConvert(DbType.MYSQL));
        }
        return typeConvert;
    }

    /**
     * 创建并获取数据库连接对象
     *
     * @return 数据库连接 {@link Connection}
     * @throws RuntimeException 如果加载驱动失败或连接数据库失败
     */
    public Connection getConn() {
        Connection conn;
        try {
            Class.forName(driverName);
            conn = DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
        return conn;
    }

}
