package io.github.dunwu.quickstart.scheduler.service;

import io.github.dunwu.core.BaseResult;
import io.github.dunwu.quickstart.scheduler.dto.BeanDTO;
import io.github.dunwu.quickstart.scheduler.entity.SchedulerInfo;

import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-30
 */
public interface SchedulerService {

    /**
     * 创建调度任务
     *
     * @param schedulerInfo 调度信息 DTO
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult createJob(SchedulerInfo schedulerInfo);

    /**
     * 更新调度任务
     *
     * @param schedulerInfo 调度信息 DTO
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult updateJob(SchedulerInfo schedulerInfo);

    /**
     * 删除调度任务
     *
     * @param schedulerInfo 调度信息 Query
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult deleteJob(SchedulerInfo schedulerInfo);

    /**
     * 暂停调度任务
     *
     * @param schedulerInfo 调度信息 Query
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult pauseJob(SchedulerInfo schedulerInfo);

    /**
     * 恢复调度任务
     *
     * @param schedulerInfo 调度信息 Query
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult resumeJob(SchedulerInfo schedulerInfo);

    /**
     * 立即执行一次调度任务。如果调度任务本身是持久化任务，不影响其正常的执行计算
     *
     * @param schedulerInfo 调度信息 DTO
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult executeJob(SchedulerInfo schedulerInfo);

    List<BeanDTO> getJobHandlers();
}
