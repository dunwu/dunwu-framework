package io.github.dunwu.quickstart.scheduler.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.data.entity.BaseRecordEntity;
import io.github.dunwu.quickstart.scheduler.constant.TriggerStatusEnum;
import io.github.dunwu.quickstart.scheduler.constant.TriggerTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 调度信息表数据实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Scheduler", description = "调度信息表")
public class Scheduler extends BaseRecordEntity {

	private static final long serialVersionUID = 1L;

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

	@ApiModelProperty(value = "触发类型", example = "0")
	private TriggerTypeEnum triggerType;

	@ApiModelProperty(value = "调用类型", example = "0")
	private Integer invokeType;

	@ApiModelProperty(value = "触发开始时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime beginTime;

	@ApiModelProperty(value = "触发结束时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
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
