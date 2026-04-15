package io.github.dunwu.common.dal.generator.config;

import io.github.dunwu.common.dal.generator.config.po.TableInfo;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 自定义输出文件配置抽象类
 * <p>
 * 用于定义代码生成器中各类文件（如 Entity, Mapper, Service 等）的模板路径及输出路径规则。
 * 继承此类并实现 {@link #outputFile(TableInfo)} 方法即可灵活控制生成文件的输出位置和名称。
 *
 * @author hubin
 * @since 2017-01-18
 */
@Data
@Accessors(chain = true)
public abstract class FileOutConfig {

    /**
     * 模板文件路径
     * <p>
     * 例如："/templates/entity.java.ftl"
     */
    private String templatePath;

    /**
     * 默认构造方法
     */
    public FileOutConfig() {
        // to do nothing
    }

    /**
     * 构造方法
     *
     * @param templatePath 模板文件路径
     */
    public FileOutConfig(String templatePath) {
        this.templatePath = templatePath;
    }

    /**
     * 获取输出文件路径
     * <p>
     * 根据表信息动态计算生成文件的最终输出路径。
     *
     * @param tableInfo 表信息对象
     * @return 输出文件的完整路径
     */
    public abstract String outputFile(TableInfo tableInfo);

}
