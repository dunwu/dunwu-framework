package io.github.dunwu.tool.util.tree;

import java.io.Serializable;
import java.util.Collection;

/**
 * 节点接口，提供节点相关的的方法定义
 *
 * @author looly
 * @since 5.2.4
 */
public interface Node<T> extends Comparable<Node> {

    /**
     * 获取ID
     *
     * @return ID
     */
    Serializable getId();

    /**
     * 设置ID
     *
     * @param id ID
     * @return this
     */
    Node<T> setId(Serializable id);

    /**
     * 获取父节点ID
     *
     * @return 父节点ID
     */
    Serializable getPid();

    /**
     * 设置父节点ID
     *
     * @param pid 父节点ID
     * @return this
     */
    Node<T> setPid(Serializable pid);

    /**
     * 获取节点标签名称
     *
     * @return 节点标签名称
     */
    String getName();

    /**
     * 设置节点标签名称
     *
     * @param name 节点标签名称
     * @return this
     */
    Node<T> setName(String name);

    /**
     * 获取权重
     *
     * @return 权重
     */
    Integer getWeight();

    /**
     * 设置权重
     *
     * @param weight 级别
     * @return this
     */
    Node<T> setWeight(Integer weight);

    Collection<T> getChildren();

    Node<T> setChildren(Collection<T> children);

    @SuppressWarnings({ "unchecked", "rawtypes", "NullableProblems" })
    @Override
    default int compareTo(Node node) {
        final Comparable level = this.getWeight();
        if (null != level) {
            final Comparable weightOther = node.getWeight();
            return level.compareTo(weightOther);
        }
        return 0;
    }

}
