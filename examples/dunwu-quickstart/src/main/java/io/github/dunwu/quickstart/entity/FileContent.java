package io.github.dunwu.quickstart.entity;

import io.github.dunwu.data.entity.BaseEntity;
import java.sql.Blob;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 文件内容表
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-24
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@ApiModel(value = "FileContent对象", description = "文件内容表")
public class FileContent extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "文件名")
    private String name;

    @ApiModelProperty(value = "文件内容")
    private Blob content;


}
