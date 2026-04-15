package io.github.dunwu.common.dal.generator.config;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.annotation.IdType;
import io.github.dunwu.common.dal.generator.config.rules.DateType;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.File;

/**
 * 代码生成器全局配置
 * <p>
 * 用于配置代码生成过程中的全局参数，如输出目录、作者信息、主键策略、日期类型处理
 * 以及是否开启特定功能（如 Swagger、ActiveRecord 等）。
 *
 * @author hubin
 * @since 2016-12-02
 */
@Data
@ToString
@Accessors(chain = true)
public class GlobalConfig {

    /**
     * 是否开启权限校验
     */
    private boolean enablePermission = false;

    /**
     * 是否开启文件覆盖模式
     * <p>
     * 如果为 true，生成代码时会覆盖已存在的同名文件
     */
    private boolean enableOverride = true;

    /**
     * 是否开启 Swagger2 模式
     * <p>
     * 如果为 true，生成的实体类会包含 Swagger 注解
     */
    private boolean enableSwagger = true;

    /**
     * 是否开启 EasyExcel 支持
     */
    private boolean enableEasyExcel = false;

    /**
     * 是否自动打开输出目录
     */
    private boolean open = false;

    /**
     * 是否开启 ActiveRecord 模式
     */
    private boolean enableActiveRecord = false;

    /**
     * 是否开启 BaseResultMap
     * <p>
     * 如果为 true，Mapper XML 中会生成 BaseResultMap
     */
    private boolean enableBaseResultMap = false;

    /**
     * 是否在 XML 中添加二级缓存配置
     */
    private boolean enableCache = false;

    /**
     * 是否开启 BaseColumnList
     * <p>
     * 如果为 true，Mapper XML 中会生成 BaseColumnList
     */
    private boolean enableBaseColumnList = false;

    /**
     * 开发人员姓名，用于生成代码注释中的作者信息
     */
    private String author;

    /**
     * 生成文件的根输出目录
     * <p>
     * 默认为 "D://"
     */
    private String outputDir = "D://";

    /**
     * 后端代码生成路径
     * <p>
     * 如果未指定，默认使用 {@link #outputDir} 下的 "backend" 目录
     */
    private String backendDir;

    /**
     * 前端代码生成路径
     * <p>
     * 如果未指定，默认使用 {@link #outputDir} 下的 "frontend" 目录
     */
    private String frontendDir;

    /**
     * 基础包路径
     */
    private String packagePath;

    /**
     * 指定生成的主键 ID 类型
     * <p>
     * 默认为 {@link IdType#NONE}
     */
    private IdType idType = IdType.NONE;

    /**
     * 时间类型对应策略
     * <p>
     * 默认为 {@link DateType#TIME_PACK}
     */
    private DateType dateType = DateType.TIME_PACK;

    /**
     * 日期格式化模式
     * <p>
     * 默认为 {@link DatePattern#NORM_DATETIME_PATTERN}
     */
    private String datePattern = DatePattern.NORM_DATETIME_PATTERN;

    // =========================================================
    // 各层文件名称方式，例如： %sAction 生成 UserAction %s 为占位符
    // =========================================================

    /**
     * Mapper XML 文件名称后缀
     */
    private String xmlName;

    /**
     * 实体类名称后缀
     */
    private String entityName;

    /**
     * Mapper 接口名称后缀
     */
    private String mapperName;

    /**
     * DAO 接口名称后缀
     */
    private String daoName;

    /**
     * DAO 实现类名称后缀
     */
    private String daoImplName;

    /**
     * Service 接口名称后缀
     */
    private String serviceName;

    /**
     * Service 实现类名称后缀
     */
    private String serviceImplName;

    /**
     * Controller 类名称后缀
     */
    private String controllerName;

    /**
     * API 接口类名称后缀
     */
    private String apiName;

    /**
     * 列表页/VO 类名称后缀
     */
    private String listName;

    /**
     * 表单页/DTO 类名称后缀
     */
    private String formName;

    /**
     * 默认构造方法
     */
    public GlobalConfig() { }

    /**
     * 获取后端代码生成路径
     * <p>
     * 如果 {@link #backendDir} 为空，则自动拼接 {@link #outputDir} 和 "backend"
     *
     * @return 后端代码生成目录路径
     */
    public String getBackendDir() {
        if (StrUtil.isBlank(this.backendDir)) {
            this.backendDir = this.outputDir + File.separator + "backend";
        }
        return this.backendDir;
    }

    /**
     * 获取前端代码生成路径
     * <p>
     * 如果 {@link #frontendDir} 为空，则自动拼接 {@link #outputDir} 和 "frontend"
     *
     * @return 前端代码生成目录路径
     */
    public String getFrontendDir() {
        if (StrUtil.isBlank(this.frontendDir)) {
            this.frontendDir = this.outputDir + File.separator + "frontend";
        }
        return this.frontendDir;
    }

}
