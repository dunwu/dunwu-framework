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
 * GaussDB 数据库字段类型转换器。
 * <p>
 * 实现了 {@link ITypeConvert} 接口，负责将 GaussDB 数据库的字段类型映射为 Java 代码生成所需的 {@link IColumnType}。
 * 内部采用责任链模式（{@link Handler} 接口）分阶段处理字符、数值、日期、多媒体等不同类型的数据映射。
 *
 * @author halower
 * @since 2019-06-27
 */
public class GaussTypeConvert implements ITypeConvert {

    /**
     * 执行 GaussDB 字段类型到 Java 类型的转换。
     * <p>
     * 将字段类型转换为小写后，交由 {@link BasicDataTypeHandler} 开始责任链处理。
     *
     * @param globalConfig 全局配置信息（在此实现中未直接使用）
     * @param fieldType    数据库字段类型字符串
     * @return 转换后的 Java 列类型 {@link IColumnType}
     */
    @Override
    public IColumnType processTypeConvert(GlobalConfig globalConfig, String fieldType) {
        String t = fieldType.toLowerCase();
        return new BasicDataTypeHandler().getDbColumnType(t);
    }

    /**
     * 类型处理器接口（责任链节点）。
     * <p>
     * 定义了获取数据库字段对应 Java 类型的方法。若当前处理器无法识别类型，可交由链中下一节点处理。
     */
    interface Handler {

        /**
         * 根据数据库字段类型获取对应的 Java 类型。
         *
         * @param fieldType 数据库字段类型字符串
         * @return Java 列类型枚举，若无法识别则交由下一节点或返回默认值
         */
        JavaColumnType getDbColumnType(String fieldType);

    }

    /**
     * 常规数据类型处理器。
     * <p>
     * 处理字符型（CHAR/VARCHAR）、数值型（INTEGER/BIGINT/DECIMAL等）、布尔型及二进制类型。
     */
    class BasicDataTypeHandler implements Handler {

        @Override
        public JavaColumnType getDbColumnType(String fieldType) {
            //字符数据类型: CHAR,CHARACTER,VARCHAR
            if (fieldType.contains("char")) {
                return JavaColumnType.STRING;
            }
            /**
             *  数值数据类型:
             *  NUMERIC,DECIMAL,DEC,MONEY,BIT,BOOL,BOOLEAN,INTEGER,INT,BIGINT
             *  TINYINT,BYTE,SMALLINT,BINARY,VARBINARY
             */
            else if (fieldType.contains("numeric")
                || fieldType.contains("decimal")
                || fieldType.contains("dec")
                || fieldType.contains("money")) {
                return JavaColumnType.BIG_DECIMAL;
            } else if (fieldType.contains("bit")
                || fieldType.contains("bool")
                || fieldType.contains("boolean")) {
                return JavaColumnType.BOOLEAN;
            } else if (fieldType.contains("integer") || fieldType.contains("int")) {
                return JavaColumnType.INTEGER;
            } else if (fieldType.contains("bigint")) {
                return JavaColumnType.BIG_INTEGER;
            } else if (fieldType.contains("tinyint")
                || fieldType.contains("byte")
                || fieldType.contains("smallint")
            ) {
                return JavaColumnType.INTEGER;
            } else if (fieldType.contains("binary")
                || fieldType.contains("varbinary")
            ) {
                return JavaColumnType.BYTE_ARRAY;
            }
            /**
             * 近似数值数据类型:
             * FLOAT
             */
            else if (fieldType.contains("float")) {
                return JavaColumnType.FLOAT;
            }
            /**
             * DOUBLE, DOUBLE PRECISION,REAL
             */
            else if (fieldType.contains("double") || fieldType.contains("real")) {
                return JavaColumnType.DOUBLE;
            }
            return new DateTimeDataTypeHandler().getDbColumnType(fieldType);
        }

    }

    /**
     * 日期时间数据类型处理器
     * <p>
     * 处理 DATE, TIME, TIMESTAMP 等类型。
     */
    class DateTimeDataTypeHandler implements Handler {

        @Override
        public JavaColumnType getDbColumnType(String fieldType) {
            if (fieldType.contains("date")
                || fieldType.contains("time")
                || fieldType.contains("timestamp")) {
                return JavaColumnType.DATE;
            }
            return new MultimediaDataTypeHandler().getDbColumnType(fieldType);
        }

    }

    /**
     * 多媒体数据类型处理器
     * <p>
     * 处理 TEXT, CLOB, BLOB, IMAGE 等大对象类型。
     */
    class MultimediaDataTypeHandler implements Handler {

        @Override
        public JavaColumnType getDbColumnType(String fieldType) {
            if (fieldType.contains("text") || fieldType.contains("longvarchar")) {
                return JavaColumnType.STRING;
            } else if (fieldType.contains("clob")) {
                return JavaColumnType.CLOB;
            } else if (fieldType.contains("blob")) {
                return JavaColumnType.BLOB;
            } else if (fieldType.contains("image")) {
                return JavaColumnType.BYTE_ARRAY;
            }
            return JavaColumnType.STRING;
        }

    }

}
