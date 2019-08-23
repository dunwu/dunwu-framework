package io.github.dunwu.quickstart.scheduler.service.impl;

import io.github.dunwu.core.*;
import io.github.dunwu.quickstart.scheduler.dto.BeanDTO;
import io.github.dunwu.quickstart.scheduler.entity.SchedulerInfo;
import io.github.dunwu.quickstart.scheduler.service.SchedulerInfoService;
import io.github.dunwu.quickstart.scheduler.service.SchedulerService;
import io.github.dunwu.annotation.JobHandler;
import io.github.dunwu.scheduler.constant.SchedulerConstant;
import io.github.dunwu.scheduler.constant.TriggerStatusEnum;
import io.github.dunwu.scheduler.constant.TriggerTypeEnum;
import io.github.dunwu.scheduler.handler.ExecuteMethodJobHandler;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.*;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-30
 */
@Service
public class SchedulerServiceImpl implements SchedulerService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Scheduler scheduler;
    private final SchedulerInfoService schedulerInfoService;

    public SchedulerServiceImpl(Scheduler scheduler, SchedulerInfoService schedulerInfoService,
                                // 注入 applicationContext 是为了保证 SpringUtil 可以成功注入 applicationContext
                                ApplicationContext applicationContext) {
        this.scheduler = scheduler;
        this.schedulerInfoService = schedulerInfoService;

        DataListResult<SchedulerInfo> dataListResult = schedulerInfoService.list();
        try {
            for (SchedulerInfo item : dataListResult.getData()) {
                createQuartzJob(item);
            }
        } catch (SchedulerException e) {
            log.error("系统启动加载调度器失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult createJob(SchedulerInfo schedulerInfo) {
        if (schedulerInfo == null) {
            return ResultUtil.failDataResult(AppCode.ERROR_PARAMETER);
        }

        try {
            fillFields(schedulerInfo);
        } catch (SchedulerException e) {
            return ResultUtil.failBaseResult();
        }

        DataListResult<SchedulerInfo> dataListResult = schedulerInfoService.list(schedulerInfo);
        if (CollectionUtils.isNotEmpty(dataListResult.getData())) {
            return ResultUtil.failBaseResult();
        }

        DataResult<? extends Serializable> dataResult = schedulerInfoService.save(schedulerInfo);
        if (ResultUtil.isNotValidResult(dataResult)) {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB.getCode(), "创建调度作业数据库记录失败");
        }
        try {
            createQuartzJob(schedulerInfo);
        } catch (SchedulerException e) {
            return ResultUtil.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(), "创建调度作业失败");
        }
        return ResultUtil.successBaseResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult updateJob(SchedulerInfo schedulerInfo) {
        if (schedulerInfo == null) {
            return ResultUtil.failDataResult(AppCode.ERROR_PARAMETER);
        }

        DataResult<SchedulerInfo> dataResult = schedulerInfoService.getById(schedulerInfo.getId());
        if (ResultUtil.isNotValidResult(dataResult)) {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB.getCode(), "未找到记录");
        }

        BaseResult baseResult = schedulerInfoService.updateById(schedulerInfo);
        if (ResultUtil.isNotValidResult(baseResult)) {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB.getCode(), "更新调度作业数据库记录失败");
        }

        try {
            // 由于作业组、作业名都可能发生变更，所以必须重新创建作业
            if (!deleteQuartzJob(schedulerInfo)) {
                return ResultUtil.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(), "删除调度作业失败");
            }
            createQuartzJob(schedulerInfo);
        } catch (SchedulerException e) {
            log.error("更新调度作业 jobGroup={},jobName={} 失败", schedulerInfo.getJobGroup(), schedulerInfo.getJobName(), e);
            return ResultUtil.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(), "创建调度作业失败");
        }
        return ResultUtil.successBaseResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult deleteJob(SchedulerInfo schedulerInfo) {
        if (schedulerInfo == null) {
            return ResultUtil.failDataResult(AppCode.ERROR_PARAMETER);
        }

        DataListResult<SchedulerInfo> dataListResult = schedulerInfoService.list(schedulerInfo);
        if (ResultUtil.isNotValidResult(dataListResult)) {
            return ResultUtil.failBaseResult(AppCode.ERROR_DB.getCode(), "未找到调度器");
        }

        for (SchedulerInfo item : dataListResult.getData()) {
            // 删除记录
            BaseResult baseResult = schedulerInfoService.removeById(item.getId());
            if (ResultUtil.isNotValidResult(baseResult)) {
                return ResultUtil.failBaseResult(AppCode.ERROR_DB.getCode(), "删除调度作业数据库记录失败");
            }

            try {
                deleteQuartzJob(item);
            } catch (SchedulerException e) {
                List<String> messages = new ArrayList<>();
                messages.add("删除调度作业失败");
                messages.add(e.getMessage());
                return ResultUtil.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(), messages);
            }

            if (log.isDebugEnabled()) {
                log.debug("删除作业 jobGroup={}, jobName={} 成功", item.getJobGroup(), item.getJobName());
            }
        }

        return ResultUtil.successBaseResult();
    }

    @Override
    public BaseResult pauseJob(SchedulerInfo schedulerInfo) {
        if (schedulerInfo == null) {
            return ResultUtil.failDataResult(AppCode.ERROR_PARAMETER);
        }

        DataListResult<SchedulerInfo> dataListResult = schedulerInfoService.list(schedulerInfo);
        if (ResultUtil.isNotValidResult(dataListResult)) {
            return ResultUtil.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(), "未找到调度器");
        }

        for (SchedulerInfo item : dataListResult.getData()) {
            item.setStatus(TriggerStatusEnum.PAUSED);
            BaseResult baseResult = schedulerInfoService.updateById(item);
            if (ResultUtil.isNotValidResult(baseResult)) {
                return ResultUtil.failBaseResult(AppCode.ERROR_DB.getCode(), "更新调度作业数据库记录失败");
            }

            try {
                pauseQuartzJob(item);
            } catch (SchedulerException e) {
                List<String> messages = new ArrayList<>();
                messages.add("暂停调度作业失败");
                messages.add(e.getMessage());
                return ResultUtil.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(), messages);
            }
        }
        return ResultUtil.successBaseResult();
    }

    @Override
    public BaseResult resumeJob(SchedulerInfo schedulerInfo) {
        if (schedulerInfo == null) {
            return ResultUtil.failDataResult(AppCode.ERROR_PARAMETER);
        }

        DataListResult<SchedulerInfo> dataListResult = schedulerInfoService.list(schedulerInfo);
        if (ResultUtil.isNotValidResult(dataListResult)) {
            return ResultUtil.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(), "未找到调度器");
        }

        for (SchedulerInfo item : dataListResult.getData()) {
            item.setStatus(TriggerStatusEnum.EXECUTING);
            BaseResult baseResult = schedulerInfoService.updateById(item);
            if (ResultUtil.isNotValidResult(baseResult)) {
                return ResultUtil.failBaseResult(AppCode.ERROR_DB.getCode(), "更新调度作业数据库记录失败");
            }

            try {
                resumeQuartzJob(item);
            } catch (SchedulerException e) {
                List<String> messages = new ArrayList<>();
                messages.add("恢复调度作业失败");
                messages.add(e.getMessage());
                return ResultUtil.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(), messages);
            }
        }
        return ResultUtil.successBaseResult();
    }

    @Override
    public BaseResult executeJob(SchedulerInfo schedulerInfo) {
        if (schedulerInfo == null) {
            return ResultUtil.failDataResult(AppCode.ERROR_PARAMETER);
        }

        try {
            executeQuartzJob(schedulerInfo);
        } catch (SchedulerException e) {
            log.error("执行调度作业 jobGroup={},jobName={} 失败", schedulerInfo.getJobGroup(), schedulerInfo.getJobName(), e);
            return ResultUtil.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(), "执行调度作业失败");
        }
        return ResultUtil.successBaseResult();
    }

    private void fillFields(SchedulerInfo schedulerInfo) throws SchedulerException {
        schedulerInfo.setSchedulerName(scheduler.getSchedulerName());
        schedulerInfo.setTriggerGroup("trigger_group_" + schedulerInfo.getJobGroup());
        schedulerInfo.setTriggerName("trigger_" + schedulerInfo.getJobName());
        schedulerInfo.setStatus(TriggerStatusEnum.EXECUTING);
        //        List<LocalDateTime> timeRange = schedulerInfo.getTimeRange();
        //        if (timeRange != null && timeRange.size() == 2) {
        //            schedulerInfo.setBeginTime(timeRange.get(0));
        //            schedulerInfo.setEndTime(timeRange.get(1));
        //        }
    }

    private void createQuartzJob(SchedulerInfo schedulerInfo) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(SchedulerConstant.BEAN_NAME, schedulerInfo.getBeanName());
        jobDataMap.put(SchedulerConstant.BEAN_TYPE, schedulerInfo.getBeanType());
        jobDataMap.put(SchedulerConstant.METHOD_NAME, "execute");
        jobDataMap.put(SchedulerConstant.METHOD_PARAMS, schedulerInfo.getMethodParams());

        // 构建 Quartz 调度任务
        JobDetail jobDetail = JobBuilder.newJob(ExecuteMethodJobHandler.class)
                                        .withIdentity(schedulerInfo.getJobName(), schedulerInfo.getJobGroup())
                                        .setJobData(jobDataMap)
                                        .build();

        // 构建 Quartz 触发器
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                                                               .withIdentity(schedulerInfo.getTriggerName(),
                                                                             schedulerInfo.getTriggerGroup());
        if (schedulerInfo.getTriggerType() == TriggerTypeEnum.SIMPLE) {
            newSimpleTriggerBuilder(schedulerInfo, triggerBuilder);
        } else if (schedulerInfo.getTriggerType() == TriggerTypeEnum.CRON) {
            newCronTriggerBuilder(schedulerInfo, triggerBuilder);
        }
        Trigger trigger = triggerBuilder.build();

        // 将调度任务和触发器添加到 scheduler 并启动
        scheduler.scheduleJob(jobDetail, trigger);
        if (schedulerInfo.getStatus() != TriggerStatusEnum.EXECUTING) {
            JobKey jobKey = new JobKey(schedulerInfo.getJobName(), schedulerInfo.getJobGroup());
            TriggerKey triggerKey = new TriggerKey(schedulerInfo.getTriggerName(), schedulerInfo.getTriggerGroup());
            scheduler.pauseJob(jobKey);
            scheduler.pauseTrigger(triggerKey);
        }
    }

    private boolean deleteQuartzJob(SchedulerInfo schedulerInfo) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfo.getJobName(), schedulerInfo.getJobGroup());
        TriggerKey triggerKey = TriggerKey.triggerKey(schedulerInfo.getTriggerName(), schedulerInfo.getTriggerGroup());
        // 删除触发器
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(jobKey);
        return true;
    }

    private void pauseQuartzJob(SchedulerInfo schedulerInfo) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfo.getJobName(), schedulerInfo.getJobGroup());
        TriggerKey triggerKey = TriggerKey.triggerKey(schedulerInfo.getTriggerName(), schedulerInfo.getTriggerGroup());
        scheduler.pauseJob(jobKey);
        scheduler.pauseTrigger(triggerKey);
    }

    private void resumeQuartzJob(SchedulerInfo schedulerInfo) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfo.getJobName(), schedulerInfo.getJobGroup());
        TriggerKey triggerKey = TriggerKey.triggerKey(schedulerInfo.getTriggerName(), schedulerInfo.getTriggerGroup());
        scheduler.resumeJob(jobKey);
        scheduler.resumeTrigger(triggerKey);
    }

    private void executeQuartzJob(SchedulerInfo schedulerInfo) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(SchedulerConstant.BEAN_NAME, schedulerInfo.getBeanName());
        jobDataMap.put(SchedulerConstant.BEAN_TYPE, schedulerInfo.getBeanType());
        jobDataMap.put(SchedulerConstant.METHOD_NAME, "execute");
        jobDataMap.put(SchedulerConstant.METHOD_PARAMS, schedulerInfo.getMethodParams());

        JobKey jobKey = new JobKey(schedulerInfo.getJobName(), schedulerInfo.getJobGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            // 构建 Quartz 调度任务
            jobDetail = JobBuilder.newJob(ExecuteMethodJobHandler.class)
                                  .withIdentity(schedulerInfo.getJobName(), schedulerInfo.getJobGroup())
                                  .setJobData(jobDataMap)
                                  .build();

            // 构建 Quartz 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                                                                   .withIdentity(schedulerInfo.getTriggerName(),
                                                                                 schedulerInfo.getTriggerGroup());
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
            scheduleBuilder.withRepeatCount(0);
            triggerBuilder.withSchedule(scheduleBuilder);
            Trigger trigger = triggerBuilder.build();


            // 将调度任务和触发器添加到 scheduler 并启动
            scheduler.scheduleJob(jobDetail, trigger);
        } else {
            scheduler.triggerJob(jobKey, jobDataMap);
        }
        scheduler.start();
    }

    private void newCronTriggerBuilder(SchedulerInfo schedulerInfo, TriggerBuilder<Trigger> triggerBuilder) {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(schedulerInfo.getCronExpression());
        scheduleBuilder.inTimeZone(TimeZone.getTimeZone("GMT+8"));
        triggerBuilder.withSchedule(scheduleBuilder);
    }

    private void newSimpleTriggerBuilder(SchedulerInfo schedulerInfo, TriggerBuilder<Trigger> triggerBuilder) {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        scheduleBuilder.withIntervalInSeconds(schedulerInfo.getRepeatInterval());
        if (schedulerInfo.getRepeatCount() != null && schedulerInfo.getRepeatCount() != -1) {
            scheduleBuilder.withRepeatCount(schedulerInfo.getRepeatCount());
        } else {
            scheduleBuilder.repeatForever();
        }
        triggerBuilder.withSchedule(scheduleBuilder);
    }

    @Override
    public List<BeanDTO> getJobHandlers() {
        Reflections reflections = new Reflections("io.github.dunwu.hehe");
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(JobHandler.class);
        List<BeanDTO> classDTOS = new ArrayList<>();
        for (Class<?> clazz : types) {
            JobHandler annotation = clazz.getAnnotation(JobHandler.class);
            String beanName = annotation.value();
            BeanDTO beanDTO = new BeanDTO();
            beanDTO.setBeanName(beanName);
            beanDTO.setBeanType(clazz);
            classDTOS.add(beanDTO);
        }
        return classDTOS;
    }
}
