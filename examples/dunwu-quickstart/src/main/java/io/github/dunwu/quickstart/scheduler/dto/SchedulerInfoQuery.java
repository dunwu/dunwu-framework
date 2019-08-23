package io.github.dunwu.quickstart.scheduler.dto;

import io.github.dunwu.scheduler.constant.InvokeTypeEnum;
import io.github.dunwu.scheduler.constant.TriggerStatusEnum;
import io.github.dunwu.scheduler.constant.TriggerTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 调度信息 Query
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-31
 */
@Data
@ToString
public class SchedulerInfoQuery {

    @ApiModelProperty(value = "ID", example = "0")
    protected String id;

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

    @ApiModelProperty(value = "触发类型")
    private TriggerTypeEnum triggerType;

    @ApiModelProperty(value = "调用类型")
    private InvokeTypeEnum invokeType;

    @ApiModelProperty(value = "状态", example = "0")
    private TriggerStatusEnum status;
}
