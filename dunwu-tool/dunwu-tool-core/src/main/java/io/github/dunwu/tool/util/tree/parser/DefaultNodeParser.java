package io.github.dunwu.tool.util.tree.parser;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import io.github.dunwu.tool.util.tree.Tree;
import io.github.dunwu.tool.util.tree.TreeNode;

import java.util.ArrayList;
import java.util.Map;

/**
 * 默认的简单转换器
 *
 * @param <T> ID类型
 * @author liangbaikai
 */
public class DefaultNodeParser<T> implements NodeParser<TreeNode<T>, T> {

    @Override
    public void parse(TreeNode<T> treeNode, Tree<T> tree) {
        if (MapUtil.isNotEmpty(treeNode.getFull())) {
            treeNode.getFull().forEach(tree::putExtra);
            if (CollUtil.isEmpty(tree.getChildren())) {
                tree.setChildren(new ArrayList<>());
            }
            return;
        }

        tree.setId(treeNode.getId());
        tree.setPid(treeNode.getPid());
        tree.setLevel(treeNode.getLevel());
        tree.setName(treeNode.getName());
        tree.setChildren(new ArrayList<>());

        //扩展字段
        final Map<String, Object> extra = treeNode.getExtra();
        if (MapUtil.isNotEmpty(extra)) {
            extra.forEach(tree::putExtra);
        }
    }

}
