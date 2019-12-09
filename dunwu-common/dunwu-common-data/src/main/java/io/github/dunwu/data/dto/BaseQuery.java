package io.github.dunwu.data.dto;

import io.github.dunwu.core.Pagination;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-06
 */
@Data
@ToString
@Accessors(chain = true)
public abstract class BaseQuery<T> implements Serializable {

	@ApiModelProperty(value = "ID", example = "0")
	protected String id;

	@ApiModelProperty(value = "创建者")
	protected String createUser;

	@ApiModelProperty(value = "更新者")
	protected String updateUser;

	@ApiModelProperty(value = "创建时间")
	protected LocalDateTime createTime;

	@ApiModelProperty(value = "更新时间")
	protected LocalDateTime updateTime;

	@ApiModelProperty(value = "分页信息")
	private Pagination<T> pagination;

}
