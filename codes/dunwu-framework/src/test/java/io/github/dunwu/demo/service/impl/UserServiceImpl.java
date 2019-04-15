package io.github.dunwu.demo.service.impl;

import io.github.dunwu.demo.entity.User;
import io.github.dunwu.demo.dao.UserDao;
import io.github.dunwu.demo.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zhang Peng
 * @since 2019-04-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, User> implements UserService {

}
