package io.github.dunwu.quickstart.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.quickstart.user.constant.GenderEnum;
import io.github.dunwu.util.text.RegexUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
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

	@NotNull
	@Length(min = 6, max = 10)
	@ApiModelProperty(value = "姓名")
	private String name;

	@Past
	@ApiModelProperty(value = "生日")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate birthday;

	@Range(min = 1, max = 2)
	@ApiModelProperty(value = "性别", example = "0")
	private GenderEnum sex;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@Email
	@ApiModelProperty(value = "邮箱")
	private String email;

	@Pattern(regexp = RegexUtils.REGEX_MOBILE, message = "不是合法手机号")
	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "地址")
	private String address;

	@ApiModelProperty(value = "逻辑删除标记", example = "0")
	private Boolean deleted;

	@ApiModelProperty(value = "角色列表")
	private List<RoleDTO> roles;

}
