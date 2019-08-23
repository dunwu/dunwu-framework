package io.github.dunwu.quickstart.user.mapper.dao.impl;

import io.github.dunwu.data.dao.BaseDao;
import io.github.dunwu.quickstart.user.entity.UserInfo;
import io.github.dunwu.quickstart.user.mapper.UserInfoMapper;
import io.github.dunwu.quickstart.user.mapper.dao.UserInfoDao;
import io.github.dunwu.annotation.Dao;

/**
 * <p>
 * 用户信息表 DAO 实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-07
 */
@Dao
public class UserInfoDaoImpl extends BaseDao<UserInfoMapper, UserInfo> implements UserInfoDao {

}
