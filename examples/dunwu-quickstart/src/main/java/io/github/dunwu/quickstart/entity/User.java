package io.github.dunwu.quickstart.entity;

import io.github.dunwu.data.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 用户表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-20
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value="User对象", description="用户表")
public class User extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "姓名")
    private String name;

    @ApiModelProperty(value = "年龄", example = "0")
    private Integer age;

    @ApiModelProperty(value = "性别,0:MALE, 1:FEMALE", example = "0")
    private Integer gender;

    @ApiModelProperty(value = "收入", example = "0")
    private Double salary;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "角色ID", example = "0")
    private Long roleId;

    @ApiModelProperty(value = "是否逻辑删除。0:未删除，1:已删除。", example = "0")
    private Integer deleted;


}
