package io.github.dunwu.admin.scheduler.service;

import io.github.dunwu.common.BaseResult;
import io.github.dunwu.data.mybatis.IService;
import io.github.dunwu.admin.scheduler.dto.BeanDTO;
import io.github.dunwu.admin.scheduler.entity.Scheduler;

import java.util.List;

/**
 * <p>
 * 调度信息表 服务类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
public interface SchedulerService extends IService<Scheduler> {

    /**
     * 创建调度任务
     *
     * @param scheduler 调度信息 DTO
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult createJob(Scheduler scheduler);

    /**
     * 删除调度任务
     *
     * @param scheduler 调度信息 Query
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult deleteJob(Scheduler scheduler);

    /**
     * 立即执行一次调度任务。如果调度任务本身是持久化任务，不影响其正常的执行计算
     *
     * @param scheduler 调度信息 DTO
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult executeJob(Scheduler scheduler);

    /**
     * 暂停调度任务
     *
     * @param scheduler 调度信息 Query
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult pauseJob(Scheduler scheduler);

    /**
     * 恢复调度任务
     *
     * @param scheduler 调度信息 Query
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult resumeJob(Scheduler scheduler);

    /**
     * 更新调度任务
     *
     * @param scheduler 调度信息 DTO
     * @return 成功则 success 为 true；反之为 false
     */
    BaseResult updateJob(Scheduler scheduler);

    List<BeanDTO> getJobHandlers();

}
