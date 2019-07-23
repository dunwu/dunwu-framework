package io.github.dunwu.hehe.service.impl;

import io.github.dunwu.hehe.entity.UserInfo;
import io.github.dunwu.hehe.dao.UserInfoDao;
import io.github.dunwu.hehe.service.UserInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户信息表 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-22
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoDao, UserInfo> implements UserInfoService {

}
