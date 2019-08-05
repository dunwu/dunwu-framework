package io.github.dunwu.scheduler.manager;

import io.github.dunwu.core.BaseResult;
import io.github.dunwu.core.DataListResult;
import io.github.dunwu.core.DataResult;
import io.github.dunwu.core.PageResult;
import io.github.dunwu.scheduler.dto.BeanDTO;
import io.github.dunwu.scheduler.dto.SchedulerInfoDTO;
import io.github.dunwu.scheduler.dto.SchedulerInfoQuery;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-30
 */
public interface SchedulerManager {

    /**
     * 创建调度任务
     *
     * @param schedulerInfoDTO 调度信息 DTO
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult createJob(SchedulerInfoDTO schedulerInfoDTO);

    /**
     * 更新调度任务
     *
     * @param schedulerInfoDTO 调度信息 DTO
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult updateJob(SchedulerInfoDTO schedulerInfoDTO);

    /**
     * 删除调度任务
     *
     * @param schedulerInfoQuery 调度信息 Query
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult deleteJob(SchedulerInfoQuery schedulerInfoQuery);

    /**
     * 暂停调度任务
     *
     * @param schedulerInfoQuery 调度信息 Query
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult pauseJob(SchedulerInfoQuery schedulerInfoQuery);

    /**
     * 恢复调度任务
     *
     * @param schedulerInfoQuery 调度信息 Query
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult resumeJob(SchedulerInfoQuery schedulerInfoQuery);

    /**
     * 立即执行一次调度任务。如果调度任务本身是持久化任务，不影响其正常的执行计算
     *
     * @param schedulerInfoDTO 调度信息 DTO
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult executeJob(SchedulerInfoDTO schedulerInfoDTO);

    DataResult<SchedulerInfoDTO> getById(String id);

    DataListResult<SchedulerInfoDTO> list();

    PageResult<SchedulerInfoDTO> page(SchedulerInfoQuery schedulerInfoQuery, PageResult.Page page);

    List<BeanDTO> getJobHandlers();
}
