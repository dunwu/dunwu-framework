package io.github.dunwu.quickstart.filesystem.service;

import io.github.dunwu.quickstart.filesystem.dto.FileDTO;
import io.github.dunwu.quickstart.filesystem.dto.UploadFileDTO;
import io.github.dunwu.quickstart.filesystem.service.impl.DbFileStorageServiceImpl;
import io.github.dunwu.quickstart.filesystem.service.impl.TempFileStorageServiceImpl;

import java.io.IOException;

/**
 * 文件存储服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see DbFileStorageServiceImpl
 * @see TempFileStorageServiceImpl
 * @since 2019-07-24
 */
public interface FileStorageService {

	/**
	 * 添加文件到存储介质
	 * @param uploadFileDTO 上传文件信息
	 * @return 成功返回文件信息 DTO；失败返回 null
	 * @throws IOException IO 异常
	 */
	String create(UploadFileDTO uploadFileDTO) throws IOException;

	/**
	 * 从存储介质删除文件
	 * @param fileDTO 上传文件内容
	 * @return 成功返回 true；失败返回 false
	 * @throws IOException IO 异常
	 */
	boolean delete(FileDTO fileDTO) throws IOException;

	/**
	 * 从存储介质删除文件
	 * @param fileDTO 查询条件
	 * @return 成功返回文件内容 DTO；失败返回 null(含未找到)
	 * @throws IOException IO 异常
	 */
	FileDTO getContent(FileDTO fileDTO) throws IOException;

}
