package io.github.dunwu.quickstart.filesystem.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.DataResult;
import io.github.dunwu.common.PageResult;
import io.github.dunwu.common.Pagination;
import io.github.dunwu.common.constant.AppResulstStatus;
import io.github.dunwu.data.mybatis.support.MybatisPlusUtil;
import io.github.dunwu.quickstart.filesystem.config.FileSystemProperties;
import io.github.dunwu.quickstart.filesystem.constant.FileStoreTypeEnum;
import io.github.dunwu.quickstart.filesystem.dto.AccessDTO;
import io.github.dunwu.quickstart.filesystem.dto.FileDTO;
import io.github.dunwu.quickstart.filesystem.dto.FileQuery;
import io.github.dunwu.quickstart.filesystem.dto.UploadFileDTO;
import io.github.dunwu.quickstart.filesystem.entity.File;
import io.github.dunwu.quickstart.filesystem.mapper.FileMapper;
import io.github.dunwu.quickstart.filesystem.service.FileManager;
import io.github.dunwu.quickstart.filesystem.service.FileStorageService;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.map.MapUtil;
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
import java.util.List;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-23
 */
@Service
@AllArgsConstructor
public class FileManagerImpl implements FileManager {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final FileMapper fileInfoMapper;

    private final FileSystemProperties fileSystemProperties;

    /**
     * 上传请求统计 Map
     */
    private final Map<String, AccessDTO> map = new ConcurrentReferenceHashMap<>(1000);

    /**
     * 判断请求方是否频繁发起上传请求，如果是，则拒绝请求
     *
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
            FileSystemProperties.UploadLimit uploadLimit = fileSystemProperties
                .getLimit();
            if (now.getTime() - first.getTime() > uploadLimit.getStatTimeRange()) {
                accessDTO.setCount(1);
                accessDTO.setDate(new Date());
            } else if (now.getTime() - first.getTime() < uploadLimit.getStatTimeRange()
                && accessDTO.getCount() + 1 > uploadLimit.getStatTimeRange()) {
                // 首次请求时间在时间间隔内，且访问次数超过限制，拒绝访问
                return DataResult.success(true);
            } else {
                accessDTO.setCount(accessDTO.getCount() + 1);
            }
        } else {
            accessDTO = new AccessDTO(ip, new Date(), 1);
        }

        synchronized (map) {
            map.put(ip, accessDTO);
        }
        return DataResult.success(false);
    }

    @Override
    public DataResult<FileDTO> create(UploadFileDTO uploadFileDTO) throws IOException {
        FileStoreTypeEnum storeType = uploadFileDTO.getStoreType();
        FileStorageService fileStorageService = SpringUtil.getBean(storeType.getValue(),
            FileStorageService.class);
        autoFillUploadFileDto(uploadFileDTO);
        FileDTO fileDTO = convert(uploadFileDTO);
        String storeUrl = fileStorageService.create(uploadFileDTO);
        if (StringUtils.isBlank(storeUrl)) {
            return DataResult.failData(AppResulstStatus.ERROR_IO);
        }
        File file = BeanUtil.toBean(fileDTO, File.class);
        file.setStoreUrl(storeUrl);
        fileInfoMapper.insert(file);
        return DataResult.success(fileDTO);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public BaseResult delete(FileQuery fileQuery) throws IOException {
        if (fileQuery == null) {
            return BaseResult.fail(AppResulstStatus.ERROR_PARAMETER);
        }

        File query = BeanUtil.toBean(fileQuery, File.class);
        File file = fileInfoMapper.selectOne(new QueryWrapper<>(query));
        FileDTO fileDTO = BeanUtil.toBean(file, FileDTO.class);
        FileStoreTypeEnum storeType = file.getStoreType();
        FileStorageService fileService = SpringUtil.getBean(storeType.getValue(),
            FileStorageService.class);
        if (fileService.delete(fileDTO)) {
            int num = fileInfoMapper.deleteById(file.getId());
            if (num > 0) {
                return BaseResult.success();
            }
        }

        return BaseResult.fail(AppResulstStatus.ERROR_DB);
    }

    @Override
    public DataResult<FileDTO> getOne(FileQuery fileQuery) throws IOException {
        if (fileQuery == null) {
            return DataResult.failData(AppResulstStatus.ERROR_PARAMETER);
        }

        File fileInfoQuery = BeanUtil.toBean(fileQuery, File.class);
        File file = fileInfoMapper.selectOne(new QueryWrapper<>(fileInfoQuery));
        if (file == null) {
            return DataResult.success(null);
        }

        FileStoreTypeEnum storeType = file.getStoreType();
        FileStorageService fileService = SpringUtil.getBean(storeType.getValue(),
            FileStorageService.class);
        FileDTO query = BeanUtil.toBean(file, FileDTO.class);
        FileDTO fileDTO = fileService.getContent(query);
        return DataResult.success(fileDTO);
    }

    @Override
    public PageResult<FileDTO> page(FileQuery fileQuery, Pagination<FileDTO> pagination) {
        File fileInfoQuery = BeanUtil.toBean(fileQuery, File.class);
        IPage<File> page = MybatisPlusUtil.transToMybatisPlusPage(pagination);
        IPage<File> result = fileInfoMapper.selectPage(page, new QueryWrapper<>(fileInfoQuery));
        List<FileDTO> list = BeanUtil.toBeanList(result.getRecords(), FileDTO.class);
        pagination.setTotal(result.getTotal());
        pagination.setList(list);
        return PageResult.success(pagination);
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
