package io.github.dunwu.quickstart.user.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.data.entity.BaseRecordEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.time.LocalDate;

/**
 * 用户信息表数据实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "User", description = "用户信息表")
public class User extends BaseRecordEntity {

	private static final long serialVersionUID = 1L;

	@ApiModelProperty(value = "昵称")
	private String nickname;

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "生日")
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private LocalDate birthday;

	@ApiModelProperty(value = "性别", example = "0")
	private Integer sex;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "邮箱")
	private String email;

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

}
