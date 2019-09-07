package io.github.dunwu.quickstart.scheduler.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.quickstart.scheduler.entity.SchedulerInfo;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
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
@EqualsAndHashCode(callSuper = true)
public class SchedulerInfoDTO extends SchedulerInfo implements Serializable {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "时间范围")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private List<LocalDateTime> timeRange;

}
