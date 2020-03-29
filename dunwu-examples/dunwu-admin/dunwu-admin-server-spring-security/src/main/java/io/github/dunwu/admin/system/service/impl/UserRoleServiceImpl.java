package io.github.dunwu.admin.system.service.impl;

import io.github.dunwu.admin.system.entity.UserRole;
import io.github.dunwu.admin.system.mapper.UserRoleMapper;
import io.github.dunwu.admin.system.service.UserRoleService;
import io.github.dunwu.data.mybatis.ServiceImpl;
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
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole>
    implements UserRoleService {

}
