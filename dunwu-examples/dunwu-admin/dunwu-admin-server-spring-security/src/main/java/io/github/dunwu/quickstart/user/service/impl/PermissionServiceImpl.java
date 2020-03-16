package io.github.dunwu.quickstart.user.service.impl;

import io.github.dunwu.data.mybatis.ServiceImpl;
import io.github.dunwu.quickstart.user.entity.Permission;
import io.github.dunwu.quickstart.user.mapper.PermissionMapper;
import io.github.dunwu.quickstart.user.service.PermissionService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-17
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission>
    implements PermissionService {

}
