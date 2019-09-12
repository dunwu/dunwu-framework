package io.github.dunwu.quickstart.user.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.util.text.RegexUtil;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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

	@NotNull
	@Length(min = 6, max = 10)
	@ApiModelProperty(value = "昵称")
	private String nickname;

	@NotNull
	@Length(min = 6, max = 10)
	@ApiModelProperty(value = "姓名")
	private String name;

	@NotNull
	@ApiModelProperty(value = "密码")
	private String password;

	@Past
	@ApiModelProperty(value = "生日")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate birthday;

	@Range(min = 1, max = 2)
	@ApiModelProperty(value = "性别", example = "0")
	private Integer sex;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@Email
	@ApiModelProperty(value = "邮箱")
	private String email;

	@Pattern(regexp = RegexUtil.REGEX_MOBILE, message = "不是合法手机号")
	@ApiModelProperty(value = "手机号")
	private String mobile;

	@ApiModelProperty(value = "职业")
	private String profession;

	@ApiModelProperty(value = "省")
	private String province;

	@ApiModelProperty(value = "市")
	private String city;

	@ApiModelProperty(value = "区")
	private String county;

	@ApiModelProperty(value = "逻辑删除标记", example = "0")
	private Integer deleted;

	@ApiModelProperty(value = "创建者")
	private String createUser;

	@ApiModelProperty(value = "更新者")
	private String updateUser;

	@ApiModelProperty(value = "创建时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime createTime;

	@ApiModelProperty(value = "更新时间")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private LocalDateTime updateTime;

	private String token;

	private String introduction;

	private List<String> roles;

	private String currentAuthority;

}
