package io.github.dunwu.quickstart.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import io.github.dunwu.data.entity.BaseEntity;

/**
 * 权限表数据实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-17
 */
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Permission", description = "权限表")
public class Permission extends BaseEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "模块")
	private String module;

	@ApiModelProperty(value = "权限名")
	private String name;

	@ApiModelProperty(value = "权限类型")
	private String type;

	@ApiModelProperty(value = "表达式")
	private String expression;

	@ApiModelProperty(value = "状态", example = "0")
	private Integer status;

	@ApiModelProperty(value = "备注")
	private String notes;

}
