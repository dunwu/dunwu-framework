/*
 * Copyright (c) 2011-2020, baomidou (jobob@qq.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * <p>
 * https://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package io.github.dunwu.common.dal.generator.config.converts;

import com.baomidou.mybatisplus.annotation.DbType;
import io.github.dunwu.common.dal.generator.config.ITypeConvert;

import java.util.EnumMap;
import java.util.Map;

/**
 * 数据库类型转换器注册表。
 * <p>
 * 维护了 {@link DbType} 到 {@link ITypeConvert} 的映射关系，
 * 用于在代码生成过程中根据目标数据库类型动态获取对应的字段类型转换器。
 *
 * @author nieqiuqiu
 * @since 2020-01-09
 */
public class TypeConvertRegistry {

    /**
     * 存储数据库类型与类型转换器实例的映射关系
     */
    private final Map<DbType, ITypeConvert> type_convert_enum_map = new EnumMap<>(DbType.class);

    /**
     * 默认构造方法，初始化所有支持的数据库类型转换器实例
     */
    public TypeConvertRegistry() {
        type_convert_enum_map.put(DbType.ORACLE, new OracleTypeConvert());
        type_convert_enum_map.put(DbType.SQL_SERVER, new SqlServerTypeConvert());
        type_convert_enum_map.put(DbType.POSTGRE_SQL, new PostgreSqlTypeConvert());
        type_convert_enum_map.put(DbType.DB2, new DB2TypeConvert());
        type_convert_enum_map.put(DbType.SQLITE, new SqliteTypeConvert());
        type_convert_enum_map.put(DbType.DM, new DmTypeConvert());
        type_convert_enum_map.put(DbType.MARIADB,
            new MySqlTypeConvert());
        type_convert_enum_map.put(DbType.KINGBASE_ES, new KingbaseESTypeConvert());
        type_convert_enum_map.put(DbType.MYSQL, new MySqlTypeConvert());
    }

    /**
     * 根据数据库类型获取对应的类型转换器
     *
     * @param dbType 数据库类型枚举
     * @return 对应的类型转换器实例，若不支持则返回 null
     */
    public ITypeConvert getTypeConvert(DbType dbType) {
        return type_convert_enum_map.get(dbType);
    }

}
