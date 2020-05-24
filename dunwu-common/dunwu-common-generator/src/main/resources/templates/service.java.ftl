package ${package.Service};

import io.github.dunwu.data.core.annotation.QueryField;
import ${package.Entity}.${entity};
import ${package.Dto}.${table.dtoName};
import ${superServiceClassPackage};
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * ${table.comment!} Service 接口
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.serviceName} : ${superServiceClass}
<#else>
public interface ${table.serviceName} extends ${superServiceClass} {

    /**
     * 添加一条 {@link ${entity}} 记录
     *
     * @param entity {@link ${entity}} 数据实体
     * @return true / false
     */
    boolean save(${entity} entity);

    /**
     * 根据 ID 更新一条 {@link ${entity}} 记录
     *
     * @param entity {@link ${entity}} 数据实体
     * @return true / false
     */
    boolean updateById(${entity} entity);

    /**
     * 根据 ID 删除一条 {@link ${entity}} 记录
     *
     * @param id {@link ${entity}} 主键
     * @return true / false
     */
    boolean removeById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link ${entity}} 记录
     *
     * @param ids {@link ${entity}} 主键列表
     * @return true / false
     */
    boolean removeByIds(Set<Serializable> ids);

    /**
     * 根据 query 和 pageable 分页查询 {@link ${table.dtoName}}
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<${table.dtoName}>}
     */
    Page<${table.dtoName}> pojoPageByQuery(Object query, Pageable pageable);

    /**
     * 根据 query 查询 {@link ${table.dtoName}} 列表
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<${table.dtoName}>}
     */
    List<${table.dtoName}> pojoListByQuery(Object query);

    /**
     * 根据 id 查询 {@link ${table.dtoName}}
     *
     * @param id {@link ${entity}} 主键
     * @return {@link List<${table.dtoName}>}
     */
    ${table.dtoName} pojoById(Serializable id);

    /**
     * 根据 query 查询 {@link ${table.dtoName}}
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<${table.dtoName}>}
     */
    ${table.dtoName} pojoByQuery(Object query);

    /**
     * 根据 query 查询满足条件的记录数
     *
     * @param query 查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(Object query);

    /**
     * 根据 id 列表查询 {@link ${table.dtoName}} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportByIds(Set<Serializable> ids, HttpServletResponse response) throws IOException;

    /**
     * 根据 query 和 pageable 查询 {@link ${table.dtoName}} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 query 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPageData(Object query, Pageable pageable, HttpServletResponse response) throws IOException;


}
</#if>
