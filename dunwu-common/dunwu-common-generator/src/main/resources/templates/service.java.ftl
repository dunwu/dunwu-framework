package ${package.Service};

import ${package.Dto}.${table.dtoName};
import ${superServiceClassPackage};

import java.io.IOException;
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

    void exportDtoList(List<${table.dtoName}> list, HttpServletResponse response) throws IOException;

}
</#if>
