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
 * DB2 数据库字段类型转换器。
 * <p>
 * 实现了 {@link ITypeConvert} 接口，负责将 DB2 数据库的字段类型映射为 Java 代码生成所需的 {@link IColumnType}。
 * 支持常见的 DB2 数据类型，如字符型、数值型、日期型、大对象等。
 *
 * @author zhanyao
 * @since 2018-05-16
 */
public class DB2TypeConvert implements ITypeConvert {

    /**
     * 执行 DB2 字段类型到 Java 类型的转换
     * <p>
     * 根据传入的数据库字段类型字符串，匹配并返回对应的 Java 类型枚举。
     * 例如：char -> String, bigint -> Long, date -> Date 等。
     *
     * @param globalConfig 全局配置信息（在此实现中未直接使用）
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
        } else if (t.contains("smallint")) {
            return JavaColumnType.BASE_SHORT;
        } else if (t.contains("int")) {
            return JavaColumnType.INTEGER;
        } else if (t.contains("date") || t.contains("time")
            || t.contains("year") || t.contains("timestamp")) {
            return JavaColumnType.DATE;
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
        }
        return JavaColumnType.STRING;
    }

}
