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
package io.github.dunwu.tool.generator.config.converts;

import io.github.dunwu.tool.generator.config.GlobalConfig;
import io.github.dunwu.tool.generator.config.ITypeConvert;
import io.github.dunwu.tool.generator.config.rules.IColumnType;
import io.github.dunwu.tool.generator.config.rules.JavaColumnType;

/**
 * KingbaseES 字段类型转换
 *
 * @author kingbase
 * @since 2019-10-12
 */
public class KingbaseESTypeConvert implements ITypeConvert {

    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        String t = fieldType.toLowerCase();
        if (t.contains("char")) {
            return JavaColumnType.STRING;
        } else if (t.contains("bigint")) {
            return JavaColumnType.LONG;
        } else if (t.contains("int")) {
            return JavaColumnType.INTEGER;
        } else if (t.contains("date") || t.contains("time")) {
            switch (globalConfig.getDateType()) {
                case ONLY_DATE:
                    return JavaColumnType.DATE;
                case SQL_PACK:
                    switch (t) {
                        case "date":
                            return JavaColumnType.DATE_SQL;
                        case "time":
                            return JavaColumnType.TIME;
                        default:
                            return JavaColumnType.TIMESTAMP;
                    }
                case TIME_PACK:
                    switch (t) {
                        case "date":
                            return JavaColumnType.LOCAL_DATE;
                        case "time":
                            return JavaColumnType.LOCAL_TIME;
                        default:
                            return JavaColumnType.LOCAL_DATE_TIME;
                    }
                default:
                    return JavaColumnType.DATE;
            }
        } else if (t.contains("text")) {
            return JavaColumnType.STRING;
        } else if (t.contains("bit")) {
            return JavaColumnType.BOOLEAN;
        } else if (t.contains("decimal")) {
            return JavaColumnType.BIG_DECIMAL;
        } else if (t.contains("clob")) {
            return JavaColumnType.CLOB;
        } else if (t.contains("blob")) {
            return JavaColumnType.BYTE_ARRAY;
        } else if (t.contains("float")) {
            return JavaColumnType.FLOAT;
        } else if (t.contains("double")) {
            return JavaColumnType.DOUBLE;
        } else if (t.contains("json") || t.contains("enum")) {
            return JavaColumnType.STRING;
        } else if (t.contains("boolean")) {
            return JavaColumnType.BOOLEAN;
        } else if (t.contains("numeric")) {
            return JavaColumnType.BIG_DECIMAL;
        }
        return JavaColumnType.STRING;
    }

}
