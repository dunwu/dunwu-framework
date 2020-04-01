package io.github.dunwu.admin.system.service;

import io.github.dunwu.admin.system.dto.MenuDTO;
import io.github.dunwu.admin.system.entity.Menu;
import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.DataListResult;
import io.github.dunwu.common.DataResult;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-26
 */
public interface MenuManager extends MenuService {

    @Transactional(rollbackFor = Exception.class)
    DataResult<Integer> relatedDeleteById(Integer id, Integer parentId);

    BaseResult checkBeforeInsert(Menu entity);

    DataListResult<MenuDTO> menuTree();

}
