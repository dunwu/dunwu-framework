package io.github.dunwu.quickstart.filesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.quickstart.filesystem.constant.FileSystemConstant;
import io.github.dunwu.quickstart.filesystem.dto.FileDTO;
import io.github.dunwu.quickstart.filesystem.dto.UploadFileDTO;
import io.github.dunwu.quickstart.filesystem.entity.FileContent;
import io.github.dunwu.quickstart.filesystem.mapper.FileContentMapper;
import io.github.dunwu.quickstart.filesystem.service.FileStorageService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

/**
 * 数据库文件存储服务
 * <p>
 * 文件将被存储到数据库表字段（file_content 表的 content 字段，BLOB 类型）中。
 * <p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-24
 */
@AllArgsConstructor
@Service(value = FileSystemConstant.DB_FILE_CONTENT_SERVICE)
public class DbFileStorageServiceImpl implements FileStorageService {

	protected final FileContentMapper fileContentMapper;

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	@Transactional(rollbackFor = Exception.class)
	public String create(UploadFileDTO uploadFileDTO) throws IOException {
		FileContent fileContent = new FileContent();
		fileContent.setFileName(uploadFileDTO.getFileName());
		fileContent.setContent(uploadFileDTO.getFile().getBytes());
		fileContentMapper.insert(fileContent);
		return fileContent.getId().toString();
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public boolean delete(FileDTO fileDTO) {
		FileContent fileContent = new FileContent();
		if (StringUtils.isNotBlank(fileDTO.getFileName())) {
			fileContent.setFileName(fileDTO.getFileName());
		}
		return fileContentMapper.delete(new QueryWrapper<>(fileContent)) > 0;
	}

	@Override
	public FileDTO getContent(FileDTO fileDTO) {
		FileContent query = new FileContent();
		if (StringUtils.isNotBlank(fileDTO.getFileName())) {
			query.setFileName(fileDTO.getFileName());
		}
		FileContent result = fileContentMapper.selectOne(new QueryWrapper<>(query));
		if (result != null) {
			fileDTO.setContent(result.getContent());
		} else {
			log.error("查找文件失败");
		}
		return fileDTO;
	}

}
