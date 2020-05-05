package io.github.dunwu.system.service.impl;

import io.github.dunwu.system.entity.RolePermission;
import io.github.dunwu.system.dao.mapper.RolePermissionMapper;
import io.github.dunwu.system.service.RolePermissionService;
import io.github.dunwu.data.mybatis.BaseResultServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色和权限关联信息 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-02
 */
@Service
public class RolePermissionServiceImpl extends BaseResultServiceImpl<RolePermissionMapper, RolePermission> implements
    RolePermissionService {

}
