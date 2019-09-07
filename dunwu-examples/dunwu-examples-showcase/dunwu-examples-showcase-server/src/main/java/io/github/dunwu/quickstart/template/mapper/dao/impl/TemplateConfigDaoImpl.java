package io.github.dunwu.quickstart.template.mapper.dao.impl;

import io.github.dunwu.annotation.Dao;
import io.github.dunwu.data.dao.BaseDao;
import io.github.dunwu.quickstart.template.entity.TemplateConfig;
import io.github.dunwu.quickstart.template.mapper.TemplateConfigMapper;
import io.github.dunwu.quickstart.template.mapper.dao.TemplateConfigDao;

/**
 * <p>
 * 模板配置表 DAO 实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-26
 */
@Dao
public class TemplateConfigDaoImpl extends BaseDao<TemplateConfigMapper, TemplateConfig>
		implements TemplateConfigDao {

}
