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

import io.github.dunwu.tool.generator.config.rules.FileType;
import lombok.Data;
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

    private String entity = FileType.ENTITY.getTemplatePath();
    private String dto = FileType.DTO.getTemplatePath();
    private String query = FileType.QUERY.getTemplatePath();
    private String mapper = FileType.MAPPER.getTemplatePath();
    private String dao = FileType.DAO.getTemplatePath();
    private String daoImpl = FileType.DAO_IMPL.getTemplatePath();
    private String service = FileType.SERVICE.getTemplatePath();
    private String serviceImpl = FileType.SERVICE_IMPL.getTemplatePath();
    private String controller = FileType.CONTROLLER.getTemplatePath();
    private String xml = FileType.XML.getTemplatePath();

    // =========================================================
    // 前端源码模板文件
    // =========================================================

    private String api = FileType.API.getTemplatePath();
    private String list = FileType.LIST.getTemplatePath();
    private String form = FileType.FORM.getTemplatePath();

}
