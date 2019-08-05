package io.github.dunwu.scheduler.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.dunwu.core.PageResult;
import io.github.dunwu.scheduler.dto.SchedulerInfoDTO;
import io.github.dunwu.scheduler.dto.SchedulerInfoQuery;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-30
 */
public interface SchedulerInfoService {

    boolean save(SchedulerInfoDTO schedulerInfoDTO);

    boolean delete(SchedulerInfoDTO schedulerInfoDTO);

    boolean updateById(SchedulerInfoDTO schedulerInfoDTO);

    SchedulerInfoDTO getById(Serializable id);

    List<SchedulerInfoDTO> list();

    List<SchedulerInfoDTO> list(SchedulerInfoQuery schedulerInfoQuery);

    IPage<SchedulerInfoDTO> page(SchedulerInfoQuery schedulerInfoQuery, PageResult.Page page);
}
