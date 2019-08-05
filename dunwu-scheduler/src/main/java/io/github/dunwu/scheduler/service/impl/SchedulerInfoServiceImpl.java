package io.github.dunwu.scheduler.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import io.github.dunwu.core.PageResult;
import io.github.dunwu.scheduler.dao.SchedulerInfoDao;
import io.github.dunwu.scheduler.dto.SchedulerInfoDTO;
import io.github.dunwu.scheduler.dto.SchedulerInfoQuery;
import io.github.dunwu.scheduler.entity.SchedulerInfo;
import io.github.dunwu.scheduler.service.SchedulerInfoService;
import io.github.dunwu.util.mapper.BeanMapper;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-31
 */
@Service
@AllArgsConstructor
public class SchedulerInfoServiceImpl implements SchedulerInfoService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final SchedulerInfoDao schedulerInfoDao;

    @Override
    public boolean save(SchedulerInfoDTO schedulerInfoDTO) {
        SchedulerInfo schedulerInfo = BeanMapper.map(schedulerInfoDTO, SchedulerInfo.class);
        if (schedulerInfoDao.save(schedulerInfo)) {
            schedulerInfoDTO.setId(schedulerInfo.getId());
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(SchedulerInfoDTO schedulerInfoDTO) {
        SchedulerInfo schedulerInfo = BeanMapper.map(schedulerInfoDTO, SchedulerInfo.class);
        return schedulerInfoDao.remove(new QueryWrapper<>(schedulerInfo));
    }

    @Override
    public boolean updateById(SchedulerInfoDTO schedulerInfoDTO) {
        SchedulerInfo schedulerInfo = BeanMapper.map(schedulerInfoDTO, SchedulerInfo.class);
        if (schedulerInfoDao.updateById(schedulerInfo)) {
            return true;
        }
        return false;
    }

    @Override
    public SchedulerInfoDTO getById(Serializable id) {
        SchedulerInfo schedulerInfo = schedulerInfoDao.getById(id);
        return BeanMapper.map(schedulerInfo, SchedulerInfoDTO.class);
    }

    @Override
    public List<SchedulerInfoDTO> list() {
        List<SchedulerInfo> list = schedulerInfoDao.list();
        return BeanMapper.mapList(list, SchedulerInfoDTO.class);
    }

    @Override
    public List<SchedulerInfoDTO> list(SchedulerInfoQuery schedulerInfoQuery) {
        SchedulerInfo schedulerInfo = BeanMapper.map(schedulerInfoQuery, SchedulerInfo.class);
        List<SchedulerInfo> list = schedulerInfoDao.list(new QueryWrapper<>(schedulerInfo));
        return BeanMapper.mapList(list, SchedulerInfoDTO.class);
    }

    @Override
    public IPage<SchedulerInfoDTO> page(SchedulerInfoQuery schedulerInfoQuery, PageResult.Page page) {
        IPage<SchedulerInfo> pageQuery = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(
            page.getCurrent(), page.getSize());
        SchedulerInfo schedulerInfo = BeanMapper.map(schedulerInfoQuery, SchedulerInfo.class);
        IPage<SchedulerInfo> fileInfoPage = schedulerInfoDao.page(pageQuery,
                                                                  new QueryWrapper<>(schedulerInfo).orderByDesc(
                                                                      "update_time"));
        List<SchedulerInfoDTO> schedulerInfoDTOS = BeanMapper.mapList(fileInfoPage.getRecords(),
                                                                      SchedulerInfoDTO.class);
        IPage<SchedulerInfoDTO> result = new com.baomidou.mybatisplus.extension.plugins.pagination.Page<>(
            fileInfoPage.getCurrent(), fileInfoPage.getSize(), fileInfoPage.getTotal());
        result.setPages(fileInfoPage.getPages());
        result.setRecords(schedulerInfoDTOS);
        return result;
    }
}
