package io.github.dunwu.admin.filesystem.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.github.dunwu.admin.filesystem.constant.FileStoreTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * 文件信息 DTO
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-23
 */
@Data
@ToString
@ApiModel(value = "FileDTO", description = "文件数据传输实体")
public class FileDTO implements Serializable {

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

    @ApiModelProperty(value = "文件大小", example = "0")
    private Long size;

    @ApiModelProperty(value = "文件扩展名")
    private String extension;

    @ApiModelProperty(value = "文件实际类型")
    private String contentType;

    @ApiModelProperty(value = "文件存储服务类型")
    private FileStoreTypeEnum storeType;

    @ApiModelProperty(value = "文件存储路径")
    private String storeUrl;

    @ApiModelProperty(value = "文件访问路径")
    private String accessUrl;

    @ApiModelProperty(value = "文件内容")
    private byte[] content;

    @ApiModelProperty(value = "上传时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

}
