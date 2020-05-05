package io.github.dunwu.system.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

/**
 * 权限信息数据实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-02
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "Permission", description = "权限信息")
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "权限ID", example = "0")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ApiModelProperty(value = "权限名称")
    private String name;

    @ApiModelProperty(value = "权限表达式")
    private String expression;

    @ApiModelProperty(value = "权限类型")
    private String type;

    @ApiModelProperty(value = "状态，0 为有效，1 为无效", example = "0")
    private Integer status;

    @ApiModelProperty(value = "备注")
    private String notes;

}
