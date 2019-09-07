package io.github.dunwu.quickstart.fs.service.impl;

import io.github.dunwu.quickstart.fs.constant.FileSystemConstant;
import io.github.dunwu.quickstart.fs.dto.FileDTO;
import io.github.dunwu.quickstart.fs.dto.UploadFileDTO;
import io.github.dunwu.quickstart.fs.service.FileInfoService;
import io.github.dunwu.quickstart.fs.service.FileStorageService;
import io.github.dunwu.util.code.IdUtil;
import io.github.dunwu.util.io.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 文件存储服务基类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see DbFileStorageServiceImpl
 * @see TempFileStorageServiceImpl
 * @see FdfsFileStorageServiceImpl
 * @since 2019-07-29
 */
public abstract class BaseFileStorageService implements FileStorageService {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	protected FileInfoService fileInfoService;

	@Override
	public FileDTO getFileInfo(UploadFileDTO uploadFileDTO) throws IOException {

		MultipartFile file = uploadFileDTO.getFile();
		String extension = FileUtil.getFileExtension(file.getOriginalFilename());
		String originName = FileUtil.getFileName(uploadFileDTO.getOriginName())
				+ FileSystemConstant.FILE_SEPARATOR + extension;
		String fileName = IdUtil.uuid2() + FileSystemConstant.FILE_SEPARATOR
				+ extension.toLowerCase();
		FileDTO fileInfoDTO = new FileDTO();
		fileInfoDTO.setNamespace(uploadFileDTO.getNamespace());
		fileInfoDTO.setTag(uploadFileDTO.getTag());
		fileInfoDTO.setOriginName(originName);
		fileInfoDTO.setFileName(fileName);
		fileInfoDTO.setSize(file.getSize());
		fileInfoDTO.setExtension(extension.toLowerCase());
		fileInfoDTO.setContentType(file.getContentType());
		fileInfoDTO.setStoreType(uploadFileDTO.getStoreType());
		fileInfoDTO.setStoreUrl(fileName);
		StringBuilder sb = new StringBuilder();
		sb.append(uploadFileDTO.getNamespace()).append("/").append(uploadFileDTO.getTag())
				.append("/").append(originName);
		fileInfoDTO.setAccessUrl(sb.toString());
		fileInfoDTO.setContent(file.getBytes());
		fileInfoDTO.setUploadTime(LocalDateTime.now());
		return fileInfoDTO;
	}

}
