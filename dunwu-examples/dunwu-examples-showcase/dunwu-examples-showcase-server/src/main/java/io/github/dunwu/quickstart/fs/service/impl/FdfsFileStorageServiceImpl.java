package io.github.dunwu.quickstart.fs.service.impl;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import io.github.dunwu.quickstart.fs.config.FileSystemProperties;
import io.github.dunwu.quickstart.fs.constant.FileSystemConstant;
import io.github.dunwu.quickstart.fs.dto.FileContentDTO;
import io.github.dunwu.quickstart.fs.dto.FileDTO;
import io.github.dunwu.quickstart.fs.dto.UploadFileDTO;
import io.github.dunwu.quickstart.fs.service.FileStorageService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * FastDFS 文件存储服务
 * <p>
 * 文件将被存储在 FastDFS 中的 {@link io.github.dunwu.quickstart.fs.config.FileSystemProperties.Fdfs#group}
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://github.com/happyfish100/fastdfs/wiki">fastdfs wiki</a>
 * @see <a href="https://github.com/tobato/FastDFS_Client">FastDFS_Client</a>
 * @since 2019-07-26
 */
@AllArgsConstructor
@Service(value = FileSystemConstant.FDFS_FILE_CONTENT_SERVICE)
public class FdfsFileStorageServiceImpl extends BaseFileStorageService implements FileStorageService {

    private final FastFileStorageClient storageClient;

    private final FileSystemProperties fileSystemProperties;

    @Override
    public FileDTO create(UploadFileDTO uploadFileDTO) throws IOException {
        FileDTO fileDTO = super.getFileInfo(uploadFileDTO);
        StorePath path = storageClient.uploadFile(fileSystemProperties.getFdfs()
                                                                      .getGroup(), uploadFileDTO.getFile()
                                                                                                .getInputStream(),
                                                  fileDTO.getSize(), fileDTO.getExtension());
        fileDTO.setStoreUrl(path.getFullPath());
        return fileDTO;
    }

    @Override
    public boolean delete(FileContentDTO fileContentDTO) throws IOException {
        String storeUrl = fileContentDTO.getStoreUrl();
        storageClient.deleteFile(storeUrl);
        return true;
    }

    @Override
    public FileContentDTO getOne(FileContentDTO fileContentDTO) throws IOException {
        StorePath path = StorePath.parseFromUrl(fileContentDTO.getStoreUrl());
        byte[] bytes = storageClient.downloadFile(fileSystemProperties.getFdfs()
                                                                      .getGroup(), path.getPath(),
                                                  new DownloadByteArray());
        fileContentDTO.setContent(bytes);
        return fileContentDTO;
    }
}
