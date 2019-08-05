package io.github.dunwu.scheduler.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.github.dunwu.data.entity.BaseRecordEntity;
import io.github.dunwu.scheduler.constant.InvokeTypeEnum;
import io.github.dunwu.scheduler.constant.TriggerStatusEnum;
import io.github.dunwu.scheduler.constant.TriggerTypeEnum;
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
 * @since 2019-08-01
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "SchedulerInfo对象", description = "调度信息表")
public class SchedulerInfo extends BaseRecordEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "调度器名")
    private String schedulerName;

    @ApiModelProperty(value = "触发器组")
    private String triggerGroup;

    @ApiModelProperty(value = "触发器名称")
    private String triggerName;

    @ApiModelProperty(value = "任务组")
    @TableField(condition = "%s LIKE CONCAT('%%',#{%s},'%%')")
    private String jobGroup;

    @ApiModelProperty(value = "任务名")
    @TableField(condition = "%s LIKE CONCAT('%%',#{%s},'%%')")
    private String jobName;

    @ApiModelProperty(value = "Bean名称")
    @TableField(condition = "%s LIKE CONCAT('%%',#{%s},'%%')")
    private String beanName;

    @ApiModelProperty(value = "Bean类型")
    @TableField(condition = "%s LIKE CONCAT('%%',#{%s},'%%')")
    private String beanType;

    @ApiModelProperty(value = "方法名称")
    @TableField(condition = "%s LIKE CONCAT('%%',#{%s},'%%')")
    private String methodName;

    @ApiModelProperty(value = "方法参数（JSON 形式）")
    private String methodParams;

    @ApiModelProperty(value = "触发类型")
    private TriggerTypeEnum triggerType;

    @ApiModelProperty(value = "调用类型")
    private InvokeTypeEnum invokeType;

    @ApiModelProperty(value = "触发开始时间")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "触发结束时间")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "重复调度间隔", example = "0")
    private Integer repeatInterval;

    @ApiModelProperty(value = "重复调度次数", example = "0")
    private Integer repeatCount;

    @ApiModelProperty(value = "CRON 表达式")
    private String cronExpression;

    @ApiModelProperty(value = "状态", example = "0")
    private TriggerStatusEnum status;

    @ApiModelProperty(value = "备注")
    private String note;
}
