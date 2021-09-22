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
    // 源码文件默认后缀
    // =========================================================

    String XML = "Xml";
    String ENTITY = "Entity";
    String DTO = "Dto";
    String QUERY = "Query";
    String DAO = "Dao";
    String DAO_IMPL = "DaoImpl";
    String SERVICE = "Service";
    String SERVICE_IMPL = "ServiceImpl";
    String MAPPER = "Mapper";
    String CONTROLLER = "Controller";
    String API = "Api";
    String LIST = "List";
    String FORM = "Form";

    // =========================================================
    // 源码文件路径 key
    // =========================================================

    String XML_PATH = "xml_path";
    String ENTITY_PATH = "entity_path";
    String DTO_PATH = "dto_path";
    String QUERY_PATH = "query_path";
    String DAO_PATH = "dao_path";
    String DAO_IMPL_PATH = "dao_impl_path";
    String SERVICE_PATH = "service_path";
    String SERVICE_IMPL_PATH = "service_impl_path";
    String MAPPER_PATH = "mapper_path";
    String CONTROLLER_PATH = "controller_path";
    String API_PATH = "api_path";
    String LIST_PATH = "list_path";
    String FORM_PATH = "form_path";

    // =========================================================
    // 模板文件默认路径
    // =========================================================

    String TEMPLATE_XML = "/templates/backend/mapper.xml";
    String TEMPLATE_ENTITY_JAVA = "/templates/backend/entity.java";
    String TEMPLATE_DTO_JAVA = "/templates/backend/dto.java";
    String TEMPLATE_QUERY_JAVA = "/templates/backend/query.java";
    String TEMPLATE_ENTITY_KT = "/templates/backend/entity.kt";
    String TEMPLATE_MAPPER = "/templates/backend/mapper.java";
    String TEMPLATE_DAO = "/templates/backend/dao.java";
    String TEMPLATE_DAO_IMPL = "/templates/backend/daoImpl.java";
    String TEMPLATE_SERVICE = "/templates/backend/service.java";
    String TEMPLATE_SERVICE_IMPL = "/templates/backend/serviceImpl.java";
    String TEMPLATE_CONTROLLER = "/templates/backend/controller.java";
    String TEMPLATE_API = "/templates/frontend/api.js";
    String TEMPLATE_LIST = "/templates/frontend/list.vue";
    String TEMPLATE_FORM = "/templates/frontend/form.vue";

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

    String VM_LOAD_PATH_KEY = "file.resource.loader.class";
    String VM_LOAD_PATH_VALUE = "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader";

    String SUPER_MAPPER_CLASS = "com.baomidou.mybatisplus.core.mapper.BaseMapper";
    String SUPER_DAO_CLASS = "io.github.dunwu.tool.data.mybatis.IExtDao";
    String SUPER_DAO_IMPL_CLASS = "io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl";
    String SUPER_SERVICE_CLASS = "io.github.dunwu.tool.data.mybatis.IService";
    String SUPER_SERVICE_IMPL_CLASS = "io.github.dunwu.tool.data.mybatis.ServiceImpl";

    String JAVA_PATH = "/src/main/java";
    String RESOURCES_PATH = "/src/main/resources";
    String VIEWS_PATH = "/src/views";

}
