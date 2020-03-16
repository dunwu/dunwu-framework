package io.github.dunwu.quickstart.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Valid
@Data
@ToString
@ApiModel(value = "UserDTO", description = "用户信息 DTO")
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID", example = "0")
    private Integer id;

    @NotNull
    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "角色类型")
    private String type;

    @NotNull
    @ApiModelProperty(value = "角色code")
    private String code;

    @ApiModelProperty(value = "状态", example = "0")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String notes;

}
