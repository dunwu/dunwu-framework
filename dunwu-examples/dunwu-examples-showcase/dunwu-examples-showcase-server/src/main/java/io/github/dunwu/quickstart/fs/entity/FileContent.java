package io.github.dunwu.quickstart.fs.entity;

import io.github.dunwu.data.entity.BaseEntity;
import io.github.dunwu.quickstart.fs.constant.FileStoreTypeEnum;
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

	@ApiModelProperty(value = "实际文件名")
	private String fileName;

	@ApiModelProperty(value = "命名空间。一般对应业务系统")
	private String namespace;

	@ApiModelProperty(value = "标签。供业务系统使用")
	private String tag;

	@ApiModelProperty(value = "源文件名")
	private String originName;

	@ApiModelProperty(value = "文件存储服务类型")
	private FileStoreTypeEnum storeType;

	@ApiModelProperty(value = "文件存储路径")
	private String storeUrl;

	@ApiModelProperty(value = "文件内容")
	private byte[] content;

}
