package io.github.dunwu.quickstart.filesystem.service.impl;

import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.quickstart.filesystem.entity.FileContent;
import io.github.dunwu.quickstart.filesystem.mapper.FileContentMapper;
import io.github.dunwu.quickstart.filesystem.service.FileContentService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件内容表 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
@Service
public class FileContentServiceImpl extends ServiceImpl<FileContentMapper, FileContent>
    implements FileContentService {

}
