package io.github.dunwu.demo.service.impl;

import io.github.dunwu.demo.entity.Role;
import io.github.dunwu.demo.dao.RoleDao;
import io.github.dunwu.demo.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author Zhang Peng
 * @since 2019-04-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleDao, Role> implements RoleService {

}
