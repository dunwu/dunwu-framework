package io.github.dunwu.quickstart.fs.service;

import io.github.dunwu.quickstart.fs.dto.FileContentDTO;
import io.github.dunwu.quickstart.fs.dto.FileDTO;
import io.github.dunwu.quickstart.fs.dto.UploadFileDTO;
import io.github.dunwu.quickstart.fs.service.impl.BaseFileStorageService;
import io.github.dunwu.quickstart.fs.service.impl.DbFileStorageServiceImpl;
import io.github.dunwu.quickstart.fs.service.impl.FdfsFileStorageServiceImpl;
import io.github.dunwu.quickstart.fs.service.impl.TempFileStorageServiceImpl;

import java.io.IOException;

/**
 * 文件存储服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see BaseFileStorageService
 * @see DbFileStorageServiceImpl
 * @see TempFileStorageServiceImpl
 * @see FdfsFileStorageServiceImpl
 * @since 2019-07-24
 */
public interface FileStorageService {

    /**
     * 添加文件到存储介质
     *
     * @param uploadFileDTO 上传文件信息
     * @return 成功返回文件信息 DTO；失败返回 null
     * @throws IOException IO 异常
     */
    FileDTO create(UploadFileDTO uploadFileDTO) throws IOException;

    /**
     * 从存储介质删除文件
     *
     * @param fileContentDTO 上传文件内容
     * @return 成功返回 true；失败返回 false
     * @throws IOException IO 异常
     */
    boolean delete(FileContentDTO fileContentDTO) throws IOException;

    /**
     * 从存储介质删除文件
     *
     * @param fileContentDTO 查询条件
     * @return 成功返回文件内容 DTO；失败返回 null(含未找到)
     * @throws IOException IO 异常
     */
    FileContentDTO getOne(FileContentDTO fileContentDTO) throws IOException;

    /**
     * 获取文件信息
     *
     * @param uploadFileDTO 上传文件信息
     * @return FileDTO 文件信息 DTO
     * @throws IOException IO 异常
     */
    FileDTO getFileInfo(UploadFileDTO uploadFileDTO) throws IOException;
}
