package io.github.dunwu.admin.system.service.impl;

import io.github.dunwu.admin.system.entity.RolePermission;
import io.github.dunwu.admin.system.mapper.RolePermissionMapper;
import io.github.dunwu.admin.system.service.RolePermissionService;
import io.github.dunwu.data.mybatis.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和权限关联信息 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-28
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

}
