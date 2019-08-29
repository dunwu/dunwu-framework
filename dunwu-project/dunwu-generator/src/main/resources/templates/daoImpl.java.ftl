package ${package.DaoImpl};

import io.github.dunwu.annotation.Dao;
import ${superDaoImplClassPackage};
import ${package.Entity}.${entity};
import ${package.Mapper}.${table.mapperName};
import ${package.Dao}.${table.daoName};

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
