package io.github.dunwu.filesystem.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import io.github.dunwu.filesystem.config.FileSystemProperties;
import io.github.dunwu.filesystem.constant.FileSystemConstant;
import io.github.dunwu.filesystem.dto.FileDTO;
import io.github.dunwu.filesystem.dto.UploadFileDTO;
import io.github.dunwu.filesystem.service.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * FastDFS 文件存储服务
 * <p>
 * 文件将被存储在 FastDFS 中的 {@link FileSystemProperties.Fdfs#group}
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://github.com/happyfish100/fastdfs/wiki">fastdfs wiki</a>
 * @see <a href="https://github.com/tobato/FastDFS_Client">FastDFS_Client</a>
 * @since 2019-07-26
 */
@AllArgsConstructor
@Service(value = FileSystemConstant.FDFS_FILE_CONTENT_SERVICE)
public class FdfsFileStorageServiceImpl implements FileStorageService {

    private final FastFileStorageClient storageClient;

    private final FileSystemProperties fileSystemProperties;

    @Override
    public String create(UploadFileDTO uploadFileDTO) throws IOException {
        StorePath storePath = storageClient.uploadFile(
            fileSystemProperties.getFdfs().getGroup(),
            uploadFileDTO.getFile().getInputStream(), uploadFileDTO.getSize(),
            uploadFileDTO.getExtension());
        return storePath.getFullPath();
    }

    @Override
    public boolean delete(FileDTO fileDTO) throws IOException {
        storageClient.deleteFile(fileDTO.getStoreUrl());
        return true;
    }

    @Override
    public FileDTO getContent(FileDTO fileDTO) throws IOException {
        StorePath path = StorePath.parseFromUrl(fileDTO.getStoreUrl());
        byte[] bytes = storageClient.downloadFile(
            fileSystemProperties.getFdfs().getGroup(), path.getPath(),
            new DownloadByteArray());
        fileDTO.setContent(bytes);
        return fileDTO;
    }

}
