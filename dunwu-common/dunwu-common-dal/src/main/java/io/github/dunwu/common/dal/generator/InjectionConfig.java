package io.github.dunwu.common.dal.generator;

import io.github.dunwu.common.dal.generator.config.FileOutConfig;
import io.github.dunwu.common.dal.generator.config.IFileCreate;
import io.github.dunwu.common.dal.generator.config.builder.ConfigBuilder;
import io.github.dunwu.common.dal.generator.config.po.TableInfo;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * 自定义注入配置抽象类
 * <p>
 * 允许用户向代码生成过程中注入自定义的配置、变量或逻辑。
 * 例如：自定义输出文件 ({@link FileOutConfig})、自定义判断是否创建文件 ({@link IFileCreate})、
 * 向模板注入额外的全局变量等。
 *
 * @author hubin
 * @since 2016-12-07
 */
@Data
@Accessors(chain = true)
public abstract class InjectionConfig {

    /**
     * 全局配置构造器
     */
    private ConfigBuilder config;

    /**
     * 自定义返回配置 Map 对象
     * <p>
     * 用于在模板中通过 {@code ${cfg.xxx}} 访问自定义的全局变量。
     */
    private Map<String, Object> map;

    /**
     * 自定义输出文件配置列表
     */
    private List<FileOutConfig> fileOutConfigList;

    /**
     * 自定义判断是否创建文件的逻辑接口
     */
    private IFileCreate fileCreate;

    /**
     * 初始化自定义 Map 对象
     * <p>
     * 用于注入针对所有表的全局参数，实现类需在此方法中初始化 {@link #map}。
     */
    public abstract void initMap();

    /**
     * 初始化表相关的元数据
     * <p>
     * 在生成每个表的代码前调用，允许子类依据表信息从第三方系统获取元数据并注入到当前配置环境中。
     *
     * @param tableInfo 当前正在处理的表信息
     */
    public void initTableMap(TableInfo tableInfo) {
        // 子类可重写此方法以注入表级别的补充信息
    }

    /**
     * 预处理模板渲染的 Object Map
     * <p>
     * 在 {@link io.github.dunwu.common.dal.generator.engine.AbstractTemplateEngine#getObjectMap(TableInfo)}
     * 构建完基础数据后调用，允许对渲染数据进行最终调整。
     *
     * @param objectMap 待渲染的 Map 数据
     * @return 处理后的 Map 数据
     */
    public Map<String, Object> prepareObjectMap(Map<String, Object> objectMap) {
        return objectMap;
    }

}
