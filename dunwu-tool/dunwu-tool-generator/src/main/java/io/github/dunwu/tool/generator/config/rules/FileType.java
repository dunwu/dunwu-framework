package io.github.dunwu.tool.generator.config.rules;

/**
 * 生成文件类型
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-02-13
 */
public enum FileType {

    // 前端文件类型
    API("Api", "api_path", "/templates/frontend/api.js"),
    LIST("List", "list_path", "/templates/frontend/list.vue"),
    FORM("Form", "form_path", "/templates/frontend/form.vue"),

    // 后端文件类型
    XML("Xml", "xml_path", "/templates/backend/mapper.xml"),
    ENTITY("Entity", "entity_path", "/templates/backend/entity.java"),
    DTO("Dto", "dto_path", "/templates/backend/dto.java"),
    QUERY("Query", "query_path", "/templates/backend/query.java"),
    MAPPER("Mapper", "mapper_path", "/templates/backend/mapper.java"),
    DAO("Dao", "dao_path", "/templates/backend/dao.java"),
    DAO_IMPL("DaoImpl", "dao_impl_path", "/templates/backend/daoImpl.java"),
    SERVICE("Service", "service_path", "/templates/backend/service.java"),
    SERVICE_IMPL("ServiceImpl", "service_impl_path", "/templates/backend/serviceImpl.java"),
    CONTROLLER("Controller", "controller_path", "/templates/backend/controller.java"),

    OTHER("Other", null, null);

    private final String code;
    private final String filePath;
    private final String templatePath;

    FileType(String code, String filePath, String templatePath) {
        this.code = code;
        this.filePath = filePath;
        this.templatePath = templatePath;
    }

    public String getCode() {
        return code;
    }

    public String getFilePath() {
        return filePath;
    }

    public String getTemplatePath() {
        return templatePath;
    }
}
