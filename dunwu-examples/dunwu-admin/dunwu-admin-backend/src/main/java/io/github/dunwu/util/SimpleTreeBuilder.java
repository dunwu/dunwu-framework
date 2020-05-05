package io.github.dunwu.util;

import io.github.dunwu.system.dto.MenuDTO;
import cn.hutool.core.collection.CollectionUtil;

import java.util.*;

/**
 * 简单树结构接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-26
 */
public class SimpleTreeBuilder {

    public static final Integer TOP_NODE_ID = 0;

    public static Set<MenuDTO> buildTree(List<MenuDTO> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }

        Set<MenuDTO> resultSet = new HashSet<>();
        Map<Integer, Set<MenuDTO>> map = new HashMap<>();
        for (MenuDTO node : list) {
            if (!map.containsKey(node.getId())) {
                Set<MenuDTO> tempSet = new HashSet<>();
                map.put(node.getId(), tempSet);
            }

            // 添加顶级节点
            if (node.getParentId().equals(TOP_NODE_ID)) {
                resultSet.add(node);
            } else {
                Set<MenuDTO> tempSet;
                if (!map.containsKey(node.getParentId())) {
                    tempSet = new HashSet<>();
                    map.put(node.getParentId(), tempSet);
                } else {
                    tempSet = map.get(node.getParentId());
                }
                tempSet.add(node);
            }
        }

        for (MenuDTO item : resultSet) {
            Set<MenuDTO> treeDTOS = map.get(item.getId());
            item.setChildren(treeDTOS);
        }
        return resultSet;
    }

    public static MenuDTO getElementById(Set<MenuDTO> set, Integer id) {
        for (MenuDTO item : set) {
            if (id.equals(item.getId())) {
                return item;
            }
        }
        return null;
    }

}
