package io.github.dunwu.quickstart.service.impl;

import io.github.dunwu.quickstart.entity.User;
import io.github.dunwu.quickstart.dao.UserDao;
import io.github.dunwu.quickstart.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
