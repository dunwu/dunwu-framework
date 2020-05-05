package io.github.dunwu.system.service.impl;

import io.github.dunwu.system.entity.Permission;
import io.github.dunwu.system.dao.mapper.PermissionMapper;
import io.github.dunwu.system.service.PermissionService;
import io.github.dunwu.data.mybatis.BaseResultServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限信息 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-02
 */
@Service
public class PermissionServiceImpl extends BaseResultServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
