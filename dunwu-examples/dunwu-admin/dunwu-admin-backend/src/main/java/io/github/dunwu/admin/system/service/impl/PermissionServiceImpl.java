package io.github.dunwu.admin.system.service.impl;

import io.github.dunwu.admin.system.entity.Permission;
import io.github.dunwu.admin.system.mapper.PermissionMapper;
import io.github.dunwu.admin.system.service.PermissionService;
import io.github.dunwu.data.mybatis.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限信息 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-28
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
