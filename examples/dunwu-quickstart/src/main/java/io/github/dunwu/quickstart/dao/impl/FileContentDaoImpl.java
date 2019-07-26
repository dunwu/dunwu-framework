package io.github.dunwu.quickstart.dao.impl;

import io.github.dunwu.quickstart.entity.FileContent;
import io.github.dunwu.quickstart.mapper.FileContentMapper;
import io.github.dunwu.quickstart.dao.FileContentDao;
import io.github.dunwu.data.dao.BaseDao;
import io.github.dunwu.meta.Dao;

/**
 * <p>
 * 文件内容表 DAO 实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-24
 */
@Dao
public class FileContentDaoImpl extends BaseDao<FileContentMapper, FileContent> implements FileContentDao {

}
