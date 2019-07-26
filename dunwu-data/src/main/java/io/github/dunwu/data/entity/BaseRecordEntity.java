package io.github.dunwu.data.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.time.LocalDateTime;

/**
 * 基础记录数据库实体类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-27
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public abstract class BaseRecordEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "创建者")
    protected String createUser;

    @ApiModelProperty(value = "更新者")
    protected String updateUser;

    @ApiModelProperty(value = "创建时间")
    protected LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    protected LocalDateTime updateTime;

    @Version
    @ApiModelProperty(value = "版本号。用于乐观锁，不需要用户填值。")
    protected Integer version;

    @TableLogic
    @TableField(select = false)
    @ApiModelProperty(value = "逻辑删除标记。不需要用户填值。", example = "0")
    protected Boolean deleted;
}
