package io.github.dunwu.app.system.service.impl;

import io.github.dunwu.app.system.entity.User;
import io.github.dunwu.app.system.mapper.UserMapper;
import io.github.dunwu.app.system.service.UserDao;
import io.github.dunwu.data.core.annotation.Dao;
import io.github.dunwu.data.mybatis.BaseExtDaoImpl;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-07
 */
@Dao
public class UserDaoImpl extends BaseExtDaoImpl<UserMapper, User> implements UserDao {

}
