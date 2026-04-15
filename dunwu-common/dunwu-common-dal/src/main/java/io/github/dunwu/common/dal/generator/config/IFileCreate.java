package io.github.dunwu.common.dal.generator.config;

import io.github.dunwu.common.dal.generator.config.builder.ConfigBuilder;
import io.github.dunwu.common.dal.generator.config.rules.FileType;

import java.io.File;

/**
 * 文件创建策略接口。
 * <p>
 * 用于自定义代码生成时的文件创建逻辑，例如判断是否覆盖已存在的文件，
 * 以及自动创建输出目录等。实现此接口可以灵活控制代码生成的输出行为。
 *
 * @author hubin
 * @since 2018-08-07
 */
public interface IFileCreate {

    /**
     * 判断是否创建文件。
     * <p>
     * 根据配置、文件类型和路径决定是否生成该文件。
     * 通常用于实现“不覆盖已存在文件”的逻辑。
     *
     * @param configBuilder 配置构建器
     * @param fileType      文件类型（如 Entity, Mapper 等）
     * @param filePath      目标文件路径
     * @return 如果应该创建文件返回 true，否则返回 false
     */
    boolean isCreate(ConfigBuilder configBuilder, FileType fileType, String filePath);

    /**
     * 检查并创建文件目录。
     * <p>
     * 如果文件所在的父目录不存在，则自动递归创建。
     *
     * @param filePath 目标文件路径
     */
    default void checkDir(String filePath) {
        File file = new File(filePath);
        boolean exist = file.exists();
        if (!exist) {
            file.getParentFile().mkdirs();
        }
    }

}
