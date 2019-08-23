package io.github.dunwu.quickstart.scheduler.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import io.github.dunwu.quickstart.scheduler.entity.SchedulerInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 调度信息表 Mapper 接口
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-07
 */
@Mapper
public interface SchedulerInfoMapper extends BaseMapper<SchedulerInfo> {

}
