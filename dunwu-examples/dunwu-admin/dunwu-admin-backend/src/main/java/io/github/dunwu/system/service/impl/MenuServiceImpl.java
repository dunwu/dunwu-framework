package io.github.dunwu.system.service.impl;

import io.github.dunwu.system.entity.Menu;
import io.github.dunwu.system.dao.mapper.MenuMapper;
import io.github.dunwu.system.service.MenuService;
import io.github.dunwu.data.mybatis.BaseResultServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 菜单信息 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-02
 */
@Service
public class MenuServiceImpl extends BaseResultServiceImpl<MenuMapper, Menu> implements MenuService {

}
