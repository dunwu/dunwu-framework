package io.github.dunwu.quickstart.fs.service.impl;

import io.github.dunwu.quickstart.fs.config.FileSystemProperties;
import io.github.dunwu.quickstart.fs.constant.FileSystemConstant;
import io.github.dunwu.quickstart.fs.dto.FileContentDTO;
import io.github.dunwu.quickstart.fs.dto.FileDTO;
import io.github.dunwu.quickstart.fs.dto.UploadFileDTO;
import io.github.dunwu.quickstart.fs.service.FileStorageService;
import io.github.dunwu.util.mapper.BeanMapper;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 临时文件存储服务
 * <p>
 * 文件将被存储在服务所在本机的临时磁盘空间，路径为
 * {@link io.github.dunwu.quickstart.fs.config.FileSystemProperties.Temp#location}
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-26
 */
@AllArgsConstructor
@Service(value = FileSystemConstant.TEMP_FILE_CONTENT_SERVICE)
public class TempFileStorageServiceImpl extends BaseFileStorageService
		implements FileStorageService {

	private final FileSystemProperties fileSystemProperties;

	@PostConstruct
	public void init() {
		Path path = Paths.get(fileSystemProperties.getTemp().getLocation());
		try {
			Files.createDirectories(path);
		}
		catch (IOException e) {
			log.error("创建临时文件存储目录 {} 失败", fileSystemProperties.getTemp().getLocation());
		}
	}

	@Override
	public FileDTO create(UploadFileDTO uploadFileDTO) throws IOException {
		FileDTO fileDTO = this.getFileInfo(uploadFileDTO);
		FileContentDTO fileContentDTO = BeanMapper.map(fileDTO, FileContentDTO.class);
		Path path = Paths.get(fileSystemProperties.getTemp().getLocation());
		Files.write(path.resolve(fileContentDTO.getStoreUrl()),
				fileContentDTO.getContent(), StandardOpenOption.CREATE);
		return fileDTO;
	}

	@Override
	public boolean delete(FileContentDTO fileContentDTO) throws IOException {
		Path path = Paths.get(fileContentDTO.getStoreUrl());
		Files.delete(path);
		return true;
	}

	@Override
	public FileContentDTO getOne(FileContentDTO fileContentDTO) throws IOException {
		Path path = Paths.get(fileContentDTO.getStoreUrl());
		Resource resource = new UrlResource(path.toUri());
		if (resource.exists() || resource.isReadable()) {
			byte[] bytes = FileUtils.readFileToByteArray(resource.getFile());
			fileContentDTO.setContent(bytes);
			return fileContentDTO;
		}
		else {
			throw new IOException("Could not read file: " + fileContentDTO.getStoreUrl());
		}
	}

	@Override
	public FileDTO getFileInfo(UploadFileDTO uploadFileDTO) throws IOException {
		FileDTO fileDTO = super.getFileInfo(uploadFileDTO);
		String storeUrl = fileSystemProperties.getTemp().getLocation() + "/"
				+ fileDTO.getFileName();
		fileDTO.setStoreUrl(storeUrl);
		return fileDTO;
	}

}
