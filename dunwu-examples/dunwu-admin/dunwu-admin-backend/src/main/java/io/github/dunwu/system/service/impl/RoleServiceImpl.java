package io.github.dunwu.system.service.impl;

import io.github.dunwu.system.entity.Role;
import io.github.dunwu.system.dao.mapper.RoleMapper;
import io.github.dunwu.system.service.RoleService;
import io.github.dunwu.data.mybatis.BaseResultServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色信息 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-02
 */
@Service
public class RoleServiceImpl extends BaseResultServiceImpl<RoleMapper, Role> implements RoleService {

}
