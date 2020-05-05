package io.github.dunwu.filesystem.service;

import io.github.dunwu.filesystem.constant.FileSystemConstant;
import io.github.dunwu.filesystem.dto.FileDTO;
import io.github.dunwu.filesystem.dto.FileQuery;
import io.github.dunwu.filesystem.dto.UploadFileDTO;
import io.github.dunwu.tool.bean.BeanUtil;
import cn.hutool.core.io.FileTypeUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.data.core.BaseResult;
import io.github.dunwu.data.core.DataResult;
import io.github.dunwu.data.core.PageResult;
import io.github.dunwu.tool.io.FileUtil;
import org.springframework.web.multipart.MultipartFile;

import java.awt.print.Pageable;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * 文件管理接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-25
 */
public interface FileManager {

    /**
     * 判断当前 IP 是否允许访问文件服务
     *
     * @param ip IP地址
     * @return 成功则 Result 中 data 为 true；反之 data 为 false
     */
    DataResult<Boolean> allowAccess(String ip);

    default void autoFillUploadFileDto(UploadFileDTO uploadFileDTO) {
        MultipartFile file = uploadFileDTO.getFile();
        String extension = FileTypeUtil.getTypeByPath(file.getOriginalFilename());
        uploadFileDTO.setExtension(extension);

        if (StrUtil.isBlank(uploadFileDTO.getOriginName())) {
            String originName = FileUtil.getName(uploadFileDTO.getOriginName())
                + FileSystemConstant.FILE_SEPARATOR + extension;
            uploadFileDTO.setOriginName(originName);
        }

        if (StrUtil.isBlank(uploadFileDTO.getFileName())) {
            String fileName = IdUtil.fastUUID() + FileSystemConstant.FILE_SEPARATOR
                + extension.toLowerCase();
            uploadFileDTO.setFileName(fileName);
        }

        uploadFileDTO.setSize(file.getSize());
        uploadFileDTO.setContentType(file.getContentType());
    }

    /**
     * 获取文件信息
     *
     * @param uploadFileDTO 上传文件信息
     * @return FileDTO 文件信息 DTO
     * @throws IOException IO 异常
     */
    default FileDTO convert(UploadFileDTO uploadFileDTO) throws IOException {
        FileDTO fileDTO = BeanUtil.toBean(uploadFileDTO, FileDTO.class);
        StringBuilder sb = new StringBuilder();
        sb.append(uploadFileDTO.getNamespace()).append("/").append(uploadFileDTO.getTag())
            .append("/").append(fileDTO.getOriginName());
        fileDTO.setAccessUrl(sb.toString());
        fileDTO.setUpdateTime(LocalDateTime.now());
        return fileDTO;
    }

    /**
     * 添加一个文件，并记录其文件信息
     *
     * @param uploadFileDTO 上传文件信息 DTO
     * @return 成功则 Result 中 data 存储 FileDTO(ID 为数据库中 ID 值)；反之 data 为 null
     * @throws IOException IO 异常
     */
    DataResult<FileDTO> create(UploadFileDTO uploadFileDTO) throws IOException;

    /**
     * 删除一个文件，并删除其文件信息
     *
     * @param fileQuery 不为 null 的字段将视为过滤条件
     * @return 成功则 Result 中 data 为 true；反之 data 为 false
     * @throws IOException IO 异常
     */
    BaseResult delete(FileQuery fileQuery) throws IOException;

    /**
     * 查询一个文件的文件信息以及其内容
     *
     * @param fileQuery 不为 null 的字段将视为过滤条件
     * @return 成功则 Result 中 data 存储 FileDTO；反之 data 为 null
     * @throws IOException IO 异常
     */
    DataResult<FileDTO> getOne(FileQuery fileQuery) throws IOException;

    /**
     * 按照过滤条件查询文件的文件信息
     *
     * @param fileQuery 不为 null 的字段将视为过滤条件
     * @param pageable  分页查询条件
     * @return 成功则 Result 中 data 存储 FileDTO 分页列表；反之 data 为 null
     */
    PageResult<FileDTO> page(FileQuery fileQuery, Pageable pageable);

}
