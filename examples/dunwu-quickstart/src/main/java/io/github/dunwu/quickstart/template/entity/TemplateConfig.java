package io.github.dunwu.quickstart.template.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import io.github.dunwu.data.entity.BaseRecordEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.sql.Blob;
import java.time.LocalDateTime;

/**
 * 模板配置表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-26
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "TemplateConfig对象", description = "模板配置表")
public class TemplateConfig extends BaseRecordEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "模板名")
    @TableField(condition = "%s LIKE CONCAT('%%',#{%s},'%%')")
    private String templateName;

    @ApiModelProperty(value = "命名空间。一般对应业务系统")
    private String namespace;

    @ApiModelProperty(value = "标签。供业务系统使用")
    private String tag;

    @ApiModelProperty(value = "模板内容")
    private String content;

    @ApiModelProperty(value = "模板元数据")
    private String metadata;

}
