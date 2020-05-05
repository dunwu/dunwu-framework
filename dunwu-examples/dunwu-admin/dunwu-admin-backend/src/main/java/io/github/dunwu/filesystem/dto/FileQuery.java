package io.github.dunwu.filesystem.dto;

import io.github.dunwu.filesystem.constant.FileStoreTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 文件查询实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-25
 */
@Data
@ToString
@ApiModel(value = "FileQuery", description = "文件查询实体")
public class FileQuery implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID", example = "0")
    private String id;

    @ApiModelProperty(value = "命名空间。一般对应业务系统")
    private String namespace;

    @ApiModelProperty(value = "标签。供业务系统使用")
    private String tag;

    @ApiModelProperty(value = "源文件名")
    private String originName;

    @ApiModelProperty(value = "实际文件名")
    private String fileName;

    @ApiModelProperty(value = "文件存储服务类型")
    private FileStoreTypeEnum storeType;

}
