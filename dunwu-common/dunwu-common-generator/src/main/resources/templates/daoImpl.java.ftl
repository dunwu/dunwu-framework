package ${package.DaoImpl};

import io.github.dunwu.data.core.annotation.Dao;
import ${package.Dao}.${table.daoName};
import ${package.Mapper}.${table.mapperName};
import ${package.Entity}.${entity};
import ${superDaoImplClassPackage};

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

}
</#if>
