package io.github.dunwu.quickstart.user.mapper.dao.impl;

import io.github.dunwu.data.dao.BaseDao;
import io.github.dunwu.quickstart.user.entity.LoginInfo;
import io.github.dunwu.quickstart.user.mapper.LoginInfoMapper;
import io.github.dunwu.quickstart.user.mapper.dao.LoginInfoDao;
import io.github.dunwu.annotation.Dao;

/**
 * <p>
 * 登录信息表 DAO 实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-07
 */
@Dao
public class LoginInfoDaoImpl extends BaseDao<LoginInfoMapper, LoginInfo>
		implements LoginInfoDao {

}
