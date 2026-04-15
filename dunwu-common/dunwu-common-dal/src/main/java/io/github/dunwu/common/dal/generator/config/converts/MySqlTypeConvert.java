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

import io.github.dunwu.common.dal.generator.config.GlobalConfig;
import io.github.dunwu.common.dal.generator.config.ITypeConvert;
import io.github.dunwu.common.dal.generator.config.rules.IColumnType;
import io.github.dunwu.common.dal.generator.config.rules.JavaColumnType;

/**
 * MySQL 数据库字段类型转换器。
 * <p>
 * 实现了 {@link ITypeConvert} 接口，负责将 MySQL 数据库的字段类型映射为 Java 代码生成所需的 {@link IColumnType}。
 * 支持根据全局配置中的 {@link io.github.dunwu.common.dal.generator.config.rules.DateType} 动态决定日期时间类型的 Java 映射策略。
 *
 * @author hubin
 * @since 2017-01-20
 */
public class MySqlTypeConvert implements ITypeConvert {

    /**
     * 执行 MySQL 字段类型到 Java 类型的转换。
     * <p>
     * 根据字段类型特征匹配，并结合 {@link GlobalConfig#getDateType()} 决定日期/时间类型的映射类。
     *
     * @param globalConfig 全局配置信息，用于决定日期类型的映射策略
     * @param fieldType    数据库字段类型字符串
     * @return 转换后的 Java 列类型 {@link IColumnType}
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        String t = fieldType.toLowerCase();
        if (t.contains("char")) {
            return JavaColumnType.STRING;
        } else if (t.contains("bigint")) {
            return JavaColumnType.LONG;
        } else if (t.contains("tinyint(1)")) {
            return JavaColumnType.BOOLEAN;
        } else if (t.contains("int")) {
            return JavaColumnType.INTEGER;
        } else if (t.contains("text")) {
            return JavaColumnType.STRING;
        } else if (t.contains("bit")) {
            return JavaColumnType.BOOLEAN;
        } else if (t.contains("decimal")) {
            return JavaColumnType.BIG_DECIMAL;
        } else if (t.contains("clob")) {
            return JavaColumnType.CLOB;
        } else if (t.contains("blob")) {
            return JavaColumnType.BLOB;
        } else if (t.contains("binary")) {
            return JavaColumnType.BYTE_ARRAY;
        } else if (t.contains("float")) {
            return JavaColumnType.FLOAT;
        } else if (t.contains("double")) {
            return JavaColumnType.DOUBLE;
        } else if (t.contains("json") || t.contains("enum")) {
            return JavaColumnType.STRING;
        } else if (t.contains("date") || t.contains("time") || t.contains("year")) {
            switch (globalConfig.getDateType()) {
                case ONLY_DATE:
                    return JavaColumnType.DATE;
                case SQL_PACK:
                    switch (t) {
                        case "date":
                            return JavaColumnType.DATE_SQL;
                        case "time":
                            return JavaColumnType.TIME;
                        case "year":
                            return JavaColumnType.DATE_SQL;
                        default:
                            return JavaColumnType.TIMESTAMP;
                    }
                case TIME_PACK:
                    switch (t) {
                        case "date":
                            return JavaColumnType.LOCAL_DATE;
                        case "time":
                            return JavaColumnType.LOCAL_TIME;
                        case "year":
                            return JavaColumnType.YEAR;
                        default:
                            return JavaColumnType.LOCAL_DATE_TIME;
                    }
            }
        }
        return JavaColumnType.STRING;
    }

}
