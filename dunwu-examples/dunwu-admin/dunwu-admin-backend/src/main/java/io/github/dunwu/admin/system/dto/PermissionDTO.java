package io.github.dunwu.admin.system.dto;

import io.github.dunwu.admin.constant.DataStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-29
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "Permission", description = "权限信息")
public class PermissionDTO implements Serializable {

    private static final long serialVersionUID = 4928114141381025869L;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "权限表达式")
    private String expression;

    @ApiModelProperty(value = "权限类型")
    private String type;

    @ApiModelProperty(value = "状态，0 为有效，1 为无效", example = "0")
    private DataStatus status;

    @ApiModelProperty(value = "备注")
    private String notes;

}
