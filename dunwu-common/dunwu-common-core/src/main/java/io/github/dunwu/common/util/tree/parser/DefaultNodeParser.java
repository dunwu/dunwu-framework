package io.github.dunwu.common.util.tree.parser;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.map.MapUtil;
import io.github.dunwu.common.util.tree.Tree;
import io.github.dunwu.common.util.tree.TreeNode;

import java.util.ArrayList;
import java.util.Map;

/**
 * 默认的简单转换器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 5.2.1
 */
public class DefaultNodeParser implements NodeParser<TreeNode, Tree> {

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
        tree.setWeight(treeNode.getWeight());
        tree.setName(treeNode.getName());
        tree.setChildren(new ArrayList<>());

        //扩展字段
        final Map<String, Object> extra = treeNode.getExtra();
        if (MapUtil.isNotEmpty(extra)) {
            extra.forEach(tree::putExtra);
        }
    }

}
