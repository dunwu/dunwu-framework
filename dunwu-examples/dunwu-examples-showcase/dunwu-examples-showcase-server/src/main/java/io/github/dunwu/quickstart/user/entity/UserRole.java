package io.github.dunwu.quickstart.user.entity;

import io.github.dunwu.data.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 用户角色表数据实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserRole", description = "用户角色表")
public class UserRole extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "用户ID", example = "0")
	private Integer userId;

	@ApiModelProperty(value = "角色ID", example = "0")
	private Integer roleId;

}
