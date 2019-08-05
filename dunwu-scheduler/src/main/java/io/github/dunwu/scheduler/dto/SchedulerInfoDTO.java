package io.github.dunwu.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.scheduler.constant.InvokeTypeEnum;
import io.github.dunwu.scheduler.constant.TriggerStatusEnum;
import io.github.dunwu.scheduler.constant.TriggerTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 调度信息 DTO
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-30
 */
@Data
@ToString
public class SchedulerInfoDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID", example = "0")
    protected String id;

    @ApiModelProperty(value = "调度器名")
    private String schedulerName;

    @ApiModelProperty(value = "触发器组")
    private String triggerGroup;

    @ApiModelProperty(value = "触发器名称")
    private String triggerName;

    @ApiModelProperty(value = "任务组")
    private String jobGroup;

    @ApiModelProperty(value = "任务名")
    private String jobName;

    @ApiModelProperty(value = "Bean名称")
    private String beanName;

    @ApiModelProperty(value = "Bean类型")
    private String beanType;

    @ApiModelProperty(value = "方法名称")
    private String methodName;

    @ApiModelProperty(value = "方法参数（JSON 形式）")
    private String methodParams;

    @ApiModelProperty(value = "触发类型")
    private TriggerTypeEnum triggerType;

    @ApiModelProperty(value = "调用类型")
    private InvokeTypeEnum invokeType;

    @ApiModelProperty(value = "时间范围")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private List<LocalDateTime> timeRange;

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

    @ApiModelProperty(value = "创建者")
    private String createUser;

    @ApiModelProperty(value = "更新者")
    private String updateUser;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}
