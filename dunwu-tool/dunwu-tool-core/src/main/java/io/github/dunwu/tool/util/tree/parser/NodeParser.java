package io.github.dunwu.tool.util.tree.parser;

import io.github.dunwu.tool.util.tree.Node;

/**
 * 树节点解析器 可以参考{@link DefaultNodeParser}
 *
 * @param <T> 转换的实体 为数据源里的对象类型
 * @author liangbaikai
 */
@FunctionalInterface
public interface NodeParser<S extends Node<S>, T extends Node<T>> {

    /**
     * @param object   源数据实体
     * @param treeNode 树节点实体
     */
    void parse(S object, T treeNode);

}

