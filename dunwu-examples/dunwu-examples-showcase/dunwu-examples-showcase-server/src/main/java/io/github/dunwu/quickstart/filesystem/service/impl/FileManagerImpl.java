package io.github.dunwu.quickstart.filesystem.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.dunwu.core.*;
import io.github.dunwu.data.util.PageUtil;
import io.github.dunwu.quickstart.filesystem.config.FileSystemProperties;
import io.github.dunwu.quickstart.filesystem.constant.FileStoreTypeEnum;
import io.github.dunwu.quickstart.filesystem.dto.AccessDTO;
import io.github.dunwu.quickstart.filesystem.dto.FileDTO;
import io.github.dunwu.quickstart.filesystem.dto.FileQuery;
import io.github.dunwu.quickstart.filesystem.dto.UploadFileDTO;
import io.github.dunwu.quickstart.filesystem.entity.FileInfo;
import io.github.dunwu.quickstart.filesystem.mapper.dao.FileInfoDao;
import io.github.dunwu.quickstart.filesystem.service.FileManager;
import io.github.dunwu.quickstart.filesystem.service.FileStorageService;
import io.github.dunwu.util.collection.MapUtil;
import io.github.dunwu.util.mapper.BeanMapper;
import io.github.dunwu.web.util.SpringUtil;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ConcurrentReferenceHashMap;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-23
 */
@Service
@AllArgsConstructor
public class FileManagerImpl implements FileManager {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	private final FileInfoDao fileInfoDao;

	private final FileSystemProperties fileSystemProperties;

	/**
	 * 上传请求统计 Map
	 */
	private final Map<String, AccessDTO> map = new ConcurrentReferenceHashMap<>(1000);

	@Override
	public DataResult<FileDTO> create(UploadFileDTO uploadFileDTO) throws IOException {
		FileStoreTypeEnum storeType = uploadFileDTO.getStoreType();
		FileStorageService fileStorageService = SpringUtil.getBean(storeType.getValue(),
				FileStorageService.class);
		autoFillUploadFileDto(uploadFileDTO);
		FileDTO fileDTO = convert(uploadFileDTO);
		String storeUrl = fileStorageService.create(uploadFileDTO);
		if (StringUtils.isBlank(storeUrl)) {
			return ResultUtil.failDataResult(AppCode.ERROR_IO);
		}
		FileInfo fileInfo = BeanMapper.map(fileDTO, FileInfo.class);
		fileInfo.setStoreUrl(storeUrl);
		fileInfoDao.save(fileInfo);
		return ResultUtil.successDataResult(fileDTO);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public DataResult<Boolean> delete(FileQuery fileQuery) throws IOException {
		if (fileQuery == null) {
			return ResultUtil.failDataResult(AppCode.ERROR_PARAMETER);
		}

		FileInfo query = BeanMapper.map(fileQuery, FileInfo.class);
		FileInfo fileInfo = fileInfoDao.getOne(query);
		FileDTO fileDTO = BeanMapper.map(fileInfo, FileDTO.class);
		FileStoreTypeEnum storeType = fileInfo.getStoreType();
		FileStorageService fileService = SpringUtil.getBean(storeType.getValue(),
				FileStorageService.class);
		if (fileService.delete(fileDTO)) {
			return ResultUtil.successDataResult(fileInfoDao.removeById(fileInfo.getId()));
		}

		return ResultUtil.failDataResult(AppCode.ERROR_DB);
	}

	@Override
	public DataResult<FileDTO> getOne(FileQuery fileQuery) throws IOException {
		if (fileQuery == null) {
			return ResultUtil.failDataResult(AppCode.ERROR_PARAMETER);
		}

		FileInfo fileInfoQuery = BeanMapper.map(fileQuery, FileInfo.class);
		FileInfo fileInfo = fileInfoDao.getOne(fileInfoQuery);
		if (fileInfo == null) {
			return ResultUtil.successDataResult(null);
		}

		FileStoreTypeEnum storeType = fileInfo.getStoreType();
		FileStorageService fileService = SpringUtil.getBean(storeType.getValue(),
				FileStorageService.class);
		FileDTO query = BeanMapper.map(fileInfo, FileDTO.class);
		FileDTO fileDTO = fileService.getContent(query);
		return ResultUtil.successDataResult(fileDTO);
	}

	@Override
	public PageResult<FileDTO> page(FileQuery fileQuery, Pagination pagination) {
		FileInfo fileInfoQuery = BeanMapper.map(fileQuery, FileInfo.class);
		IPage page = PageUtil.transToMybatisPlusPage(pagination);
		IPage<FileDTO> result = fileInfoDao.page(page, fileInfoQuery);
		pagination.setTotal(result.getTotal());
		pagination.setPages(result.getPages());
		pagination.setList(result.getRecords());
		return ResultUtil.successPageResult(pagination);
	}

	/**
	 * 判断请求方是否频繁发起上传请求，如果是，则拒绝请求
	 * @param ip 请求IP
	 * @return boolean
	 */
	@Override
	public DataResult<Boolean> allowAccess(String ip) {
		AccessDTO accessDTO;
		if (map.containsKey(ip)) {
			accessDTO = map.get(ip);

			Date now = new Date();
			Date first = accessDTO.getDate();

			// 首次请求时间已经超出时间间隔，刷新时间和次数
			FileSystemProperties.UploadTimeLimit uploadTimeLimit = fileSystemProperties
					.getLimit();
			if (now.getTime() - first.getTime() > uploadTimeLimit.getStatTimeRange()) {
				accessDTO.setCount(1);
				accessDTO.setDate(new Date());
			}
			else if (now.getTime() - first.getTime() < uploadTimeLimit.getStatTimeRange()
					&& accessDTO.getCount() + 1 > uploadTimeLimit.getStatTimeRange()) {
				// 首次请求时间在时间间隔内，且访问次数超过限制，拒绝访问
				return ResultUtil.successDataResult(true);
			}
			else {
				accessDTO.setCount(accessDTO.getCount() + 1);
			}
		}
		else {
			accessDTO = new AccessDTO(ip, new Date(), 1);
		}

		synchronized (map) {
			map.put(ip, accessDTO);
		}
		return ResultUtil.successDataResult(false);
	}

	/**
	 * 每 5 分钟定时清除 map，避免占用内存过大
	 */
	@Scheduled(cron = "0 0/5 * * * ?")
	public void cleanThreadLocal() {
		log.debug("[定时器启动] 清除上传请求统计 Map ，开始执行清除动作");
		if (MapUtil.isNotEmpty(map)) {
			map.clear();
		}
	}

}
