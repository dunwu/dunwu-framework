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

import cn.hutool.core.date.DatePattern;
import com.baomidou.mybatisplus.annotation.IdType;
import io.github.dunwu.tool.generator.config.rules.DateType;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.File;

/**
 * 全局配置
 *
 * @author hubin
 * @since 2016-12-02
 */
@Data
@ToString
@Accessors(chain = true)
public class GlobalConfig {

    /** 开启权限校验 */
    private boolean enablePermission = false;
    /** 开启文件覆盖模式 */
    private boolean enableOverride = true;
    /** 开启 swagger2 模式 */
    private boolean enableSwagger = true;
    /** 是否打开输出目录 */
    private boolean open = false;
    /** 开启 Kotlin 模式 */
    private boolean enableKotlin = false;
    /** 开启 ActiveRecord 模式 */
    private boolean enableActiveRecord = false;
    /** 开启 BaseResultMap */
    private boolean enableBaseResultMap = false;
    /** 是否在xml中添加二级缓存配置 */
    private boolean enableCache = false;
    /** 开启 enableBaseColumnList */
    private boolean enableBaseColumnList = false;

    /** 开发人员 */
    private String author;
    /** 生成文件的输出目录【默认 D 盘根目录】 */
    private String outputDir = "D://";
    /** 后端代码生成路径 */
    private String backendDir;
    /** 前端代码生成路径 */
    private String frontendDir;
    /** 包路径 */
    private String packagePath;

    /** 指定生成的主键的ID类型 */
    private IdType idType = IdType.NONE;
    /** 时间类型对应策略 */
    private DateType dateType = DateType.TIME_PACK;
    /** 日期格式化 */
    private String datePattern = DatePattern.NORM_DATETIME_PATTERN;

    // =========================================================
    // 各层文件名称方式，例如： %sAction 生成 UserAction %s 为占位符
    // =========================================================

    private String xmlName;
    private String entityName;
    private String mapperName;
    private String daoName;
    private String daoImplName;
    private String serviceName;
    private String serviceImplName;
    private String controllerName;
    private String apiName;
    private String listName;
    private String formName;

    public GlobalConfig() {}

    public String getBackendDir() {
        if (this.backendDir == null) {
            this.backendDir = this.outputDir + File.separator + "backend";
        }
        return this.backendDir;
    }

    public String getFrontendDir() {
        if (this.frontendDir == null) {
            this.frontendDir = this.outputDir + File.separator + "frontend";
        }
        return this.frontendDir;
    }

}
