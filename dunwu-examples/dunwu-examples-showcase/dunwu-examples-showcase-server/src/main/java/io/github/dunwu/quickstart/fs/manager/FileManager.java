package io.github.dunwu.quickstart.fs.manager;

import io.github.dunwu.core.DataResult;
import io.github.dunwu.core.PageResult;
import io.github.dunwu.core.Pagination;
import io.github.dunwu.quickstart.fs.dto.FileDTO;
import io.github.dunwu.quickstart.fs.dto.FileQuery;
import io.github.dunwu.quickstart.fs.dto.UploadFileDTO;

import java.io.IOException;

/**
 * 文件管理接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-25
 */
public interface FileManager {

	/**
	 * 添加一个文件，并记录其文件信息
	 * @param uploadFileDTO 上传文件信息 DTO
	 * @return 成功则 Result 中 data 存储 FileDTO(ID 为数据库中 ID 值)；反之 data 为 null
	 * @throws IOException IO 异常
	 */
	DataResult<FileDTO> create(UploadFileDTO uploadFileDTO) throws IOException;

	/**
	 * 删除一个文件，并删除其文件信息
	 * @param fileQuery 不为 null 的字段将视为过滤条件
	 * @return 成功则 Result 中 data 为 true；反之 data 为 false
	 * @throws IOException IO 异常
	 */
	DataResult<Boolean> delete(FileQuery fileQuery) throws IOException;

	/**
	 * 查询一个文件的文件信息以及其内容
	 * @param fileQuery 不为 null 的字段将视为过滤条件
	 * @return 成功则 Result 中 data 存储 FileDTO；反之 data 为 null
	 * @throws IOException IO 异常
	 */
	DataResult<FileDTO> getOne(FileQuery fileQuery) throws IOException;

	/**
	 * 按照过滤条件查询文件的文件信息
	 * @param fileQuery 不为 null 的字段将视为过滤条件
	 * @param page 分页查询条件
	 * @return 成功则 Result 中 data 存储 FileDTO 分页列表；反之 data 为 null
	 */
	PageResult<FileDTO> page(FileQuery fileQuery, Pagination page);

	/**
	 * 判断当前 IP 是否允许访问文件服务
	 * @param ip IP地址
	 * @return 成功则 Result 中 data 为 true；反之 data 为 false
	 */
	DataResult<Boolean> allowAccess(String ip);

}
