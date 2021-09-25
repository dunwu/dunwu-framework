package ${package.DaoImpl};

import io.github.dunwu.tool.data.annotation.Dao;
import ${superDaoImplClassPackage};
import ${package.Dao}.${table.daoName};
import ${package.Mapper}.${table.mapperName};
import ${package.Entity}.${entity};

/**
 * ${table.comment!} Dao 类
 *
 * @author ${author}
 * @since ${date}
 */
@Dao
<#if enableKotlin>
open class ${table.daoImplName} : ${superDaoImplClass}<${table.mapperName}, ${entity}>(), ${table.daoName} {

}
<#else>
public class ${table.daoImplName} extends ${superDaoImplClass}<${table.mapperName}, ${entity}> implements ${table.daoName} {

}
</#if>
