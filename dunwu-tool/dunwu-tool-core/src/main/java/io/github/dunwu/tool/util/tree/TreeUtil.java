package io.github.dunwu.tool.util.tree;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import io.github.dunwu.tool.util.tree.parser.DefaultNodeParser;
import io.github.dunwu.tool.util.tree.parser.NodeParser;

import java.io.Serializable;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 树工具类
 *
 * @author liangbaikai
 */
public class TreeUtil {

    /**
     * 树构建
     *
     * @param list 源数据集合
     * @return List
     */
    public static Collection<Tree> build(Collection<TreeNode> list) {
        return build(list, 0);
    }

    /**
     * 树构建
     *
     * @param list     源数据集合
     * @param parentId 最顶层父id值 一般为 0 之类
     * @return List
     */
    public static Collection<Tree> build(Collection<TreeNode> list, Serializable parentId) {
        return build(list, parentId, TreeNodeConfig.DEFAULT_CONFIG, new DefaultNodeParser());
    }

    /**
     * 树构建
     *
     * @param list           源数据集合
     * @param parentId       最顶层父id值 一般为 0 之类
     * @param treeNodeConfig 配置
     * @return List
     */
    public static Collection<Tree> build(Collection<TreeNode> list, Serializable parentId,
        TreeNodeConfig treeNodeConfig) {
        return build(list, parentId, treeNodeConfig, new DefaultNodeParser());
    }

    /**
     * 树构建
     *
     * @param list       源数据集合
     * @param parentId   最顶层父id值 一般为 0 之类
     * @param nodeParser 转换器
     * @return List
     */
    public static Collection<Tree> build(Collection<TreeNode> list, Serializable parentId,
        NodeParser<TreeNode> nodeParser) {
        return build(list, parentId, TreeNodeConfig.DEFAULT_CONFIG, nodeParser);
    }

    /**
     * 树构建
     *
     * @param list           源数据集合
     * @param rootId         最顶层父id值 一般为 0 之类
     * @param treeNodeConfig 配置
     * @param nodeParser     转换器
     * @return List
     */
    public static Collection<Tree> build(Collection<TreeNode> list, Serializable rootId, TreeNodeConfig treeNodeConfig,
        NodeParser<TreeNode> nodeParser) {
        final List<Tree> treeList = CollUtil.newArrayList();
        Tree tree;
        for (TreeNode obj : list) {
            tree = new Tree(treeNodeConfig);
            nodeParser.parse(obj, tree);
            treeList.add(tree);
        }

        List<Tree> finalTreeList = new ArrayList<>();
        Set<Serializable> ids = new HashSet<>();
        for (Tree parentNode : treeList) {
            // 如果是顶级节点（非根节点），直接加入树列表
            if (parentNode.getPid() == rootId) {
                finalTreeList.add(parentNode);
            }

            for (Tree it : treeList) {
                if (it.getPid().equals(parentNode.getId())) {
                    if (parentNode.getChildren() == null) {
                        parentNode.setChildren(new ArrayList<>());
                    }
                    parentNode.getChildren().add(it);
                    ids.add(it.getId());
                }
            }
            if (CollUtil.isNotEmpty(parentNode.getChildren())) {
                List<Tree> children = parentNode.getChildren().stream().sorted().collect(Collectors.toList());
                parentNode.setChildren(children);
            }
        }

        // 如果没有成功组织为树结构，直接将剩余节点加入列表
        if (finalTreeList.size() == 0) {
            finalTreeList = treeList.stream().filter(s -> !ids.contains(s.getId())).collect(Collectors.toList());
        }

        // 内存每层已经排过了 这是最外层排序
        finalTreeList = finalTreeList.stream().sorted().collect(Collectors.toList());
        return finalTreeList;
    }

    /**
     * 获取ID对应的节点，如果有多个ID相同的节点，只返回第一个。<br> 此方法只查找此节点及子节点，采用广度优先遍历。
     *
     * @param node 节点
     * @param id   ID
     * @return 节点
     * @since 5.2.4
     */
    public static Tree getNode(Tree node, Serializable id) {
        if (ObjectUtil.equal(id, node.getId())) {
            return node;
        }

        // 查找子节点
        Tree childNode;
        for (Tree child : node.getChildren()) {
            childNode = child.getNode(id);
            if (null != childNode) {
                return childNode;
            }
        }

        // 未找到节点
        return null;
    }

    /**
     * 获取所有父节点名称列表
     *
     * <p>
     * 比如有个人在研发1部，他上面有研发部，接着上面有技术中心<br> 返回结果就是：[研发一部, 研发中心, 技术中心]
     *
     * @param node               节点
     * @param includeCurrentNode 是否包含当前节点的名称
     * @return 所有父节点名称列表，node为null返回空List
     * @since 5.2.4
     */
    public static Collection<CharSequence> getParentsName(Tree node, boolean includeCurrentNode) {
        final List<CharSequence> result = new ArrayList<>();
        if (null == node) {
            return result;
        }

        if (includeCurrentNode) {
            result.add(node.getName());
        }

        Tree parent = node.getParent();
        while (null != parent) {
            result.add(parent.getName());
            parent = parent.getParent();
        }
        return result;
    }

}