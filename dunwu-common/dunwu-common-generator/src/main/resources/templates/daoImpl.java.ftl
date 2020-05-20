package ${package.DaoImpl};

import io.github.dunwu.data.core.annotation.Dao;
import ${package.Dao}.${table.daoName};
import ${package.Mapper}.${table.mapperName};
import ${package.Entity}.${entity};
import ${package.Dto}.${table.dtoName};
import ${superDaoImplClassPackage};
import io.github.dunwu.web.util.ServletUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * ${table.comment!} Dao 类
 *
 * @author ${author}
 * @since ${date}
 */
@Dao
<#if kotlin>
open class ${table.daoImplName} : ${superDaoImplClass}<${table.mapperName}, ${entity}>(), ${table.daoName} {

}
<#else>
public class ${table.daoImplName} extends ${superDaoImplClass}<${table.mapperName}, ${entity}> implements ${table.daoName} {

    @Override
    public void exportDtoList(List<${table.dtoName}> list, HttpServletResponse response) throws IOException {
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
        ServletUtil.downloadExcel(mapList, response);
    }

}
</#if>
