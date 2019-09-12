package io.github.dunwu.quickstart.user.service.impl;

import io.github.dunwu.data.service.ServiceImpl;
import io.github.dunwu.quickstart.user.entity.Login;
import io.github.dunwu.quickstart.user.mapper.LoginMapper;
import io.github.dunwu.quickstart.user.service.LoginService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录信息表 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login>
		implements LoginService {

}
