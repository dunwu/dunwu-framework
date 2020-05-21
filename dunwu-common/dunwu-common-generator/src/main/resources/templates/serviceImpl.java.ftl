package ${package.ServiceImpl};

import ${package.Entity}.${entity};
import ${package.Dto}.${table.dtoName};
import ${package.Dao}.${table.daoName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletResponse;

/**
 * ${table.comment!} Service 类
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass} implements ${table.serviceName} {

    private final ${table.daoName} dao;

    public SysDictServiceImpl(${table.daoName} dao) {
        this.dao = dao;
    }

    @Override
    public boolean save(${entity} entity) {
        return dao.save(entity);
    }

    @Override
    public boolean updateById(${entity} entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean removeById(String id) {
        return dao.removeById(id);
    }

    @Override
    public boolean removeByIds(Set<String> ids) {
        return dao.removeByIds(ids);
    }

    @Override
    public Page<${table.dtoName}> pojoPageByQuery(Object query, Pageable pageable) {
        return dao.pojoPageByQuery(query, pageable, ${table.dtoName}.class);
    }

    @Override
    public List<${table.dtoName}> pojoListByQuery(Object query) {
        return dao.pojoListByQuery(query, ${table.dtoName}.class);
    }

    @Override
    public ${table.dtoName} pojoById(String id) {
        return dao.pojoById(id, ${table.dtoName}.class);
    }

    @Override
    public ${table.dtoName} pojoByQuery(Object query) {
        return dao.pojoByQuery(query, ${table.dtoName}.class);
    }

    @Override
    public Integer countByQuery(Object query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportByIds(Set<String> ids, HttpServletResponse response) throws IOException {
    List<${table.dtoName}> list = dao.pojoListByIds(ids, ${table.dtoName}.class);
        dao.exportDtoList(list, response);
    }

    @Override
    public void exportPageData(Object query, Pageable pageable, HttpServletResponse response) throws IOException {
    Page<${table.dtoName}> page = dao.pojoPageByQuery(query, pageable, ${table.dtoName}.class);
        dao.exportDtoList(page.getContent(), response);
    }

}
</#if>
