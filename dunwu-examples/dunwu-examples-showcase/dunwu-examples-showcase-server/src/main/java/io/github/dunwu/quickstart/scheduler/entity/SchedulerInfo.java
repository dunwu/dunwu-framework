package io.github.dunwu.quickstart.scheduler.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.data.entity.BaseRecordEntity;
import io.github.dunwu.quickstart.scheduler.constant.InvokeTypeEnum;
import io.github.dunwu.quickstart.scheduler.constant.TriggerStatusEnum;
import io.github.dunwu.quickstart.scheduler.constant.TriggerTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 调度信息表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SchedulerInfo对象", description = "调度信息表")
public class SchedulerInfo extends BaseRecordEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "调度器名")
    protected String schedulerName;

    @ApiModelProperty(value = "触发器组")
    protected String triggerGroup;

    @ApiModelProperty(value = "触发器名称")
    protected String triggerName;

    @ApiModelProperty(value = "任务组")
    protected String jobGroup;

    @ApiModelProperty(value = "任务名")
    protected String jobName;

    @ApiModelProperty(value = "Bean名称")
    protected String beanName;

    @ApiModelProperty(value = "Bean类型")
    protected String beanType;

    @ApiModelProperty(value = "方法名称")
    protected String methodName;

    @ApiModelProperty(value = "方法参数（JSON 形式）")
    protected String methodParams;

    @ApiModelProperty(value = "触发类型", example = "0")
    protected TriggerTypeEnum triggerType;

    @ApiModelProperty(value = "调用类型", example = "0")
    protected InvokeTypeEnum invokeType;

    @ApiModelProperty(value = "触发开始时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime beginTime;

    @ApiModelProperty(value = "触发结束时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime endTime;

    @ApiModelProperty(value = "重复调度间隔", example = "0")
    protected Integer repeatInterval;

    @ApiModelProperty(value = "重复调度次数", example = "0")
    protected Integer repeatCount;

    @ApiModelProperty(value = "CRON 表达式")
    protected String cronExpression;

    @ApiModelProperty(value = "状态", example = "0")
    protected TriggerStatusEnum status;

    @ApiModelProperty(value = "备注")
    protected String note;
}
