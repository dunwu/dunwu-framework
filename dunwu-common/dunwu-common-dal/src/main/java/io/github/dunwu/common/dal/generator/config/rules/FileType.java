package io.github.dunwu.common.dal.generator.config.rules;

/**
 * 代码生成文件类型枚举。
 * <p>
 * 定义了代码生成器支持生成的所有文件类型，包括后端（Entity, DAO, Service 等）和前端（API, List, Form）文件，
 * 以及它们对应的配置 Key 和模板路径。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-02-13
 */
public enum FileType {

    // ==================== 前端文件类型 ====================

    /**
     * 前端 API 接口文件（JavaScript/TypeScript）
     */
    API("Api", "api_path", "/templates/frontend/api.js"),

    /**
     * 前端列表页文件（Vue 组件）
     */
    LIST("List", "list_path", "/templates/frontend/list.vue"),

    /**
     * 前端表单页文件（Vue 组件）
     */
    FORM("Form", "form_path", "/templates/frontend/form.vue"),

    // ==================== 后端文件类型 ====================

    /**
     * MyBatis Mapper XML 文件
     */
    XML("Xml", "xml_path", "/templates/backend/mapper.xml"),

    /**
     * 实体类文件（Entity/Model）
     */
    ENTITY("Entity", "entity_path", "/templates/backend/entity.java"),

    /**
     * 数据传输对象（DTO）文件
     */
    DTO("Dto", "dto_path", "/templates/backend/dto.java"),

    /**
     * 查询条件对象（Query）文件
     */
    QUERY("Query", "query_path", "/templates/backend/query.java"),

    /**
     * MyBatis Mapper 接口文件
     */
    MAPPER("Mapper", "mapper_path", "/templates/backend/mapper.java"),

    /**
     * DAO 接口文件
     */
    DAO("Dao", "dao_path", "/templates/backend/dao.java"),

    /**
     * DAO 实现类文件
     */
    DAO_IMPL("DaoImpl", "dao_impl_path", "/templates/backend/daoImpl.java"),

    /**
     * Service 接口文件
     */
    SERVICE("Service", "service_path", "/templates/backend/service.java"),

    /**
     * Service 实现类文件
     */
    SERVICE_IMPL("ServiceImpl", "service_impl_path", "/templates/backend/serviceImpl.java"),

    /**
     * Controller 控制器文件
     */
    CONTROLLER("Controller", "controller_path", "/templates/backend/controller.java"),

    /**
     * 其他类型（用于自定义扩展）
     */
    OTHER("Other", null, null);

    /**
     * 文件类型代码标识
     */
    private final String code;

    /**
     * 文件路径在配置中的 Key
     */
    private final String filePath;

    /**
     * 对应的 Freemarker 模板路径
     */
    private final String templatePath;

    /**
     * 构造方法
     *
     * @param code         类型代码标识
     * @param filePath     文件路径在配置中的 Key
     * @param templatePath 对应的模板路径
     */
    FileType(String code, String filePath, String templatePath) {
        this.code = code;
        this.filePath = filePath;
        this.templatePath = templatePath;
    }

    /**
     * 获取类型代码标识
     *
     * @return 类型代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 获取文件路径在配置中的 Key
     *
     * @return 配置 Key
     */
    public String getFilePath() {
        return filePath;
    }

    /**
     * 获取对应的模板路径
     *
     * @return 模板路径
     */
    public String getTemplatePath() {
        return templatePath;
    }
}
