package ${package.Service};

import io.github.dunwu.tool.data.annotation.QueryField;
import ${package.Entity}.${entity};
import ${package.Dto}.${table.dtoName};
import ${package.Query}.${table.queryName};
import ${superServiceClassPackage};
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
<#if enableKotlin>
interface ${table.serviceName} : ${superServiceClass}
<#else>
public interface ${table.serviceName} extends ${superServiceClass} {

    /**
     * 添加一条 {@link ${entity}} 记录
     *
     * @param entity {@link ${entity}} 数据实体
     * @return true / false
     */
    boolean insert(${entity} entity);

    /**
     * 批量添加 {@link ${entity}} 记录
     *
     * @param list {@link ${entity}} 数据实体列表
     * @return true / false
     */
    boolean insertBatch(Collection<${entity}> list);

    /**
     * 根据 ID 更新一条 {@link ${entity}} 记录
     *
     * @param entity {@link ${entity}} 数据实体
     * @return true / false
     */
    boolean updateById(${entity} entity);

    /**
     * 根据 ID 批量更新 {@link ${entity}} 记录
     *
     * @param list {@link ${entity}} 数据实体列表
     * @return true / false
     */
    boolean updateBatchById(Collection<${entity}> list);

    /**
     * 添加或更新一条 {@link ${entity}} 记录
     *
     * @param entity {@link ${entity}} 数据实体
     * @return true / false
     */
    boolean save(${entity} entity);

    /**
     * 批量添加或更新 {@link ${entity}} 记录
     *
     * @param list {@link ${entity}} 数据实体列表
     * @return true / false
     */
    boolean saveBatch(Collection<${entity}> list);

    /**
     * 根据 ID 删除一条 {@link ${entity}} 记录
     *
     * @param id {@link ${entity}} 主键
     * @return true / false
     */
    boolean deleteById(Serializable id);

    /**
     * 根据 ID 列表批量删除 {@link ${entity}} 记录
     *
     * @param ids {@link ${entity}} 主键列表
     * @return true / false
     */
    boolean deleteBatchByIds(Collection<? extends Serializable> ids);

    /**
     * 查询 {@link ${table.dtoName}} 全量数据列表
     *
     * @return {@link List<${table.dtoName}>}
     */
    List<${table.dtoName}> pojoList();

    /**
    * 根据 ID 列表查询 {@link ${table.dtoName}} 列表
    *
    * @param ids {@link ${entity}} 主键列表
    * @return {@link List<${table.dtoName}>}
    */
    List<${table.dtoName}> pojoListByIds(Collection<? extends Serializable> ids);

    /**
     * 根据 {@link ${table.queryName}} 查询 {@link ${table.dtoName}} 列表
     *
     * @param query 查询条件，根据 {@link ${table.queryName}} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link List<${table.dtoName}>}
     */
    List<${table.dtoName}> pojoListByQuery(${table.queryName} query);

    /**
     * 根据 {@link ${table.queryName}} 和 {@link Pageable} 分页查询 {@link ${table.dtoName}} 列表
     *
     * @param query    查询条件，根据 {@link ${table.queryName}} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @return {@link Page<${table.dtoName}>}
     */
    Page<${table.dtoName}> pojoSpringPageByQuery(${table.queryName} query, Pageable pageable);

    /**
     * 根据 id 查询 {@link ${table.dtoName}}
     *
     * @param id {@link ${entity}} 主键
     * @return {@link ${table.dtoName}}
     */
    ${table.dtoName} pojoById(Serializable id);

    /**
     * 根据 {@link ${table.queryName}} 查询 {@link ${table.dtoName}} 列表
     *
     * @param query 查询条件，根据 {@link ${table.queryName}} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link ${table.dtoName}}
     */
    ${table.dtoName} pojoByQuery(${table.queryName} query);

    /**
     * 根据 {@link ${table.queryName}} 查询匹配条件的记录数
     *
     * @param query 查询条件，根据 {@link ${table.queryName}} 中的 {@link QueryField} 注解自动组装查询条件
     * @return {@link Integer}
     */
    Integer countByQuery(${table.queryName} query);

    /**
     * 根据 id 列表查询 {@link ${table.dtoName}} 列表，并导出 excel 表单
     *
     * @param ids      id 列表
     * @param response {@link HttpServletResponse} 实体
     */
    void exportList(Collection<? extends Serializable> ids, HttpServletResponse response);

    /**
     * 根据 {@link ${table.queryName}} 和 {@link Pageable} 分页查询 {@link ${table.dtoName}} 列表，并导出 excel 表单
     *
     * @param query    查询条件，根据 {@link ${table.queryName}} 中的 {@link QueryField} 注解自动组装查询条件
     * @param pageable 分页查询条件
     * @param response {@link HttpServletResponse} 实体
     */
    void exportPage(${table.queryName} query, Pageable pageable, HttpServletResponse response);

    /**
     * 将 {@link ${entity}} 转为 {@link ${table.dtoName}}
     *
     * @param entity 数据实体
     * @return /
     */
    ${table.dtoName} doToDto(${entity} entity);

    /**
     * 将 {@link ${table.dtoName}} 转为 {@link ${entity}}
     *
     * @param dto Dto 实体
     * @return /
     */
    ${entity} dtoToDo(${table.dtoName} dto);

}
</#if>
