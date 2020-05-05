package ${package.ServiceImpl};

import ${package.Dto}.${table.dtoName};
import ${package.Service}.${table.serviceName};
import ${superServiceImplClassPackage};
import io.github.dunwu.web.util.ServletUtil;
<#if swagger2>
import lombok.RequiredArgsConstructor;
</#if>
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

/**
 * ${table.comment!} Service 类
 *
 * @author ${author}
 * @since ${date}
 */
@Service
@RequiredArgsConstructor
<#if kotlin>
open class ${table.serviceImplName} : ${superServiceImplClass}(), ${table.serviceName} {

}
<#else>
public class ${table.serviceImplName} extends ${superServiceImplClass} implements ${table.serviceName} {

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
