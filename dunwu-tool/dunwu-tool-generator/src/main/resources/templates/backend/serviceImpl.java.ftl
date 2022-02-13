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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.core.exception.AppException;
import io.github.dunwu.tool.data.excel.ExcelUtil;
<#if entityLombokModel>
import lombok.extern.slf4j.Slf4j;
<#else>
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
</#if>
<#if table.enableLog>
import io.github.dunwu.tool.web.log.annotation.OperationLog;
import io.github.dunwu.tool.web.log.constant.OperationType;
</#if>

import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import javax.servlet.http.HttpServletResponse;

/**
 * ${table.comment!} Service 类
 *
 * @author ${author}
 * @since ${date}
 */
<#if entityLombokModel>
@Slf4j
</#if>
@Service
public class ${table.serviceImplName} extends ${superServiceImplClass} implements ${table.serviceName} {

    <#if !entityLombokModel>
    private static final Logger log = LoggerFactory.getLogger(${table.serviceImplName}.class);
    </#if>

    private final ${table.daoName} dao;

    public ${table.serviceImplName}(${table.daoName} dao) {
        this.dao = dao;
    }

    @Override
    <#if table.enableLog>
    @OperationLog(bizType = "${table.comment!}", operation = OperationType.ADD)
    </#if>
    public boolean insert(${entity} entity) {
        return dao.insert(entity);
    }

    @Override
    <#if table.enableLog>
    @OperationLog(bizType = "${table.comment!}", operation = OperationType.BATCH_ADD)
    </#if>
    public boolean insertBatch(Collection<${entity}> list) {
        return dao.insertBatch(list);
    }

    @Override
    <#if table.enableLog>
    @OperationLog(bizType = "${table.comment!}", operation = OperationType.EDIT, bizNo = "{{#entity.id}}")
    </#if>
    public boolean updateById(${entity} entity) {
        return dao.updateById(entity);
    }

    @Override
    <#if table.enableLog>
    @OperationLog(bizType = "${table.comment!}", operation = OperationType.BATCH_EDIT)
    </#if>
    public boolean updateBatchById(Collection<${entity}> list) {
        return dao.updateBatchById(list);
    }

    @Override
    <#if table.enableLog>
    @OperationLog(bizType = "${table.comment!}", operation = OperationType.SAVE, bizNo = "{{#entity.id}}")
    </#if>
    public boolean save(${entity} entity) {
        return dao.save(entity);
    }

    @Override
    <#if table.enableLog>
    @OperationLog(bizType = "${table.comment!}", operation = OperationType.BATCH_SAVE)
    </#if>
    public boolean saveBatch(Collection<${entity}> list) {
        return dao.saveBatch(list);
    }

    @Override
    <#if table.enableLog>
    @OperationLog(bizType = "${table.comment!}", operation = OperationType.DEL, bizNo = "{{#id}}")
    </#if>
    public boolean deleteById(Serializable id) {
        return dao.deleteById(id);
    }

    @Override
    <#if table.enableLog>
    @OperationLog(bizType = "${table.comment!}", operation = OperationType.BATCH_DEL, bizNo = "{{#ids}}")
    </#if>
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
    public Page<${table.dtoName}> pojoSpringPageByQuery(Pageable pageable, ${table.queryName} query) {
        return dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
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
    @Transactional(rollbackFor = { Exception.class })
    @OperationLog(bizType = "${table.comment!}", operation = OperationType.IMPORT_EXCEL,
        success = "导入${table.comment!}(Excel文件：{{#file.getOriginalFilename()}})『成功』",
        fail = "导入${table.comment!}(Excel文件：{{#file.getOriginalFilename()}})『失败』"
    )
    public void importList(MultipartFile file) {
        try {
            ExcelUtil.saveExcelData(file.getInputStream(), ${entity}.class, dao);
        } catch (IOException e) {
            log.error("【${table.comment!}】【导入失败】", e);
            throw new AppException(ResultStatus.IO_ERROR.getCode(), "【${table.comment!}】【导入失败】");
        }
    }

    @Override
    <#if table.enableLog>
    @OperationLog(bizType = "${table.comment!}", operation = OperationType.EXPORT_EXCEL, bizNo = "{{#ids}}")
    </#if>
    public void exportList(Collection<? extends Serializable> ids, HttpServletResponse response) {
        List<${table.dtoName}> list = dao.pojoListByIds(ids, this::doToDto);
        <#if table.enableEasyExcel>
        try {
            ExcelUtil.downloadEasyExcel(response, list, DictDto.class);
        } catch (IOException e) {
            log.error("【${table.comment!}】【导出失败】", e);
            throw new AppException(ResultStatus.IO_ERROR.getCode(), "【${table.comment!}】【导出失败】");
        }
        <#else>
        exportDtoList(page.getContent(), response);
        </#if>
    }

    @Override
    <#if table.enableLog>
    @OperationLog(bizType = "${table.comment!}", operation = OperationType.EXPORT_EXCEL,
        success = "分页查询导出${table.comment!}(page={{#pageable.getPageNumber()}}, size={{#pageable.getPageSize()}}, query={{#query.toString()}})『成功』",
        fail = "分页查询导出${table.comment!}(page={{#pageable.getPageNumber()}}, size={{#pageable.getPageSize()}}, query={{#query.toString()}})『失败』"
    )
    </#if>
    public void exportPage(Pageable pageable, ${table.queryName} query, HttpServletResponse response) {
        Page<${table.dtoName}> page = dao.pojoSpringPageByQuery(pageable, query, this::doToDto);
        <#if table.enableEasyExcel>
        try {
            ExcelUtil.downloadEasyExcel(response, page.getContent(), DictDto.class);
        } catch (IOException e) {
            log.error("【${table.comment!}】【导出失败】", e);
            throw new AppException(ResultStatus.IO_ERROR.getCode(), "【${table.comment!}】【导出失败】");
        }
        <#else>
        exportDtoList(page.getContent(), response);
        </#if>
    }

    <#if !table.enableEasyExcel>
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
        ExcelUtil.downloadExcel(response, mapList);
    }
    </#if>

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
