package io.github.dunwu.quickstart.filesystem.service.impl;

import io.github.dunwu.quickstart.filesystem.config.FileSystemProperties;
import io.github.dunwu.quickstart.filesystem.constant.FileSystemConstant;
import io.github.dunwu.quickstart.filesystem.dto.FileDTO;
import io.github.dunwu.quickstart.filesystem.dto.UploadFileDTO;
import io.github.dunwu.quickstart.filesystem.service.FileStorageService;
import lombok.AllArgsConstructor;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * {@link io.github.dunwu.quickstart.filesystem.config.FileSystemProperties.Temp#location}
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-26
 */
@AllArgsConstructor
@Service(value = FileSystemConstant.TEMP_FILE_CONTENT_SERVICE)
public class TempFileStorageServiceImpl implements FileStorageService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

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
	public String create(UploadFileDTO uploadFileDTO) throws IOException {
		String storeUrl = fileSystemProperties.getTemp().getLocation() + "/"
				+ uploadFileDTO.getOriginName();
		Path path = Paths.get(fileSystemProperties.getTemp().getLocation());
		Files.write(path.resolve(storeUrl), uploadFileDTO.getFile().getBytes(),
				StandardOpenOption.CREATE);
		return storeUrl;
	}

	@Override
	public boolean delete(FileDTO fileDTO) throws IOException {
		Path path = Paths.get(fileDTO.getStoreUrl());
		Files.delete(path);
		return true;
	}

	@Override
	public FileDTO getContent(FileDTO fileDTO) throws IOException {
		Path path = Paths.get(fileDTO.getStoreUrl());
		Resource resource = new UrlResource(path.toUri());
		if (resource.exists() || resource.isReadable()) {
			byte[] bytes = FileUtils.readFileToByteArray(resource.getFile());
			fileDTO.setContent(bytes);
			return fileDTO;
		}
		else {
			throw new IOException("Could not read file: " + fileDTO.getStoreUrl());
		}
	}

}
