package io.github.dunwu.quickstart.user.service.impl;

import io.github.dunwu.data.service.BaseService;
import io.github.dunwu.quickstart.user.entity.LoginInfo;
import io.github.dunwu.quickstart.user.service.LoginInfoService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录信息表 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-07
 */
@Service
@AllArgsConstructor
public class LoginInfoServiceImpl extends BaseService<LoginInfo> implements LoginInfoService {

}
