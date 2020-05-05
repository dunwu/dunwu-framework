package io.github.dunwu.app.system.query;

import io.github.dunwu.data.core.annotation.QueryField;
import io.github.dunwu.data.core.annotation.QueryTable;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-07
 */
@Data
@QueryTable
public class UserQuery implements Serializable {

    @QueryField
    private Long id;

    @QueryField(blurry = { "username", "email", "mobile" }, type = QueryField.QueryType.EQUAL)
    private String blurry;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "手机号")
    private String mobile;

    @QueryField
    @ApiModelProperty(value = "性别")
    private Integer sex;

    @QueryField
    @ApiModelProperty(value = "状态，0 为有效，1 为无效")
    private Integer status;

}
