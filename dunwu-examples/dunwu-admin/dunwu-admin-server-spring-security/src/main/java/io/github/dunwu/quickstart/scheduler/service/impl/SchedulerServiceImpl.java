package io.github.dunwu.quickstart.scheduler.service.impl;

import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.DataListResult;
import io.github.dunwu.common.DataResult;
import io.github.dunwu.common.ResultUtils;
import io.github.dunwu.common.annotation.JobHandler;
import io.github.dunwu.common.constant.AppCode;
import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.quickstart.scheduler.constant.SchedulerConstant;
import io.github.dunwu.quickstart.scheduler.constant.TriggerStatusEnum;
import io.github.dunwu.quickstart.scheduler.constant.TriggerTypeEnum;
import io.github.dunwu.quickstart.scheduler.dto.BeanDTO;
import io.github.dunwu.quickstart.scheduler.entity.Scheduler;
import io.github.dunwu.quickstart.scheduler.handler.ExecuteMethodJobHandler;
import io.github.dunwu.quickstart.scheduler.mapper.SchedulerMapper;
import io.github.dunwu.quickstart.scheduler.service.SchedulerService;
import io.github.dunwu.tool.collection.CollectionUtil;
import org.quartz.*;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import javax.annotation.PostConstruct;

/**
 * <p>
 * 调度信息表 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
@Service
public class SchedulerServiceImpl extends ServiceImpl<SchedulerMapper, Scheduler>
    implements SchedulerService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final org.quartz.Scheduler scheduler;

    public SchedulerServiceImpl(SchedulerFactoryBean schedulerFactoryBean,
        // 注入 applicationContext 是为了保证 SpringUtil 可以成功注入 applicationContext
        ApplicationContext applicationContext) {
        this.scheduler = schedulerFactoryBean.getScheduler();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult createJob(Scheduler schedulerInfo) {
        if (schedulerInfo == null) {
            return ResultUtils.failDataResult(AppCode.ERROR_PARAMETER);
        }

        try {
            fillFields(schedulerInfo);
        } catch (SchedulerException e) {
            return ResultUtils.failBaseResult();
        }

        DataListResult<Scheduler> dataListResult = super.list(schedulerInfo);
        if (CollectionUtil.isNotEmpty(dataListResult.getData())) {
            return ResultUtils.failBaseResult();
        }

        DataResult<? extends Serializable> dataResult = super.save(schedulerInfo);
        if (ResultUtils.isNotValid(dataResult)) {
            return ResultUtils.failBaseResult(AppCode.ERROR_DB.getCode(), "创建调度作业数据库记录失败");
        }
        try {
            createQuartzJob(schedulerInfo);
        } catch (SchedulerException e) {
            return ResultUtils.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(),
                "创建调度作业失败");
        }
        return ResultUtils.successBaseResult();
    }

    private void fillFields(Scheduler schedulerInfo) throws SchedulerException {
        schedulerInfo.setSchedulerName(scheduler.getSchedulerName());
        schedulerInfo.setTriggerGroup("trigger_group_" + schedulerInfo.getJobGroup());
        schedulerInfo.setTriggerName("trigger_" + schedulerInfo.getJobName());
        schedulerInfo.setStatus(TriggerStatusEnum.EXECUTING);
        // List<LocalDateTime> timeRange = schedulerInfo.getTimeRange();
        // if (timeRange != null && timeRange.size() == 2) {
        // schedulerInfo.setBeginTime(timeRange.get(0));
        // schedulerInfo.setEndTime(timeRange.get(1));
        // }
    }

    private void createQuartzJob(Scheduler schedulerInfo) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(SchedulerConstant.BEAN_NAME, schedulerInfo.getBeanName());
        jobDataMap.put(SchedulerConstant.BEAN_TYPE, schedulerInfo.getBeanType());
        jobDataMap.put(SchedulerConstant.METHOD_NAME, "execute");
        jobDataMap.put(SchedulerConstant.METHOD_PARAMS, schedulerInfo.getMethodParams());

        // 构建 Quartz 调度任务
        JobDetail jobDetail = JobBuilder.newJob(ExecuteMethodJobHandler.class)
            .withIdentity(schedulerInfo.getJobName(), schedulerInfo.getJobGroup())
            .setJobData(jobDataMap).build();

        // 构建 Quartz 触发器
        TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger().withIdentity(
            schedulerInfo.getTriggerName(), schedulerInfo.getTriggerGroup());
        if (schedulerInfo.getTriggerType() == TriggerTypeEnum.SIMPLE) {
            newSimpleTriggerBuilder(schedulerInfo, triggerBuilder);
        } else if (schedulerInfo.getTriggerType() == TriggerTypeEnum.CRON) {
            newCronTriggerBuilder(schedulerInfo, triggerBuilder);
        }
        Trigger trigger = triggerBuilder.build();

        // 将调度任务和触发器添加到 scheduler 并启动
        scheduler.scheduleJob(jobDetail, trigger);
        if (schedulerInfo.getStatus() != TriggerStatusEnum.EXECUTING) {
            JobKey jobKey = new JobKey(schedulerInfo.getJobName(),
                schedulerInfo.getJobGroup());
            TriggerKey triggerKey = new TriggerKey(schedulerInfo.getTriggerName(),
                schedulerInfo.getTriggerGroup());
            scheduler.pauseJob(jobKey);
            scheduler.pauseTrigger(triggerKey);
        }
    }

    private void newSimpleTriggerBuilder(Scheduler schedulerInfo,
        TriggerBuilder<Trigger> triggerBuilder) {
        SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule();
        scheduleBuilder.withIntervalInSeconds(schedulerInfo.getRepeatInterval());
        if (schedulerInfo.getRepeatCount() != null
            && schedulerInfo.getRepeatCount() != -1) {
            scheduleBuilder.withRepeatCount(schedulerInfo.getRepeatCount());
        } else {
            scheduleBuilder.repeatForever();
        }
        triggerBuilder.withSchedule(scheduleBuilder);
    }

    private void newCronTriggerBuilder(Scheduler schedulerInfo,
        TriggerBuilder<Trigger> triggerBuilder) {
        CronScheduleBuilder scheduleBuilder = CronScheduleBuilder
            .cronSchedule(schedulerInfo.getCronExpression());
        scheduleBuilder.inTimeZone(TimeZone.getTimeZone("GMT+8"));
        triggerBuilder.withSchedule(scheduleBuilder);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult deleteJob(Scheduler schedulerInfo) {
        if (schedulerInfo == null) {
            return ResultUtils.failDataResult(AppCode.ERROR_PARAMETER);
        }

        DataListResult<Scheduler> dataListResult = super.list(schedulerInfo);
        if (ResultUtils.isNotValid(dataListResult)) {
            return ResultUtils.failBaseResult(AppCode.ERROR_DB.getCode(), "未找到调度器");
        }

        for (Scheduler item : dataListResult.getData()) {
            // 删除记录
            BaseResult baseResult = super.removeById(item.getId());
            if (ResultUtils.isNotValid(baseResult)) {
                return ResultUtils.failBaseResult(AppCode.ERROR_DB.getCode(),
                    "删除调度作业数据库记录失败");
            }

            try {
                deleteQuartzJob(item);
            } catch (SchedulerException e) {
                List<String> messages = new ArrayList<>();
                messages.add("删除调度作业失败");
                messages.add(e.getMessage());
                return ResultUtils.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(),
                    messages);
            }

            if (log.isDebugEnabled()) {
                log.debug("删除作业 jobGroup={}, jobName={} 成功", item.getJobGroup(),
                    item.getJobName());
            }
        }

        return ResultUtils.successBaseResult();
    }

    private boolean deleteQuartzJob(Scheduler schedulerInfo) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfo.getJobName(),
            schedulerInfo.getJobGroup());
        TriggerKey triggerKey = TriggerKey.triggerKey(schedulerInfo.getTriggerName(),
            schedulerInfo.getTriggerGroup());
        // 删除触发器
        scheduler.pauseTrigger(triggerKey);
        scheduler.unscheduleJob(triggerKey);
        // 删除任务
        scheduler.deleteJob(jobKey);
        return true;
    }

    @Override
    public BaseResult executeJob(Scheduler schedulerInfo) {
        if (schedulerInfo == null) {
            return ResultUtils.failDataResult(AppCode.ERROR_PARAMETER);
        }

        try {
            executeQuartzJob(schedulerInfo);
        } catch (SchedulerException e) {
            log.error("执行调度作业 jobGroup={},jobName={} 失败", schedulerInfo.getJobGroup(),
                schedulerInfo.getJobName(), e);
            return ResultUtils.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(),
                "执行调度作业失败");
        }
        return ResultUtils.successBaseResult();
    }

    private void executeQuartzJob(Scheduler schedulerInfo) throws SchedulerException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(SchedulerConstant.BEAN_NAME, schedulerInfo.getBeanName());
        jobDataMap.put(SchedulerConstant.BEAN_TYPE, schedulerInfo.getBeanType());
        jobDataMap.put(SchedulerConstant.METHOD_NAME, "execute");
        jobDataMap.put(SchedulerConstant.METHOD_PARAMS, schedulerInfo.getMethodParams());

        JobKey jobKey = new JobKey(schedulerInfo.getJobName(),
            schedulerInfo.getJobGroup());
        JobDetail jobDetail = scheduler.getJobDetail(jobKey);
        if (jobDetail == null) {
            // 构建 Quartz 调度任务
            jobDetail = JobBuilder.newJob(ExecuteMethodJobHandler.class)
                .withIdentity(schedulerInfo.getJobName(), schedulerInfo.getJobGroup())
                .setJobData(jobDataMap).build();

            // 构建 Quartz 触发器
            TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger()
                .withIdentity(schedulerInfo.getTriggerName(),
                    schedulerInfo.getTriggerGroup());
            SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder
                .simpleSchedule();
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

    @Override
    public BaseResult pauseJob(Scheduler schedulerInfo) {
        if (schedulerInfo == null) {
            return ResultUtils.failDataResult(AppCode.ERROR_PARAMETER);
        }

        DataListResult<Scheduler> dataListResult = super.list(schedulerInfo);
        if (ResultUtils.isNotValid(dataListResult)) {
            return ResultUtils.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(), "未找到调度器");
        }

        for (Scheduler item : dataListResult.getData()) {
            item.setStatus(TriggerStatusEnum.PAUSED);
            BaseResult baseResult = super.updateById(item);
            if (ResultUtils.isNotValid(baseResult)) {
                return ResultUtils.failBaseResult(AppCode.ERROR_DB.getCode(),
                    "更新调度作业数据库记录失败");
            }

            try {
                pauseQuartzJob(item);
            } catch (SchedulerException e) {
                List<String> messages = new ArrayList<>();
                messages.add("暂停调度作业失败");
                messages.add(e.getMessage());
                return ResultUtils.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(),
                    messages);
            }
        }
        return ResultUtils.successBaseResult();
    }

    private void pauseQuartzJob(Scheduler schedulerInfo) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfo.getJobName(),
            schedulerInfo.getJobGroup());
        TriggerKey triggerKey = TriggerKey.triggerKey(schedulerInfo.getTriggerName(),
            schedulerInfo.getTriggerGroup());
        scheduler.pauseJob(jobKey);
        scheduler.pauseTrigger(triggerKey);
    }

    @Override
    public BaseResult resumeJob(Scheduler schedulerInfo) {
        if (schedulerInfo == null) {
            return ResultUtils.failDataResult(AppCode.ERROR_PARAMETER);
        }

        DataListResult<Scheduler> dataListResult = super.list(schedulerInfo);
        if (ResultUtils.isNotValid(dataListResult)) {
            return ResultUtils.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(), "未找到调度器");
        }

        for (Scheduler item : dataListResult.getData()) {
            item.setStatus(TriggerStatusEnum.EXECUTING);
            BaseResult baseResult = super.updateById(item);
            if (ResultUtils.isNotValid(baseResult)) {
                return ResultUtils.failBaseResult(AppCode.ERROR_DB.getCode(),
                    "更新调度作业数据库记录失败");
            }

            try {
                resumeQuartzJob(item);
            } catch (SchedulerException e) {
                List<String> messages = new ArrayList<>();
                messages.add("恢复调度作业失败");
                messages.add(e.getMessage());
                return ResultUtils.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(),
                    messages);
            }
        }
        return ResultUtils.successBaseResult();
    }

    private void resumeQuartzJob(Scheduler schedulerInfo) throws SchedulerException {
        JobKey jobKey = new JobKey(schedulerInfo.getJobName(),
            schedulerInfo.getJobGroup());
        TriggerKey triggerKey = TriggerKey.triggerKey(schedulerInfo.getTriggerName(),
            schedulerInfo.getTriggerGroup());
        scheduler.resumeJob(jobKey);
        scheduler.resumeTrigger(triggerKey);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult updateJob(Scheduler schedulerInfo) {
        if (schedulerInfo == null) {
            return ResultUtils.failDataResult(AppCode.ERROR_PARAMETER);
        }

        DataResult<Scheduler> dataResult = super.getById(schedulerInfo.getId());
        if (ResultUtils.isNotValid(dataResult)) {
            return ResultUtils.failBaseResult(AppCode.ERROR_DB.getCode(), "未找到记录");
        }

        BaseResult baseResult = super.updateById(schedulerInfo);
        if (ResultUtils.isNotValid(baseResult)) {
            return ResultUtils.failBaseResult(AppCode.ERROR_DB.getCode(), "更新调度作业数据库记录失败");
        }

        try {
            // 由于作业组、作业名都可能发生变更，所以必须重新创建作业
            if (!deleteQuartzJob(schedulerInfo)) {
                return ResultUtils.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(),
                    "删除调度作业失败");
            }
            createQuartzJob(schedulerInfo);
        } catch (SchedulerException e) {
            log.error("更新调度作业 jobGroup={},jobName={} 失败", schedulerInfo.getJobGroup(),
                schedulerInfo.getJobName(), e);
            return ResultUtils.failBaseResult(AppCode.ERROR_SCHEDULER.getCode(),
                "创建调度作业失败");
        }
        return ResultUtils.successBaseResult();
    }

    @Override
    public List<BeanDTO> getJobHandlers() {
        Reflections reflections = new Reflections(
            "io.github.dunwu.quickstart.scheduler.job");
        Set<Class<?>> types = reflections.getTypesAnnotatedWith(JobHandler.class);
        List<BeanDTO> beans = new ArrayList<>();
        for (Class<?> clazz : types) {
            JobHandler annotation = clazz.getAnnotation(JobHandler.class);
            String beanName = annotation.value();
            BeanDTO beanDTO = new BeanDTO();
            beanDTO.setBeanName(beanName);
            beanDTO.setBeanType(clazz);
            beans.add(beanDTO);
        }
        return beans;
    }

    @PostConstruct
    public void init() {
        DataListResult<Scheduler> dataListResult = list();
        try {
            for (Scheduler item : dataListResult.getData()) {
                createQuartzJob(item);
            }
        } catch (SchedulerException e) {
            log.error("系统启动加载调度器失败", e);
        }
    }

}
