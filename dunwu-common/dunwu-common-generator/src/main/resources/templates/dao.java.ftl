package ${package.Dao};

import ${package.Entity}.${entity};
import ${package.Dto}.${table.dtoName};
import ${superDaoClassPackage};

import java.io.IOException;
import java.util.Collection;
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

  /**
    * 根据传入的 ${table.dtoName} 列表，导出 excel 表单
    *
    * @param list     {@link ${table.dtoName}} 列表
    * @param response {@link HttpServletResponse} 实体
    */
    void exportDtoList(Collection<${table.dtoName}> list, HttpServletResponse response) throws IOException;

}
</#if>
