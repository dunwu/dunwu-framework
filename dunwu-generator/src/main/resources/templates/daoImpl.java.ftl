package ${package.DaoImpl};

import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Dao}.${table.daoName};
import ${superDaoImplClassPackage};
import io.github.dunwu.metadata.Dao;

/**
 * <p>
 * ${table.comment!} DAO 实现类
 * </p>
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
