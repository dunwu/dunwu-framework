package io.github.dunwu.system.service.impl;

import io.github.dunwu.system.entity.User;
import io.github.dunwu.system.dao.mapper.UserMapper;
import io.github.dunwu.system.service.UserService;
import io.github.dunwu.data.mybatis.BaseResultServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-07
 */
@Service
public class UserServiceImpl extends BaseResultServiceImpl<UserMapper, User> implements UserService {

}
