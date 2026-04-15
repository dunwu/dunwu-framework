package io.github.dunwu.common.dal.generator;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.Version;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import freemarker.template.TemplateException;
import io.github.dunwu.common.dal.generator.config.builder.ConfigBuilder;
import io.github.dunwu.common.dal.generator.config.po.TableInfo;
import io.github.dunwu.common.dal.generator.engine.AbstractTemplateEngine;
import io.github.dunwu.common.dal.generator.engine.CodeGenerateContentDto;
import io.github.dunwu.common.dal.generator.engine.FreemarkerTemplateEngine;
import lombok.Data;
import lombok.experimental.Accessors;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 代码生成器核心类
 * <p>
 * 负责协调配置信息、初始化模板引擎，并执行代码的预览与生成流程。
 * 支持生成 Entity, DTO, Mapper, Service, Controller 等多种类型的代码文件。
 * 通过 {@link ConfigBuilder} 聚合数据源、全局配置、策略配置等信息。
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
@Slf4j
@Data
@Accessors(chain = true)
public class CodeGenerator {

    /**
     * 配置构造器，包含全局配置、策略配置、包配置等
     */
    protected ConfigBuilder builder;

    /**
     * 模板引擎实例，用于渲染模板并输出文件
     */
    private AbstractTemplateEngine templateEngine;

    /**
     * 构造代码生成器
     *
     * @param builder 配置构造器实例
     */
    public CodeGenerator(ConfigBuilder builder) {
        assert builder != null;
        this.builder = builder;

        if (null == templateEngine) {
            // 为了兼容之前逻辑，采用 Freemarker 引擎 【 默认 】
            templateEngine = new FreemarkerTemplateEngine();
        }
        // 模板引擎初始化执行文件输出
        templateEngine.init(this.pretreatmentConfigBuilder(builder));
    }

    /**
     * 生成所有配置的代码文件
     * <p>
     * 包括创建必要的目录、执行模板渲染并输出文件，最后可选打开输出目录。
     */
    public void generateAll() {
        log.debug(">>>>>>>> 准备自动生成源码文件");

        // 模板引擎初始化执行文件输出
        templateEngine.mkdirs().generateAll().open();
        log.debug("<<<<<<<< 自动生成源码文件完成");
    }

    /**
     * 生成代码预览内容
     * <p>
     * 返回所有待生成文件的内容列表，用于在生成前进行预览检查。
     *
     * @return 包含文件类型和生成内容的列表
     */
    public List<CodeGenerateContentDto> preview() {
        log.debug(">>>>>>>> 准备自动生成源码文件预览列表");

        List<CodeGenerateContentDto> list = new ArrayList<>();
        try {
            list.addAll(templateEngine.preview());
            log.debug("<<<<<<<< 自动生成源码文件预览列表完成");
        } catch (IOException | TemplateException e) {
            log.error("预览代码失败", e);
        }

        return list;
    }

    /**
     * 预处理配置信息
     * <p>
     * 在代码生成前对配置进行最终调整，例如注入自定义配置、处理表信息、
     * 添加必要的导入包（如 ActiveRecord 模式、逻辑删除、乐观锁等注解）。
     *
     * @param builder 总配置信息
     * @return 处理后的配置构造器实例
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
     * 获取所有表信息列表
     * <p>
     * 开放表信息获取，预留子类重写以自定义表信息的加载逻辑。
     *
     * @param builder 配置信息
     * @return 表信息集合
     */
    protected Collection<TableInfo> getAllTableInfoList(ConfigBuilder builder) {
        return builder.getTableInfoList();
    }

}
