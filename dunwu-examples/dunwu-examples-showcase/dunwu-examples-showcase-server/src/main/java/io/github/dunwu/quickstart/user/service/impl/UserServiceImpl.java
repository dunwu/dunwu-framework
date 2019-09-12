package io.github.dunwu.quickstart.user.service.impl;

import io.github.dunwu.quickstart.user.entity.User;
import io.github.dunwu.quickstart.user.mapper.UserMapper;
import io.github.dunwu.quickstart.user.service.UserService;
import io.github.dunwu.data.service.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
		implements UserService {

}
