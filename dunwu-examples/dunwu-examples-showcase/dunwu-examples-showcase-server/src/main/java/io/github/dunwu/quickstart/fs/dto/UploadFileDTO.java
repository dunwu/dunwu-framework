package io.github.dunwu.quickstart.fs.dto;

import io.github.dunwu.quickstart.fs.constant.FileStoreTypeEnum;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件信息 DTO
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-29
 */
@Data
@ToString
public class UploadFileDTO {

    @ApiModelProperty(value = "命名空间。一般对应业务系统")
    private String namespace;

    @ApiModelProperty(value = "标签。供业务系统使用")
    private String tag;

    @ApiModelProperty(value = "源文件名")
    private String originName;

    @ApiModelProperty(value = "文件存储服务类型")
    private FileStoreTypeEnum storeType;

    @ApiModelProperty(value = "上传的文件")
    private MultipartFile file;
}
