package io.github.dunwu.admin.filesystem.dto;

import io.github.dunwu.admin.filesystem.constant.FileStoreTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

/**
 * 上传文件信息 DTO
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-29
 */
@Data
@ToString
public class UploadFileDTO implements Serializable {

    private static final long serialVersionUID = 1L;

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

    @ApiModelProperty(value = "上传的文件")
    private MultipartFile file;

}
