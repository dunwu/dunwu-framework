package io.github.dunwu.scheduler.dao.impl;

import io.github.dunwu.data.dao.BaseDao;
import io.github.dunwu.metadata.Dao;
import io.github.dunwu.scheduler.dao.SchedulerInfoDao;
import io.github.dunwu.scheduler.dao.mapper.SchedulerInfoMapper;
import io.github.dunwu.scheduler.entity.SchedulerInfo;

/**
 * <p>
 * 调度信息表 DAO 实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-01
 */
@Dao

public class SchedulerInfoDaoImpl extends BaseDao<SchedulerInfoMapper, SchedulerInfo> implements SchedulerInfoDao {

}
