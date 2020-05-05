package io.github.dunwu.template.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 模板配置表数据实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
@Data
@ToString
@Accessors(chain = true)
@ApiModel(value = "Template", description = "模板配置表")
public class Template implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime createTime;

    @ApiModelProperty(value = "更新时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    protected LocalDateTime updateTime;

    @TableLogic
    @TableField(select = false)
    @ApiModelProperty(value = "逻辑删除标记。不需要用户填值。", example = "0")
    protected Boolean deleted = false;

    @ApiModelProperty(value = "模板名")
    @TableField(condition = "%s LIKE CONCAT('%%',#{%s},'%%')")
    private String name;

    @ApiModelProperty(value = "命名空间")
    private String namespace;

    @ApiModelProperty(value = "标签")
    private String tag;

    @ApiModelProperty(value = "模板内容")
    private String content;

    @ApiModelProperty(value = "模板元数据")
    private String metadata;

}
