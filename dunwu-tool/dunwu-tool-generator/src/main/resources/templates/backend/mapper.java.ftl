package ${package.Mapper};

import ${superMapperClassPackage};
import ${package.Entity}.${entity};

/**
 * ${table.comment!} Mapper 接口
 *
 * @author ${author}
 * @since ${date}
 */
<#if enableKotlin>
interface ${table.mapperName} : ${superMapperClass}<${entity}>
<#else>
public interface ${table.mapperName} extends ${superMapperClass}<${entity}> {

}
</#if>
