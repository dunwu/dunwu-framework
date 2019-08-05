package io.github.dunwu.scheduler.manager.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.dunwu.config.DunwuSchedulerProperties;
import io.github.dunwu.core.*;
import io.github.dunwu.metadata.JobHandler;
import io.github.dunwu.metadata.Manager;
import io.github.dunwu.scheduler.constant.SchedulerConstant;
import io.github.dunwu.scheduler.constant.TriggerStatusEnum;
import io.github.dunwu.scheduler.constant.TriggerTypeEnum;
import io.github.dunwu.scheduler.dto.BeanDTO;
import io.github.dunwu.scheduler.dto.SchedulerInfoDTO;
import io.github.dunwu.scheduler.dto.SchedulerInfoQuery;
import io.github.dunwu.scheduler.handler.ExecuteMethodJobHandler;
import io.github.dunwu.scheduler.manager.SchedulerManager;
import io.github.dunwu.scheduler.service.SchedulerInfoService;
import io.github.dunwu.util.mapper.BeanMapper;
import org.apache.commons.collections4.CollectionUtils;
import org.quartz.*;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-30
 */
@Manager
public class SchedulerManagerImpl implements SchedulerManager {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final Scheduler persistScheduler;
    private final Scheduler nonPersistentScheduler;
    private final DunwuSchedulerProperties properties;
    private final SchedulerInfoService schedulerInfoService;

    public SchedulerManagerImpl(SchedulerInfoService schedulerInfoService, DunwuSchedulerProperties properties,
                                @Qualifier(value = "persistScheduler") Scheduler persistScheduler,
                                @Qualifier(value = "nonPersistentScheduler") Scheduler nonPersistentScheduler,
                                // 注入 applicationContext 是为了保证 SpringUtil 可以成功注入 applicationContext
                                ApplicationContext applicationContext) {
        this.persistScheduler = persistScheduler;
        this.nonPersistentScheduler = nonPersistentScheduler;
        this.schedulerInfoService = schedulerInfoService;
        this.properties = properties;

        List<SchedulerInfoDTO> list = schedulerInfoService.list();
        try {
            for (SchedulerInfoDTO item : list) {
                createQuartzJob(item);
            }
        } catch (SchedulerException e) {
            log.error("系统启动加载调度器失败", e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult createJob(SchedulerInfoDTO schedulerInfoDTO) {
        if (schedulerInfoDTO == null) {
            return ResultUtil.failDataResult(DefaultAppCode.ERROR_PARAMETER);
        }

        try {
            fillFields(schedulerInfoDTO);
        } catch (SchedulerException e) {
            return ResultUtil.failBaseResult();
        }
        SchedulerInfoQuery schedulerInfoQuery = BeanMapper.map(schedulerInfoDTO, SchedulerInfoQuery.class);
        List<SchedulerInfoDTO> list = schedulerInfoService.list(schedulerInfoQuery);
        if (CollectionUtils.isNotEmpty(list)) {
            return ResultUtil.failBaseResult();
        }

        if (!schedulerInfoService.save(schedulerInfoDTO)) {
            return ResultUtil.failBaseResult(DefaultAppCode.ERROR_DB.code(), "创建调度作业数据库记录失败");
        }
        try {
            createQuartzJob(schedulerInfoDTO);
        } catch (SchedulerException e) {
            return ResultUtil.failBaseResult(DefaultAppCode.ERROR_SCHEDULER.code(), "创建调度作业失败");
        }
        return ResultUtil.successBaseResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult updateJob(SchedulerInfoDTO schedulerInfoDTO) {
        if (schedulerInfoDTO == null) {
            return ResultUtil.failDataResult(DefaultAppCode.ERROR_PARAMETER);
        }

        SchedulerInfoDTO originSchedulerInfoDTO = schedulerInfoService.getById(schedulerInfoDTO.getId());
        if (originSchedulerInfoDTO == null) {
            return ResultUtil.failBaseResult(DefaultAppCode.ERROR_DB.code(), "未找到记录");
        }

        if (!schedulerInfoService.updateById(schedulerInfoDTO)) {
            return ResultUtil.failBaseResult(DefaultAppCode.ERROR_DB.code(), "更新调度作业数据库记录失败");
        }

        try {
            // 由于作业组、作业名都可能发生变更，所以必须重新创建作业
            if (!deleteQuartzJob(originSchedulerInfoDTO)) {
                return ResultUtil.failBaseResult(DefaultAppCode.ERROR_SCHEDULER.code(), "删除调度作业失败");
            }
            createQuartzJob(schedulerInfoDTO);
        } catch (SchedulerException e) {
            log.error("更新调度作业 jobGroup={},jobName={} 失败", schedulerInfoDTO.getJobGroup(), schedulerInfoDTO.getJobName(),
                      e);
            return ResultUtil.failBaseResult(DefaultAppCode.ERROR_SCHEDULER.code(), "创建调度作业失败");
        }
        return ResultUtil.successBaseResult();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult deleteJob(SchedulerInfoQuery schedulerInfoQuery) {
        if (schedulerInfoQuery == null) {
            return ResultUtil.failDataResult(DefaultAppCode.ERROR_PARAMETER);
        }

        List<SchedulerInfoDTO> list = schedulerInfoService.list(schedulerInfoQuery);
        if (CollectionUtils.isEmpty(list)) {
            return ResultUtil.failBaseResult(DefaultAppCode.ERROR_DB.code(), "未找到调度器");
        }

        for (SchedulerInfoDTO item : list) {
            // 删除记录
            boolean flag = schedulerInfoService.delete(item);
            if (!flag) {
                return ResultUtil.failBaseResult(DefaultAppCode.ERROR_DB.code(), "删除调度作业数据库记录失败");
            }

            try {
                deleteQuartzJob(item);
            } catch (SchedulerException e) {
                List<String> messages = new ArrayList<>();
                messages.add("删除调度作业失败");
                messages.add(e.getMessage());
                return ResultUtil.failBaseResult(DefaultAppCode.ERROR_SCHEDULER.code(), messages);
            }

            if (log.isDebugEnabled()) {
                log.debug("删除作业 jobGroup={}, jobName={} 成功", item.getJobGroup(), item.getJobName());
            }
        }

        return ResultUtil.successBaseResult();
    }

    @Override
    public BaseResult pauseJob(SchedulerInfoQuery schedulerInfoQuery) {
        if (schedulerInfoQuery == null) {
            return ResultUtil.failDataResult(DefaultAppCode.ERROR_PARAMETER);
        }

        List<SchedulerInfoDTO> list = schedulerInfoService.list(schedulerInfoQuery);
        if (CollectionUtils.isEmpty(list)) {
            return ResultUtil.failBaseResult(DefaultAppCode.ERROR_SCHEDULER.code(), "未找到调度器");
        }

        for (SchedulerInfoDTO item : list) {
            item.setStatus(TriggerStatusEnum.PAUSED);
            if (!schedulerInfoService.updateById(item)) {
                return ResultUtil.failBaseResult(DefaultAppCode.ERROR_DB.code(), "更新调度作业数据库记录失败");
            }

            try {
                pauseQuartzJob(item);
            } catch (SchedulerException e) {
                List<String> messages = new ArrayList<>();
                messages.add("暂停调度作业失败");
                messages.add(e.getMessage());
                return ResultUtil.failBaseResult(DefaultAppCode.ERROR_SCHEDULER.code(), messages);
            }
        }
        return ResultUtil.successBaseResult();
    }

    @Override
    public BaseResult resumeJob(SchedulerInfoQuery schedulerInfoQuery) {
        if (schedulerInfoQuery == null) {
            return ResultUtil.failDataResult(DefaultAppCode.ERROR_PARAMETER);
        }

        List<SchedulerInfoDTO> list = schedulerInfoService.list(schedulerInfoQuery);
        if (CollectionUtils.isEmpty(list)) {
            return ResultUtil.failBaseResult(DefaultAppCode.ERROR_SCHEDULER.code(), "未找到调度器");
        }

        for (SchedulerInfoDTO item : list) {
            item.setStatus(TriggerStatusEnum.EXECUTING);
            if (!schedulerInfoService.updateById(item)) {
                return ResultUtil.failBaseResult(DefaultAppCode.ERROR_DB.code(), "更新调度作业数据库记录失败");
            }

            try {
                resumeQuartzJob(item);
            } catch (SchedulerException e) {
                List<String> messages = new ArrayList<>();
                messages.add("恢复调度作业失败");
                messages.add(e.getMessage());
                return ResultUtil.failBaseResult(DefaultAppCode.ERROR_SCHEDULER.code(), messages);
            }
        }
        return ResultUtil.successBaseResult();
    }

    @Override
    public BaseResult executeJob(SchedulerInfoDTO schedulerInfoDTO) {
        if (schedulerInfoDTO == null) {
            return ResultUtil.failDataResult(DefaultAppCode.ERROR_PARAMETER);
        }

        try {
            executeQuartzJob(schedulerInfoDTO);
        } catch (SchedulerException e) {
            log.error("执行调度作业 jobGroup={},jobName={} 失败", schedulerInfoDTO.getJobGroup(), schedulerInfoDTO.getJobName(),
                      e);
            return ResultUtil.failBaseResult(DefaultAppCode.ERROR_SCHEDULER.code(), "执行调度作业失败");
        }
        return ResultUtil.successBaseResult();
    }

    @Override
    public DataResult<SchedulerInfoDTO> getById(String id) {
        return ResultUtil.successDataResult(schedulerInfoService.getById(id));
    }

    @Override
    public DataListResult<SchedulerInfoDTO> list() {
        return ResultUtil.successDataListResult(schedulerInfoService.list());
    }

    @Override
    public PageResult<SchedulerInfoDTO> page(SchedulerInfoQuery schedulerInfoQuery, PageResult.Page page) {
        IPage<SchedulerInfoDTO> resultPage = schedulerInfoService.page(schedulerInfoQuery, page);
        page.setTotal(resultPage.getTotal());
        page.setPages(resultPage.getPages());
        return ResultUtil.successPageResult(resultPage.getRecords(), page);
    }

    private void fillFields(SchedulerInfoDTO schedulerInfoDTO) throws SchedulerException {
        schedulerInfoDTO.setSchedulerName(persistScheduler.getSchedulerName());
        schedulerInfoDTO.setTriggerGroup("trigger_group_" + schedulerInfoDTO.getJobGroup());
        schedulerInfoDTO.setTriggerName("trigger_" + schedulerInfoDTO.getJobName());
        schedulerInfoDTO.setStatus(TriggerStatusEnum.EXECUTING);
        if (schedulerInfoDTO.getTimeRange() != null && schedulerInfoDTO.getTimeRange()
                                                                       .size() == 2) {
            schedulerInfoDTO.setBeginTime(schedulerInfoDTO.getTimeRange()
                                                          .get(0));
            schedulerInfoDTO.setEndTime(schedulerInfoDTO.getTimeRange()
                                                        .get(1));
        }
    }

    private void createQuartzJob(SchedulerInfoDTO schedulerInfoDTO) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(SchedulerConstant.BEAN_NAME, schedulerInfoDTO.getBeanName());
        jobDataMap.put(SchedulerConstant.BEAN_TYPE, schedulerInfoDTO.getBeanType());
        jobDataMap.put(SchedulerConstant.METHOD_NAME, "execute");
        jobDataMap.put(SchedulerConstant.METHOD_PARAMS, schedulerInfoDTO.getMethodParams());

        // 构建 Quartz 调度任务
        JobDetail jobDetail = JobBuilder.newJob(ExecuteMethodJobHandler.class)
                                        .withIdentity(schedulerInfoDTO.getJobName(), schedulerInfoDTO.getJobGroup())
                                        .setJobData(jobDataMap)
                                        .build();

        // 构建 Quartz 触发器
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                                                               .withIdentity(schedulerInfoDTO.getTriggerName(),
                                                                             schedulerInfoDTO.getTriggerGroup());
        if (schedulerInfoDTO.getTriggerType() == TriggerTypeEnum.SIMPLE) {
            newSimpleTriggerBuilder(schedulerInfoDTO, triggerBuilder);
        } else if (schedulerInfoDTO.getTriggerType() == TriggerTypeEnum.CRON) {
            newCronTriggerBuilder(schedulerInfoDTO, triggerBuilder);
        }
        Trigger trigger = triggerBuilder.build();

        // 将调度任务和触发器添加到 scheduler 并启动
        persistScheduler.scheduleJob(jobDetail, trigger);
        if (schedulerInfoDTO.getStatus() != TriggerStatusEnum.EXECUTING) {
            JobKey jobKey = new JobKey(schedulerInfoDTO.getJobName(), schedulerInfoDTO.getJobGroup());
            TriggerKey triggerKey = new TriggerKey(schedulerInfoDTO.getTriggerName(),
                                                   schedulerInfoDTO.getTriggerGroup());
            persistScheduler.pauseJob(jobKey);
            persistScheduler.pauseTrigger(triggerKey);
        }
    }

    private boolean deleteQuartzJob(SchedulerInfoDTO schedulerInfoDTO) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfoDTO.getJobName(), schedulerInfoDTO.getJobGroup());
        TriggerKey triggerKey = TriggerKey.triggerKey(schedulerInfoDTO.getTriggerName(),
                                                      schedulerInfoDTO.getTriggerGroup());
        // 删除触发器
        persistScheduler.pauseTrigger(triggerKey);
        persistScheduler.unscheduleJob(triggerKey);
        // 删除任务
        persistScheduler.deleteJob(jobKey);
        return true;
    }

    private void pauseQuartzJob(SchedulerInfoDTO schedulerInfoDTO) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfoDTO.getJobName(), schedulerInfoDTO.getJobGroup());
        TriggerKey triggerKey = TriggerKey.triggerKey(schedulerInfoDTO.getTriggerName(),
                                                      schedulerInfoDTO.getTriggerGroup());
        persistScheduler.pauseJob(jobKey);
        persistScheduler.pauseTrigger(triggerKey);
    }

    private void resumeQuartzJob(SchedulerInfoDTO schedulerInfoDTO) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfoDTO.getJobName(), schedulerInfoDTO.getJobGroup());
        TriggerKey triggerKey = TriggerKey.triggerKey(schedulerInfoDTO.getTriggerName(),
                                                      schedulerInfoDTO.getTriggerGroup());
        persistScheduler.resumeJob(jobKey);
        persistScheduler.resumeTrigger(triggerKey);
    }

    private void executeQuartzJob(SchedulerInfoDTO schedulerInfoDTO) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(SchedulerConstant.BEAN_NAME, schedulerInfoDTO.getBeanName());
        jobDataMap.put(SchedulerConstant.BEAN_TYPE, schedulerInfoDTO.getBeanType());
        jobDataMap.put(SchedulerConstant.METHOD_NAME, "execute");
        jobDataMap.put(SchedulerConstant.METHOD_PARAMS, schedulerInfoDTO.getMethodParams());

        JobKey jobKey = new JobKey(schedulerInfoDTO.getJobName(), schedulerInfoDTO.getJobGroup());
        JobDetail jobDetail = persistScheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            // 构建 Quartz 调度任务
            jobDetail = JobBuilder.newJob(ExecuteMethodJobHandler.class)
                                  .withIdentity(schedulerInfoDTO.getJobName(), schedulerInfoDTO.getJobGroup())
                                  .setJobData(jobDataMap)
                                  .build();

            // 构建 Quartz 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                                                                   .withIdentity(schedulerInfoDTO.getTriggerName(),
                                                                                 schedulerInfoDTO.getTriggerGroup());
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
            scheduleBuilder.withRepeatCount(0);
            triggerBuilder.withSchedule(scheduleBuilder);
            Trigger trigger = triggerBuilder.build();


            // 将调度任务和触发器添加到 scheduler 并启动
            persistScheduler.scheduleJob(jobDetail, trigger);
        } else {
            persistScheduler.triggerJob(jobKey, jobDataMap);
        }
        persistScheduler.start();
    }

    private void newCronTriggerBuilder(SchedulerInfoDTO schedulerInfoDTO, TriggerBuilder<Trigger> triggerBuilder) {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(schedulerInfoDTO.getCronExpression());
        scheduleBuilder.inTimeZone(TimeZone.getTimeZone("GMT+8"));
        triggerBuilder.withSchedule(scheduleBuilder);
    }

    private void newSimpleTriggerBuilder(SchedulerInfoDTO schedulerInfoDTO, TriggerBuilder<Trigger> triggerBuilder) {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        scheduleBuilder.withIntervalInSeconds(schedulerInfoDTO.getRepeatInterval());
        if (schedulerInfoDTO.getRepeatCount() != null && schedulerInfoDTO.getRepeatCount() != -1) {
            scheduleBuilder.withRepeatCount(schedulerInfoDTO.getRepeatCount());
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
