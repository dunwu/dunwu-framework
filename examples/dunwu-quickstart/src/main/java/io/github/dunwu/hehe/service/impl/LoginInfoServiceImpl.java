package io.github.dunwu.hehe.service.impl;

import io.github.dunwu.hehe.entity.LoginInfo;
import io.github.dunwu.hehe.dao.LoginInfoDao;
import io.github.dunwu.hehe.service.LoginInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 登录信息表 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-22
 */
@Service
public class LoginInfoServiceImpl extends ServiceImpl<LoginInfoDao, LoginInfo> implements LoginInfoService {

}
