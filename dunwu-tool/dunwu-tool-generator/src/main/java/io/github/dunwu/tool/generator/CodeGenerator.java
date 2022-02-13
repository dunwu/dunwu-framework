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
package io.github.dunwu.tool.generator;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import freemarker.template.TemplateException;
import io.github.dunwu.tool.generator.config.builder.ConfigBuilder;
import io.github.dunwu.tool.generator.config.po.TableInfo;
import io.github.dunwu.tool.generator.engine.AbstractTemplateEngine;
import io.github.dunwu.tool.generator.engine.CodeGenerateContentDto;
import io.github.dunwu.tool.generator.engine.FreemarkerTemplateEngine;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 代码生成器
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
@Slf4j
@Data
@Accessors(chain = true)
public class CodeGenerator {

    /** 配置构造器 */
    protected ConfigBuilder builder;

    /** 模板引擎 */
    private AbstractTemplateEngine templateEngine;

    public CodeGenerator(ConfigBuilder builder) {
        assert builder != null;
        this.builder = builder;

        if (null == templateEngine) {
            // 为了兼容之前逻辑，采用 Freemark 引擎 【 默认 】
            templateEngine = new FreemarkerTemplateEngine();
        }
        // 模板引擎初始化执行文件输出
        templateEngine.init(this.pretreatmentConfigBuilder(builder));
    }

    /**
     * 生成所有代码
     */
    public void generateAll() {
        log.debug(">>>>>>>> 准备自动生成源码文件");

        // 模板引擎初始化执行文件输出
        templateEngine.mkdirs().generateAll().open();
        log.debug("<<<<<<<< 自动生成源码文件完成");
    }

    /**
     * 生成代码预览内容
     *
     * @return /
     */
    public List<CodeGenerateContentDto> preview() {
        log.debug(">>>>>>>> 准备自动生成源码文件预览列表");

        List<CodeGenerateContentDto> list = new ArrayList<>();
        try {
            list.addAll(templateEngine.preview());
            log.debug("<<<<<<<< 自动生成源码文件预览列表完成");
        } catch (IOException | TemplateException e) {
            e.printStackTrace();
        }

        return list;
    }

    /**
     * 预处理配置
     *
     * @param builder 总配置信息
     * @return 解析数据结果集
     */
    protected ConfigBuilder pretreatmentConfigBuilder(ConfigBuilder builder) {
        /*
         * 注入自定义配置
         */
        if (null != builder.getInjectionConfig()) {
            builder.getInjectionConfig().initMap();
            builder.setInjectionConfig(builder.getInjectionConfig());
        }
        /*
         * 表信息列表
         */
        Collection<TableInfo> tableList = this.getAllTableInfoList(builder);
        for (TableInfo tableInfo : tableList) {
            /* ---------- 添加导入包 ---------- */
            if (builder.getGlobalConfig().isEnableActiveRecord()) {
                // 开启 ActiveRecord 模式
                tableInfo.setImportPackages(Model.class.getCanonicalName());
            }
            if (tableInfo.isConvert()) {
                // 表注解
                tableInfo.setImportPackages(TableName.class.getCanonicalName());
            }
            if (builder.getStrategyConfig().getLogicDeleteFieldName() != null && tableInfo.isLogicDelete(
                builder.getStrategyConfig().getLogicDeleteFieldName())) {
                // 逻辑删除注解
                tableInfo.setImportPackages(TableLogic.class.getCanonicalName());
            }
            if (StringUtils.isNotBlank(builder.getStrategyConfig().getVersionFieldName())) {
                // 乐观锁注解
                tableInfo.setImportPackages(Version.class.getCanonicalName());
            }
            boolean importSerializable = true;
            if (StringUtils.isNotBlank(builder.getSuperEntityClass())) {
                // 父实体
                tableInfo.setImportPackages(builder.getSuperEntityClass());
                importSerializable = false;
            }
            if (builder.getGlobalConfig().isEnableActiveRecord()) {
                importSerializable = true;
            }
            if (importSerializable) {
                tableInfo.setImportPackages(Serializable.class.getCanonicalName());
            }
            // Boolean类型is前缀处理
            if (builder.getStrategyConfig().isEntityBooleanColumnRemoveIsPrefix()
                && CollectionUtils.isNotEmpty(tableInfo.getFields())) {
                tableInfo.getFields().stream().filter(
                             field -> "boolean".equalsIgnoreCase(field.getJavaType().getType()))
                         .filter(field -> field.getPropertyName().startsWith("is"))
                         .forEach(field -> {
                             field.setConvert(true);
                             field.setPropertyName(
                                 StringUtils.removePrefixAfterPrefixToLower(field.getPropertyName(), 2));
                         });
            }
        }
        return builder.setTableInfoList(tableList);
    }

    /**
     * 开放表信息、预留子类重写
     *
     * @param builder 配置信息
     * @return ignore
     */
    protected Collection<TableInfo> getAllTableInfoList(ConfigBuilder builder) {
        return builder.getTableInfoList();
    }

}
