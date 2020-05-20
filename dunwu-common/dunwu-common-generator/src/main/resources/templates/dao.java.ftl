package ${package.Dao};

import ${package.Entity}.${entity};
import ${package.Dto}.${table.dtoName};
import ${superDaoClassPackage};

import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

/**
 * ${table.comment!} Dao 接口
 *
 * @author ${author}
 * @since ${date}
 */
<#if kotlin>
interface ${table.daoName} : ${superDaoClass}<${entity}>
<#else>
public interface ${table.daoName} extends ${superDaoClass}<${entity}> {

    void exportDtoList(List<${table.dtoName}> list, HttpServletResponse response) throws IOException;

}
</#if>
