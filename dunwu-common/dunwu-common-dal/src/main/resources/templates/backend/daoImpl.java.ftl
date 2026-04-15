package ${package.DaoImpl};

import io.github.dunwu.common.dal.annotation.Dao;
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
public class ${table.daoImplName} extends ${superDaoImplClass}<${table.mapperName}, ${entity}> implements ${table.daoName} {

}
