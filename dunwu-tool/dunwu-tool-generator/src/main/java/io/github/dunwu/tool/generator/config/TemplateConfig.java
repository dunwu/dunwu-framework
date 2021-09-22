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

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

/**
 * 模板路径配置项
 *
 * @author tzg hubin
 * @since 2017-06-17
 */
@Data
@Accessors(chain = true)
public class TemplateConfig {

    // =========================================================
    // 后端源码模板文件
    // =========================================================

    @Getter(AccessLevel.NONE)
    private String entity = ConstVal.TEMPLATE_ENTITY_JAVA;
    private String dto = ConstVal.TEMPLATE_DTO_JAVA;
    private String query = ConstVal.TEMPLATE_QUERY_JAVA;
    private String entityKt = ConstVal.TEMPLATE_ENTITY_KT;
    private String mapper = ConstVal.TEMPLATE_MAPPER;
    private String dao = ConstVal.TEMPLATE_DAO;
    private String daoImpl = ConstVal.TEMPLATE_DAO_IMPL;
    private String service = ConstVal.TEMPLATE_SERVICE;
    private String serviceImpl = ConstVal.TEMPLATE_SERVICE_IMPL;
    private String controller = ConstVal.TEMPLATE_CONTROLLER;
    private String xml = ConstVal.TEMPLATE_XML;

    // =========================================================
    // 前端源码模板文件
    // =========================================================

    private String api = ConstVal.TEMPLATE_API;
    private String list = ConstVal.TEMPLATE_LIST;
    private String form = ConstVal.TEMPLATE_FORM;

    public String getEntity(boolean enableKotlin) {
        return enableKotlin ? entityKt : entity;
    }

}
