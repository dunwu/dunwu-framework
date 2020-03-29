package io.github.dunwu.admin.system.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import io.github.dunwu.admin.constant.DataStatus;
import io.github.dunwu.admin.util.SimpleTree;
import io.github.dunwu.tool.collection.CollectionUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-26
 */
@Valid
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "Menu", description = "菜单信息")
public class MenuDTO implements SimpleTree, Serializable {

    private static final long serialVersionUID = -5595786953543337558L;

    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty(value = "ID", example = "0")
    protected Integer id;

    @ApiModelProperty(value = "父菜单ID", example = "0")
    private Integer parentId;

    @NotBlank
    @ApiModelProperty(value = "菜单URL")
    private String url;

    @NotBlank
    @ApiModelProperty(value = "菜单名称")
    private String name;

    @NotBlank
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

    @ApiModelProperty(value = "子菜单", example = "0")
    private Collection<MenuDTO> children = Collections.emptySet();

    @Override
    public boolean isLeaf() {
        return CollectionUtil.isEmpty(children);
    }

}
