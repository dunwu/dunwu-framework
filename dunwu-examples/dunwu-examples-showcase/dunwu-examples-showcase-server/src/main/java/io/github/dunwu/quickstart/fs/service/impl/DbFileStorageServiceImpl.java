package io.github.dunwu.quickstart.fs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.github.dunwu.quickstart.fs.constant.FileSystemConstant;
import io.github.dunwu.quickstart.fs.dto.FileContentDTO;
import io.github.dunwu.quickstart.fs.dto.FileDTO;
import io.github.dunwu.quickstart.fs.dto.UploadFileDTO;
import io.github.dunwu.quickstart.fs.entity.FileContent;
import io.github.dunwu.quickstart.fs.mapper.dao.FileContentDao;
import io.github.dunwu.quickstart.fs.service.FileStorageService;
import io.github.dunwu.util.mapper.BeanMapper;
import lombok.AllArgsConstructor;
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
public class DbFileStorageServiceImpl extends BaseFileStorageService implements FileStorageService {

    protected final FileContentDao fileContentDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public FileDTO create(UploadFileDTO uploadFileDTO) throws IOException {
        FileDTO fileDTO = super.getFileInfo(uploadFileDTO);
        FileContentDTO fileContentDTO = BeanMapper.map(fileDTO, FileContentDTO.class);
        FileContent fileContent = BeanMapper.map(fileContentDTO, FileContent.class);
        if (fileContentDao.save(fileContent)) {
            return fileDTO;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(FileContentDTO fileContentDTO) {
        FileContent fileContent = BeanMapper.map(fileContentDTO, FileContent.class);
        return fileContentDao.remove(new QueryWrapper<>(fileContent));
    }

    @Override
    public FileContentDTO getOne(FileContentDTO fileContentDTO) {
        FileContent query = BeanMapper.map(fileContentDTO, FileContent.class);
        FileContent result = fileContentDao.getOne(new QueryWrapper<>(query));
        return BeanMapper.map(result, FileContentDTO.class);
    }
}
