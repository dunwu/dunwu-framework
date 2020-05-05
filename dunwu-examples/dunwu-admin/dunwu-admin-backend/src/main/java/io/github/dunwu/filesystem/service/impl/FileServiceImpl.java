package io.github.dunwu.filesystem.service.impl;

import io.github.dunwu.data.mybatis.BaseResultServiceImpl;
import io.github.dunwu.filesystem.entity.File;
import io.github.dunwu.filesystem.mapper.FileMapper;
import io.github.dunwu.filesystem.service.FileService;
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
public class FileServiceImpl extends BaseResultServiceImpl<FileMapper, File> implements FileService {

}
