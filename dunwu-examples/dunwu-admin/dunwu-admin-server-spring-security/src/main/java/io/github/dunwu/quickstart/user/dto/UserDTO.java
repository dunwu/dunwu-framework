package io.github.dunwu.quickstart.user.dto;

import io.github.dunwu.quickstart.user.constant.GenderEnum;
import io.github.dunwu.tool.util.RegexUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-24
 */
@Valid
@Data
@ToString
@ApiModel(value = "UserDTO", description = "用户信息 DTO")
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID", example = "0")
    private Integer id;

    @NotNull
    @Length(min = 6, max = 10)
    @ApiModelProperty(value = "用户名")
    private String username;

    @NotNull
    @ApiModelProperty(value = "密码")
    private String password;

    @Range(min = 1, max = 2)
    @ApiModelProperty(value = "性别", example = "0")
    private GenderEnum sex;

    @ApiModelProperty(value = "头像")
    private String avatar;

    @Email
    @ApiModelProperty(value = "邮箱")
    private String email;

    @Pattern(regexp = RegexUtil.MOBILE, message = "不是合法手机号")
    @ApiModelProperty(value = "手机号")
    private String mobile;

    @ApiModelProperty(value = "地址")
    private String address;

    @ApiModelProperty(value = "状态，0 为有效，1 为无效", example = "0")
    private Integer status;

    @ApiModelProperty(value = "角色列表")
    private List<RoleDTO> roles;

}
