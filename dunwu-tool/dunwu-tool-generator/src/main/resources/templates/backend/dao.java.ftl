package ${package.Dao};

import ${superDaoClassPackage};
import ${package.Entity}.${entity};

/**
 * ${table.comment!} Dao 接口
 *
 * @author ${author}
 * @since ${date}
 */
<#if enableKotlin>
interface ${table.daoName} : ${superDaoClass}<${entity}>
<#else>
public interface ${table.daoName} extends ${superDaoClass}<${entity}> {

}
</#if>
