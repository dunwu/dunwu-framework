package ${package.Service};

import ${package.Entity}.${entity};
import ${package.Dto}.${table.dtoName};
import ${superServiceClassPackage};
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
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

    boolean save(${entity} pojo);

    boolean updateById(${entity} pojo);

    boolean removeById(Serializable id);

    boolean removeByIds(Set<Serializable> ids);

    Page<${table.dtoName}> pojoPageByQuery(Object query, Pageable pageable);

    List<${table.dtoName}> pojoListByQuery(Object query);

    ${table.dtoName} pojoById(Serializable id);

    ${table.dtoName} pojoByQuery(Object query);

    Integer countByQuery(Object query);

    void exportByIds(Set<Serializable> ids, HttpServletResponse response) throws IOException;

    void exportPageData(Object query, Pageable pageable, HttpServletResponse response) throws IOException;

}
</#if>
