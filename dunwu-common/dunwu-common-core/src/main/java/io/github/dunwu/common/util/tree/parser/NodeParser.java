package io.github.dunwu.common.util.tree.parser;

import io.github.dunwu.common.util.tree.Node;

/**
 * 树节点解析器 可以参考{@link DefaultNodeParser}
 *
 * @param <S> 源数据节点类型
 * @param <T> 目标树节点类型
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 5.2.1
 */
@FunctionalInterface
public interface NodeParser<S extends Node<S>, T extends Node<T>> {

    /**
     * 将源数据实体解析为树节点实体
     *
     * @param object   源数据实体
     * @param treeNode 树节点实体
     */
    void parse(S object, T treeNode);

}

