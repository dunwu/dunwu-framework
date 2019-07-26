package io.github.dunwu.quickstart.dao.impl;

import io.github.dunwu.meta.Dao;
import io.github.dunwu.quickstart.entity.UserInfo;
import io.github.dunwu.quickstart.mapper.UserInfoMapper;
import io.github.dunwu.quickstart.dao.UserInfoDao;
import io.github.dunwu.data.dao.BaseDao;
import org.springframework.stereotype.Component;

/**
 * <p>
 * 用户信息表 DAO 实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-24
 */
@Dao
public class UserInfoDaoImpl extends BaseDao<UserInfoMapper, UserInfo> implements UserInfoDao {

}
