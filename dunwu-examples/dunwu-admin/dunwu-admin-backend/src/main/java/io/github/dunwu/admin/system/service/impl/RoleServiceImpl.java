package io.github.dunwu.admin.system.service.impl;

import io.github.dunwu.admin.system.entity.Role;
import io.github.dunwu.admin.system.mapper.RoleMapper;
import io.github.dunwu.admin.system.service.RoleService;
import io.github.dunwu.data.mybatis.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-25
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
