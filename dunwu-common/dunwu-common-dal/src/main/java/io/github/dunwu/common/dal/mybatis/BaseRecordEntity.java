package io.github.dunwu.common.dal.mybatis;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 基础审计实体类
 * <p>
 * 包含常见的数据库审计字段，如 ID、启用/禁用状态、备注、创建/更新者及时间。
 * 所有继承此类的实体将自动拥有这些通用属性。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-27
 */
@Data
@Accessors(chain = true)
public abstract class BaseRecordEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键 ID，自增
     */
    @Schema(description = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    protected Long id;

    /**
     * 是否禁用状态
     * <p>
     * {@code true} 表示禁用，{@code false} 表示启用。
     */
    @Schema(description = "是否禁用，1表示禁用，0表示启用")
    protected Boolean isDisabled;

    /**
     * 备注信息
     */
    @Schema(description = "备注")
    protected String note;

    /**
     * 创建者 ID
     */
    @Schema(description = "创建者")
    protected Long createBy;

    /**
     * 最后更新者 ID
     */
    @Schema(description = "更新者")
    protected Long updateBy;

    /**
     * 记录创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "创建时间")
    protected LocalDateTime createTime;

    /**
     * 记录最后更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Schema(description = "更新时间")
    protected LocalDateTime updateTime;

}
