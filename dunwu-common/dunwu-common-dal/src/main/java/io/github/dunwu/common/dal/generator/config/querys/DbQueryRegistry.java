package io.github.dunwu.common.dal.generator.config.querys;

import com.baomidou.mybatisplus.annotation.DbType;
import io.github.dunwu.common.dal.generator.config.IDbQuery;

import java.util.EnumMap;
import java.util.Map;

/**
 * 数据库查询实现注册表。
 * <p>
 * 负责维护不同数据库类型（如 MySQL, Oracle, PostgreSQL 等）对应的 {@link IDbQuery} 实现实例。
 *
 * @author nieqiuqiu
 * @since 2020-01-09
 */
public class DbQueryRegistry {

    /**
     * 数据库类型与查询实现的映射关系
     */
    private final Map<DbType, IDbQuery> db_query_enum_map = new EnumMap<>(DbType.class);

    /**
     * 默认构造方法
     * <p>
     * 初始化并注册所有支持的数据库查询实现。
     */
    public DbQueryRegistry() {
        db_query_enum_map.put(DbType.ORACLE, new OracleQuery());
        db_query_enum_map.put(DbType.SQL_SERVER, new SqlServerQuery());
        db_query_enum_map.put(DbType.POSTGRE_SQL, new PostgreSqlQuery());
        db_query_enum_map.put(DbType.DB2, new DB2Query());
        db_query_enum_map.put(DbType.MARIADB, new MariadbQuery());
        db_query_enum_map.put(DbType.H2, new H2Query());
        db_query_enum_map.put(DbType.SQLITE, new SqliteQuery());
        db_query_enum_map.put(DbType.DM, new DMQuery());
        db_query_enum_map.put(DbType.KINGBASE_ES, new KingbaseESQuery());
        db_query_enum_map.put(DbType.MYSQL, new MySqlQuery());
    }

    /**
     * 根据数据库类型获取对应的查询实现
     *
     * @param dbType 数据库类型枚举
     * @return 对应的 IDbQuery 实现，如果未注册则返回 null
     */
    public IDbQuery getDbQuery(DbType dbType) {
        return db_query_enum_map.get(dbType);
    }

}
