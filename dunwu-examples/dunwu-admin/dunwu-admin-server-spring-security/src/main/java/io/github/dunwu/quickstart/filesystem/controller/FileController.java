package io.github.dunwu.quickstart.filesystem.controller;

import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.DataResult;
import io.github.dunwu.common.PageResult;
import io.github.dunwu.common.Pagination;
import io.github.dunwu.common.constant.AppResulstStatus;
import io.github.dunwu.quickstart.filesystem.dto.FileDTO;
import io.github.dunwu.quickstart.filesystem.dto.FileQuery;
import io.github.dunwu.quickstart.filesystem.dto.UploadFileDTO;
import io.github.dunwu.quickstart.filesystem.service.FileManager;
import io.github.dunwu.web.util.ServletUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 文件上传下载服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-23
 */
@RestController
@RequestMapping("file")
@Api(tags = "file")
public class FileController {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final FileManager fileManager;

    public FileController(FileManager fileManager) {
        this.fileManager = fileManager;
    }

    @PostMapping("delete")
    @ApiOperation(value = "删除文件")
    public BaseResult delete(@RequestBody FileQuery fileQuery) throws IOException {
        return fileManager.delete(fileQuery);
    }

    @GetMapping("/download/{namespace}/{tag}/{originName:.+}")
    @ApiOperation(value = "下载文件")
    public void downloadByName(HttpServletRequest request, HttpServletResponse response,
        @PathVariable("namespace") String namespace, @PathVariable("tag") String tag,
        @PathVariable("originName") String originName) throws IOException {
        FileQuery fileQuery = new FileQuery();
        fileQuery.setNamespace(namespace);
        fileQuery.setTag(tag);
        fileQuery.setOriginName(originName);
        DataResult<FileDTO> dataResult = fileManager.getOne(fileQuery);
        if (dataResult == null || dataResult.isNotOk()) {
            response.setStatus(404);
            return;
        }

        FileDTO fileDTO = dataResult.getData();
        ServletUtil.setFileDownloadHeader(request, response, fileDTO.getOriginName(),
            fileDTO.getContent());
        IOUtils.write(fileDTO.getContent(), response.getOutputStream());
    }

    @GetMapping("image/{namespace}/{tag}/{originName:.+}")
    @ApiOperation(value = "查看图片文件")
    public void imageByName(HttpServletResponse response,
        @PathVariable("namespace") String namespace, @PathVariable("tag") String tag,
        @PathVariable("originName") String originName) throws IOException {

        FileQuery fileQuery = new FileQuery();
        fileQuery.setNamespace(namespace);
        fileQuery.setTag(tag);
        fileQuery.setOriginName(originName);
        DataResult<FileDTO> dataResult = fileManager.getOne(fileQuery);
        if (dataResult == null || !dataResult.isOk()) {
            response.setStatus(404);
            return;
        }

        FileDTO fileDTO = dataResult.getData();
        ServletUtil.setFileShowHeader(response);
        IOUtils.write(fileDTO.getContent(), response.getOutputStream());
    }

    @GetMapping("/page")
    @ApiOperation(value = "分页查询")
    public PageResult<FileDTO> page(FileQuery fileQuery, Pagination<FileDTO> pagination) {
        return fileManager.page(fileQuery, pagination);
    }

    @PostMapping("upload")
    @ApiOperation(value = "批量上传文件")
    public DataResult<FileDTO> upload(MultipartHttpServletRequest request,
        UploadFileDTO uploadFileDTO) throws IOException {

        String ip = ServletUtil.getRealRemoteAddr(request);
        DataResult<Boolean> dataResult = fileManager.allowAccess(ip);
        if (dataResult.getData()) {
            return DataResult.failData(AppResulstStatus.ERROR_AUTHENTICATION.getCode(),
                "上传请求过于频繁，请稍后再尝试");
        }

        if (uploadFileDTO == null) {
            return DataResult.failData(AppResulstStatus.ERROR_PARAMETER);
        }

        return fileManager.create(uploadFileDTO);
    }

}
