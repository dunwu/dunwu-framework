package io.github.dunwu.quickstart.fs.mapper.dao.impl;

import io.github.dunwu.data.dao.BaseDao;
import io.github.dunwu.quickstart.fs.entity.FileContent;
import io.github.dunwu.quickstart.fs.mapper.FileContentMapper;
import io.github.dunwu.quickstart.fs.mapper.dao.FileContentDao;
import io.github.dunwu.annotation.Dao;

/**
 * <p>
 * 文件内容表 DAO 实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-08
 */
@Dao
public class FileContentDaoImpl extends BaseDao<FileContentMapper, FileContent> implements FileContentDao {

}
