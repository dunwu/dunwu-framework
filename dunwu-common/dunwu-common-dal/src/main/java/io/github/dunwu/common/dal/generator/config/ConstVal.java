package io.github.dunwu.common.dal.generator.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;

import java.nio.charset.StandardCharsets;

/**
 * 代码生成器常量定义接口
 * <p>
 * 包含代码生成过程中使用的各种常量，如文件后缀、默认父类全限定名、
 * 默认路径配置以及模板路径等。
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-31
 */
public interface ConstVal {

    /**
     * 模块名占位符
     */
    String MODULE_NAME = "ModuleName";

    // =========================================================
    // 模板文件类型后缀
    // =========================================================

    /**
     * Java 源文件后缀
     */
    String JAVA_SUFFIX = StringPool.DOT_JAVA;

    /**
     * Kotlin 源文件后缀
     */
    String KT_SUFFIX = ".kt";

    /**
     * XML 配置文件后缀
     */
    String XML_SUFFIX = ".xml";

    /**
     * JavaScript 脚本文件后缀
     */
    String JS_SUFFIX = ".js";

    /**
     * Vue 组件文件后缀
     */
    String VUE_SUFFIX = ".vue";

    // =========================================================
    // 其他通用常量
    // =========================================================

    /**
     * Java 临时目录系统属性名
     */
    String JAVA_TMPDIR = "java.io.tmpdir";

    /**
     * UTF-8 字符集名称
     */
    String UTF8 = StandardCharsets.UTF_8.name();

    /**
     * 下划线字符
     */
    String UNDERLINE = "_";

    // =========================================================
    // 默认父类全限定名
    // =========================================================

    /**
     * 默认 Mapper 接口父类
     */
    String SUPER_MAPPER_CLASS = "com.baomidou.mybatisplus.core.mapper.BaseMapper";

    /**
     * 默认 DAO 接口父类
     */
    String SUPER_DAO_CLASS = "io.github.dunwu.tool.data.mybatis.IExtDao";

    /**
     * 默认 DAO 实现类父类
     */
    String SUPER_DAO_IMPL_CLASS = "io.github.dunwu.tool.data.mybatis.BaseExtDaoImpl";

    /**
     * 默认 Service 接口父类
     */
    String SUPER_SERVICE_CLASS = "io.github.dunwu.tool.data.mybatis.IService";

    /**
     * 默认 Service 实现类父类
     */
    String SUPER_SERVICE_IMPL_CLASS = "io.github.dunwu.tool.data.mybatis.ServiceImpl";

    // =========================================================
    // 默认路径配置
    // =========================================================

    /**
     * Java 源码根目录相对路径
     */
    String JAVA_PATH = "/src/main/java";

    /**
     * 资源文件根目录相对路径
     */
    String RESOURCES_PATH = "/src/main/resources";

    /**
     * 前端视图根目录相对路径
     */
    String VIEWS_PATH = "/src/views";

    /**
     * 字典代码生成模板路径
     */
    String DICT_TEMPLATE_PATH = "templates/backend/dict.java.ftl";

}
