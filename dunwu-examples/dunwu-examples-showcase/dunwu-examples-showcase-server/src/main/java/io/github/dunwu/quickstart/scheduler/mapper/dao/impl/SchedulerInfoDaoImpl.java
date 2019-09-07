package io.github.dunwu.quickstart.scheduler.mapper.dao.impl;

import io.github.dunwu.data.dao.BaseDao;
import io.github.dunwu.quickstart.scheduler.entity.SchedulerInfo;
import io.github.dunwu.quickstart.scheduler.mapper.SchedulerInfoMapper;
import io.github.dunwu.quickstart.scheduler.mapper.dao.SchedulerInfoDao;
import io.github.dunwu.annotation.Dao;

/**
 * <p>
 * 调度信息表 DAO 实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-07
 */
@Dao
public class SchedulerInfoDaoImpl extends BaseDao<SchedulerInfoMapper, SchedulerInfo>
		implements SchedulerInfoDao {

}
