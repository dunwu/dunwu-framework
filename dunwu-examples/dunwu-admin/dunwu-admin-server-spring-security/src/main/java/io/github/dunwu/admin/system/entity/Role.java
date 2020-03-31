package io.github.dunwu.admin.system.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.github.dunwu.admin.constant.DataStatus;
import io.github.dunwu.data.mybatis.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * 角色信息数据实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-25
 */
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("role")
@ApiModel(value = "Role", description = "角色信息")
public class Role extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "角色编码")
    private String code;

    @ApiModelProperty(value = "角色名")
    private String name;

    @ApiModelProperty(value = "状态，0 为有效，1 为无效", example = "0")
    private DataStatus status;

    @ApiModelProperty(value = "备注")
    private String notes;

}
