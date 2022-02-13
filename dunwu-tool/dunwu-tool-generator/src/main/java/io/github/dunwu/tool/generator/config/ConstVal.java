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
package io.github.dunwu.tool.generator.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.nio.charset.StandardCharsets;

/**
 * 定义常量
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-31
 */
public interface ConstVal {

    String MODULE_NAME = "ModuleName";

    // =========================================================
    // 模板文件类型
    // =========================================================

    String JAVA_SUFFIX = StringPool.DOT_JAVA;
    String KT_SUFFIX = ".kt";
    String XML_SUFFIX = ".xml";
    String JS_SUFFIX = ".js";
    String VUE_SUFFIX = ".vue";

    // =========================================================
    // 其他
    // =========================================================

    String JAVA_TMPDIR = "java.io.tmpdir";
    String UTF8 = StandardCharsets.UTF_8.name();
    String UNDERLINE = "_";

    String SUPER_MAPPER_CLASS = "com.baomidou.mybatisplus.core.mapper.BaseMapper";
    String SUPER_DAO_CLASS = "io.github.dunwu.tool.data.mybatis.IExtDao";
    String SUPER_DAO_IMPL_CLASS = "io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl";
    String SUPER_SERVICE_CLASS = "io.github.dunwu.tool.data.mybatis.IService";
    String SUPER_SERVICE_IMPL_CLASS = "io.github.dunwu.tool.data.mybatis.ServiceImpl";

    String JAVA_PATH = "/src/main/java";
    String RESOURCES_PATH = "/src/main/resources";
    String VIEWS_PATH = "/src/views";

    String DICT_TEMPLATE_PATH = "templates/backend/dict.java.ftl";

}
