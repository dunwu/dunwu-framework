package io.github.dunwu.quickstart.user.service.impl;

import io.github.dunwu.data.service.ServiceImpl;
import io.github.dunwu.quickstart.user.entity.UserRole;
import io.github.dunwu.quickstart.user.mapper.UserRoleMapper;
import io.github.dunwu.quickstart.user.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色表 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-23
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
