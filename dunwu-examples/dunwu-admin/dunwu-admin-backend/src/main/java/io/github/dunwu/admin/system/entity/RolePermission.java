package io.github.dunwu.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.dunwu.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
* 角色和权限关联信息数据实体
*
* @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
* @since 2020-03-28
*/
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("role_permission")
@ApiModel(value = "RolePermission", description = "角色和权限关联信息")
public class RolePermission extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色ID", example = "0")
    private Integer roleId;

    @ApiModelProperty(value = "权限ID", example = "0")
    private Integer permissionId;

}
