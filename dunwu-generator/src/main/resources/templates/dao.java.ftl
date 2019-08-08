package ${package.Dao};

import ${package.Entity}.${entity};
import ${superDaoClassPackage};

/**
 * <p>
 * ${table.comment!} DAO 接口
 * </p>
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
