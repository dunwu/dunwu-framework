package io.github.dunwu.system.service.impl;

import io.github.dunwu.system.entity.UserRole;
import io.github.dunwu.system.dao.mapper.UserRoleMapper;
import io.github.dunwu.system.service.UserRoleService;
import io.github.dunwu.data.mybatis.BaseResultServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户和角色关联信息 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-02
 */
@Service
public class UserRoleServiceImpl extends BaseResultServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
