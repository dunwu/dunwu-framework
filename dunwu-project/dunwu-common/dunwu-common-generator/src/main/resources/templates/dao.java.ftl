package ${package.Dao};

import ${package.Entity}.${entity};
import ${superDaoClassPackage};

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

}
</#if>
