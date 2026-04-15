package io.github.dunwu.common.dal.generator.config;

import io.github.dunwu.common.dal.generator.config.rules.FileType;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 代码生成模板路径配置类。
 * <p>
 * 用于定义代码生成器中各层代码（Entity, DTO, DAO, Service, Controller 等）
 * 以及前端页面（API, List, Form）所使用的模板文件路径。
 * 默认使用内置模板，用户可通过设置对应属性来自定义模板路径。
 *
 * @author tzg, hubin
 * @since 2017-06-17
 */
@Data
@Accessors(chain = true)
public class TemplateConfig {

    // =========================================================
    // 后端源码模板文件路径
    // =========================================================

    /**
     * Entity 实体类模板路径
     */
    private String entity = FileType.ENTITY.getTemplatePath();

    /**
     * DTO 数据传输对象模板路径
     */
    private String dto = FileType.DTO.getTemplatePath();

    /**
     * Query 查询条件类模板路径
     */
    private String query = FileType.QUERY.getTemplatePath();

    /**
     * Mapper 接口模板路径
     */
    private String mapper = FileType.MAPPER.getTemplatePath();

    /**
     * DAO 接口模板路径
     */
    private String dao = FileType.DAO.getTemplatePath();

    /**
     * DAO 实现类模板路径
     */
    private String daoImpl = FileType.DAO_IMPL.getTemplatePath();

    /**
     * Service 接口模板路径
     */
    private String service = FileType.SERVICE.getTemplatePath();

    /**
     * Service 实现类模板路径
     */
    private String serviceImpl = FileType.SERVICE_IMPL.getTemplatePath();

    /**
     * Controller 控制器模板路径
     */
    private String controller = FileType.CONTROLLER.getTemplatePath();

    /**
     * Mapper XML 文件模板路径
     */
    private String xml = FileType.XML.getTemplatePath();

    // =========================================================
    // 前端源码模板文件路径
    // =========================================================

    /**
     * 前端 API 接口文件模板路径
     */
    private String api = FileType.API.getTemplatePath();

    /**
     * 前端列表页模板路径
     */
    private String list = FileType.LIST.getTemplatePath();

    /**
     * 前端表单页模板路径
     */
    private String form = FileType.FORM.getTemplatePath();

}
