package io.github.dunwu.quickstart.user.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-14
 */
@Data
@ToString
public class LoginDTO implements Serializable {

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "手机区号")
	private String prefix;

	@ApiModelProperty(value = "手机号")
	private String mobile;

}
