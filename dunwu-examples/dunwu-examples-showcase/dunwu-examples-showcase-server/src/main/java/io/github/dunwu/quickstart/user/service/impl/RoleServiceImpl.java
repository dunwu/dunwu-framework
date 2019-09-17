package io.github.dunwu.quickstart.user.service.impl;

import io.github.dunwu.quickstart.user.entity.Role;
import io.github.dunwu.quickstart.user.mapper.RoleMapper;
import io.github.dunwu.quickstart.user.service.RoleService;
import io.github.dunwu.data.service.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-17
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role>
		implements RoleService {

}
