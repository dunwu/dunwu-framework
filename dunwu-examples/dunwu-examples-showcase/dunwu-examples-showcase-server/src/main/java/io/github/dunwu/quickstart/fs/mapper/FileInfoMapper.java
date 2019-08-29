package io.github.dunwu.quickstart.fs.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.dunwu.quickstart.fs.entity.FileInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 文件信息表 Mapper 接口
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-08
 */
@Mapper
public interface FileInfoMapper extends BaseMapper<FileInfo> {

}
