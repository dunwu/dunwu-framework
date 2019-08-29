package io.github.dunwu.quickstart.fs.mapper.dao.impl;

import io.github.dunwu.data.dao.BaseDao;
import io.github.dunwu.quickstart.fs.entity.FileInfo;
import io.github.dunwu.quickstart.fs.mapper.FileInfoMapper;
import io.github.dunwu.quickstart.fs.mapper.dao.FileInfoDao;
import io.github.dunwu.annotation.Dao;

/**
 * <p>
 * 文件信息表 DAO 实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-08
 */
@Dao
public class FileInfoDaoImpl extends BaseDao<FileInfoMapper, FileInfo> implements FileInfoDao {

}
