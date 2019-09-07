package io.github.dunwu.quickstart.user.entity;

import io.github.dunwu.data.entity.BaseRecordEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 登录信息表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "LoginInfo对象", description = "登录信息表")
public class LoginInfo extends BaseRecordEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "手机号")
	private String mobile;

}
