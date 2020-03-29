package io.github.dunwu.admin.system.dto;

import io.github.dunwu.admin.constant.DataStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Valid
@Data
@ToString
@ApiModel(value = "Role", description = "角色信息")
public class RoleDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID", example = "0")
    private Integer id;

    @NotBlank
    @ApiModelProperty(value = "角色编码")
    private String code;

    @NotBlank
    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "状态，0 为有效，1 为无效", example = "0")
    private DataStatus status;

    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty(value = "角色拥有的权限")
    private Collection<PermissionDTO> permissions;

    public RoleDTO() {
        this.permissions = Collections.emptyList();
    }

}
