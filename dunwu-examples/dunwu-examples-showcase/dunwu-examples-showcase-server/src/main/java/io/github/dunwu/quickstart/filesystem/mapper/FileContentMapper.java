package io.github.dunwu.quickstart.filesystem.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.dunwu.quickstart.filesystem.entity.FileContent;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文件内容表 Mapper 接口
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-08
 */
@Mapper
public interface FileContentMapper extends BaseMapper<FileContent> {

}
