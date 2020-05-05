package ${package.DaoImpl};

import ${package.Dao}.${table.daoName};
import ${package.Mapper}.${table.mapperName};
import ${package.Entity}.${entity};
import ${superDaoImplClassPackage};
import org.springframework.stereotype.Service;

/**
 * ${table.comment!} Dao 类
 *
 * @author ${author}
 * @since ${date}
 */
@Service
<#if kotlin>
open class ${table.daoImplName} : ${superDaoImplClass}<${table.mapperName}, ${entity}>(), ${table.daoName} {

}
<#else>
public class ${table.daoImplName} extends ${superDaoImplClass}<${table.mapperName}, ${entity}> implements ${table.daoName} {

}
</#if>
