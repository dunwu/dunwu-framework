package io.github.dunwu.admin.filesystem.service.impl;

import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.admin.filesystem.entity.File;
import io.github.dunwu.admin.filesystem.mapper.FileMapper;
import io.github.dunwu.admin.filesystem.service.FileService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件信息表 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
@Service
public class FileServiceImpl extends ServiceImpl<FileMapper, File> implements FileService {

}
