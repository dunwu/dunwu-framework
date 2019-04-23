package io.github.dunwu.quickstart.service.impl;

import io.github.dunwu.quickstart.entity.Role;
import io.github.dunwu.quickstart.dao.RoleDao;
import io.github.dunwu.quickstart.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author Zhang Peng
 * @since 2019-04-23
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

}
