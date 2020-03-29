package io.github.dunwu.admin.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.toolkit.SqlHelper;
import io.github.dunwu.admin.system.dto.MenuDTO;
import io.github.dunwu.admin.system.entity.Menu;
import io.github.dunwu.admin.system.service.MenuManager;
import io.github.dunwu.admin.util.SimpleTreeBuilder;
import io.github.dunwu.common.BaseResult;
import io.github.dunwu.common.DataListResult;
import io.github.dunwu.common.DataResult;
import io.github.dunwu.common.annotation.Manager;
import io.github.dunwu.common.constant.ResultStatus;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.util.StringUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Set;

/**
 * <p>
 * 菜单信息 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-25
 */
@Slf4j
@Manager
@RequiredArgsConstructor
public class MenuManagerImpl extends MenuServiceImpl implements MenuManager {

    @Override
    public DataResult<Integer> relatedDeleteById(Integer id, Integer parentId) {
        Menu queryEntity = new Menu();
        if (SimpleTreeBuilder.TOP_NODE_ID.equals(parentId)) {
            queryEntity.setParentId(id);
            baseMapper.deleteById(id);
            return DataResult.success(baseMapper.delete(Wrappers.query(queryEntity)) + 1);
        } else {
            return DataResult.success(baseMapper.deleteById(id));
        }
    }

    @Override
    public BaseResult checkBeforeInsert(Menu entity) {
        int count;

        if (entity.getId() == null) {
            // id == null 表明是 insert 操作
            if (StringUtil.isNotBlank(entity.getUrl())) {
                Menu query = new Menu().setUrl(entity.getUrl());
                count = SqlHelper.retCount(baseMapper.selectCount(Wrappers.query(query)));
                if (count > 0) {
                    return BaseResult.fail(ResultStatus.DATA_ERROR.getCode(), "表单不正确：url 重复");
                }
            }

            return BaseResult.success();
        } else {
            // id != null 表明是 update 操作
            return BaseResult.success();
        }
    }

    @Override
    public DataListResult<MenuDTO> menuTree() {
        List<Menu> allList = baseMapper.selectList(Wrappers.emptyWrapper());
        List<MenuDTO> menuDTOS = BeanUtil.toBeanList(allList, MenuDTO.class);
        Set<MenuDTO> treeSet = SimpleTreeBuilder.buildTree(menuDTOS);
        return DataListResult.success(treeSet);
    }

}
