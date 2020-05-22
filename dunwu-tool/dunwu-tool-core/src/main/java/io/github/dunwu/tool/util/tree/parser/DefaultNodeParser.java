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
 * @author liangbaikai
 * @author Zhang Peng
 */
public class DefaultNodeParser implements NodeParser<TreeNode> {

    @Override
    public void parse(TreeNode treeNode, Tree tree) {
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
