package io.github.dunwu.data.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class RecordEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创建者")
    protected String createUser;

    @ApiModelProperty(value = "更新者")
    protected String updateUser;

    @ApiModelProperty(value = "创建时间")
    protected LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    protected LocalDateTime updateTime;
}
