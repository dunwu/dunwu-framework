package io.github.dunwu.common.dal.generator.config;

import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Map;

/**
 * 代码生成器包路径配置
 * <p>
 * 用于定义生成代码时各层（Entity, DAO, Service, Controller 等）的包名及路径信息。
 * 支持设置父包名和模块名，以便自动生成完整的包路径。
 *
 * @author YangHu, tangguo, hubin
 * @since 2016-08-30
 */
@Data
@Accessors(chain = true)
public class PackageConfig {

    /**
     * 父包名
     * <p>
     * 如果为空，则子包名必须填写完整路径；否则子包名只需填写相对于父包的路径。
     * 默认为 "com.baomidou"
     */
    private String parent = "com.baomidou";

    /**
     * 父包模块名
     * <p>
     * 如果设置，最终生成的包路径将是 {@link #parent} + "." + {@link #moduleName}
     */
    private String moduleName = null;

    /**
     * Entity 实体类包名
     * <p>
     * 默认为 "entity"
     */
    private String entity = "entity";

    /**
     * DTO 数据传输对象包名
     * <p>
     * 默认为 "entity.dto"
     */
    private String dto = "entity.dto";

    /**
     * Query 查询条件类包名
     * <p>
     * 默认为 "entity.query"
     */
    private String query = "entity.query";

    /**
     * DAO 接口包名
     * <p>
     * 默认为 "dao"
     */
    private String dao = "dao";

    /**
     * DAO 实现类包名
     * <p>
     * 默认为 "dao.impl"
     */
    private String daoImpl = "dao.impl";

    /**
     * Service 接口包名
     * <p>
     * 默认为 "service"
     */
    private String service = "service";

    /**
     * Service 实现类包名
     * <p>
     * 默认为 "service.impl"
     */
    private String serviceImpl = "service.impl";

    /**
     * Mapper 接口包名
     * <p>
     * 默认为 "dao.mapper"
     */
    private String mapper = "dao.mapper";

    /**
     * Mapper XML 文件包名
     * <p>
     * 默认为 "mapper"
     */
    private String xml = "mapper";

    /**
     * Controller 控制器包名
     * <p>
     * 默认为 "controller"
     */
    private String controller = "controller";

    /**
     * 自定义路径配置信息
     * <p>
     * Key 为文件类型（如 Entity, Mapper 等），Value 为对应的输出路径
     */
    private Map<String, String> pathInfo;

    /**
     * 默认构造方法
     */
    public PackageConfig() { }

    /**
     * 构造方法
     *
     * @param parent     父包名
     * @param moduleName 模块名
     */
    public PackageConfig(String parent, String moduleName) {
        this.parent = parent;
        this.moduleName = moduleName;
    }

    /**
     * 获取完整的父包名
     * <p>
     * 如果设置了 {@link #moduleName}，则返回 "parent.moduleName"；否则返回 {@link #parent}。
     *
     * @return 完整的父包名
     */
    public String getParent() {
        if (StringUtils.isNotBlank(moduleName)) {
            return parent + StringPool.DOT + moduleName;
        }
        return parent;
    }

}
