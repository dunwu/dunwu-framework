package ${package.ServiceImpl};

import cn.hutool.core.bean.BeanUtil;
import ${package.Entity}.${entity};
import ${package.Dto}.${table.dtoName};
import ${package.Query}.${table.queryName};
import ${package.Dao}.${table.daoName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import io.github.dunwu.tool.web.ServletUtil;

import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * ${table.comment!} Service 类
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if enableKotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass} implements ${table.serviceName} {

    private final ${table.daoName} dao;

    public ${table.serviceImplName}(${table.daoName} dao) {
        this.dao = dao;
    }

    @Override
    public boolean insert(${entity} entity) {
        return dao.insert(entity);
    }

    @Override
    public boolean insertBatch(Collection<${entity}> list) {
        return dao.insertBatch(list);
    }

    @Override
    public boolean updateById(${entity} entity) {
        return dao.updateById(entity);
    }

    @Override
    public boolean updateBatchById(Collection<${entity}> list) {
        return dao.updateBatchById(list);
    }

    @Override
    public boolean save(${entity} entity) {
        return dao.save(entity);
    }

    @Override
    public boolean saveBatch(Collection<${entity}> list) {
        return dao.saveBatch(list);
    }

    @Override
    public boolean deleteById(Serializable id) {
        return dao.deleteById(id);
    }

    @Override
    public boolean deleteBatchByIds(Collection<? extends Serializable> ids) {
        return dao.deleteBatchByIds(ids);
    }

    @Override
    public List<${table.dtoName}> pojoList() {
        return dao.pojoList(this::doToDto);
    }

    @Override
    public List<${table.dtoName}> pojoListByIds(Collection<? extends Serializable> ids) {
        return dao.pojoListByIds(ids, this::doToDto);
    }

    @Override
    public List<${table.dtoName}> pojoListByQuery(${table.queryName} query) {
        return dao.pojoListByQuery(query, this::doToDto);
    }

    @Override
    public Page<${table.dtoName}> pojoSpringPageByQuery(${table.queryName} query, Pageable pageable) {
        return dao.pojoSpringPageByQuery(query, pageable, this::doToDto);
    }

    @Override
    public ${table.dtoName} pojoById(Serializable id) {
        return dao.pojoById(id, this::doToDto);
    }

    @Override
    public ${table.dtoName} pojoByQuery(${table.queryName} query) {
        return dao.pojoByQuery(query, this::doToDto);
    }

    @Override
    public Integer countByQuery(${table.queryName} query) {
        return dao.countByQuery(query);
    }

    @Override
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<${table.dtoName}> list = dao.pojoListByIds(ids, this::doToDto);
        exportDtoList(list, response);
    }

    @Override
    public void exportPage(${table.queryName} query, Pageable pageable, HttpServletResponse response) {
        Page<${table.dtoName}> page = dao.pojoSpringPageByQuery(query, pageable, this::doToDto);
        exportDtoList(page.getContent(), response);
    }

    /**
     * 根据传入的 ${table.dtoName} 列表，导出 excel 表单
     *
     * @param list     {@link ${table.dtoName}} 列表
     * @param response {@link HttpServletResponse} 实体
     */
    private void exportDtoList(Collection<${table.dtoName}> list, HttpServletResponse response) {
        List<Map<String, Object>> mapList = new ArrayList<>();
        for (${table.dtoName} item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            <#list table.fields as field>
              <#if field.comment != ''>
            map.put("${field.comment}", item.get${field.propertyName?cap_first}());
              <#else>
            map.put("${field.propertyName}", item.get${field.propertyName?cap_first}());
              </#if>
            </#list>
            mapList.add(map);
        }
        ServletUtil.downloadExcel(response, mapList);
    }

    @Override
    public ${table.dtoName} doToDto(${entity} entity) {
        if (entity == null) {
            return null;
        }

        return BeanUtil.toBean(entity, ${table.dtoName}.class);
    }

    @Override
    public ${entity} dtoToDo(${table.dtoName} dto) {
        if (dto == null) {
            return null;
        }

        return BeanUtil.toBean(dto, ${entity}.class);
    }

}
</#if>
