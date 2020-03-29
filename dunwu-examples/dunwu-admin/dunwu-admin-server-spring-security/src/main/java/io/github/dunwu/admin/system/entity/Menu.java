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

import javax.validation.Valid;

/**
 * 菜单信息数据实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-25
 */
@Valid
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@TableName("t_menu")
@ApiModel(value = "Menu", description = "菜单信息")
public class Menu extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "父菜单ID", example = "0")
    private Integer parentId = 0;

    @ApiModelProperty(value = "菜单URL")
    private String url;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "权限表达式")
    private String perms;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单类型")
    private String type;

    @ApiModelProperty(value = "菜单权重", example = "0")
    private Integer power;

    @ApiModelProperty(value = "备注")
    private String notes;

    @ApiModelProperty(value = "状态，0 为有效，1 为无效", example = "0")
    private DataStatus status;

}
