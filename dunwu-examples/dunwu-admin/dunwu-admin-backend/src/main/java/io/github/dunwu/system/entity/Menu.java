package io.github.dunwu.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * 菜单信息数据实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-02
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "Menu", description = "菜单信息")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "菜单ID", example = "0")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "父菜单ID", example = "0")
    private Integer parentId;

    @ApiModelProperty(value = "菜单URL")
    private String url;

    @ApiModelProperty(value = "菜单名称")
    private String name;

    @ApiModelProperty(value = "权限表达式")
    private String perms;

    @ApiModelProperty(value = "菜单类型")
    private String type;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单权重", example = "0")
    private Integer power;

    @ApiModelProperty(value = "状态，0 为有效，1 为无效", example = "0")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String notes;

}
