package io.github.dunwu.common.util.tree;

import java.util.Collection;

/**
 * 节点接口，提供节点相关的的方法定义
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 5.2.4
 */
@SuppressWarnings("rawtypes")
public interface Node<T> extends Comparable<Node<T>> {

    /**
     * 获取ID
     *
     * @return ID
     */
    Comparable getId();

    /**
     * 设置ID
     *
     * @param id ID
     * @return this
     */
    Node<T> setId(Comparable id);

    /**
     * 获取父节点ID
     *
     * @return 父节点ID
     */
    Comparable getPid();

    /**
     * 设置父节点ID
     *
     * @param pid 父节点ID
     * @return this
     */
    Node<T> setPid(Comparable pid);

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
    Comparable getWeight();

    /**
     * 设置权重
     *
     * @param weight 级别
     * @return this
     */
    Node<T> setWeight(Comparable weight);

    /**
     * 获取排序方式
     *
     * @return 排序方式
     */
    SORT getSort();

    /**
     * 设置排序方式
     *
     * @return 权重
     */
    Node<T> setSort(SORT sort);

    /**
     * 是否有子节点
     *
     * @return 是否有子节点
     */
    boolean isHasChildren();

    /**
     * 设置是否有子节点
     *
     * @param hasChildren 是否有子节点
     * @return this
     */
    Node<T> setHasChildren(boolean hasChildren);

    /**
     * 是否为叶子节点
     *
     * @return 是否为叶子节点
     */
    boolean isLeaf();

    /**
     * 设置是否为叶子节点
     *
     * @param leaf 是否为叶子节点
     * @return this
     */
    Node<T> setLeaf(boolean leaf);

    /**
     * 获取子节点集合
     *
     * @return 子节点集合
     */
    Collection<T> getChildren();

    /**
     * 设置子节点集合
     *
     * @param children 子节点集合
     * @return this
     */
    Node<T> setChildren(Collection<T> children);

    @SuppressWarnings({ "unchecked", "rawtypes", "NullableProblems" })
    @Override
    default int compareTo(Node node) {
        final Comparable weight = this.getWeight();
        if (null != weight) {
            if (this.getSort() == SORT.ASC) {
                final Comparable weightOther = node.getWeight();
                return weight.compareTo(weightOther);
            } else {
                final Comparable weightOther = node.getWeight();
                return weightOther.compareTo(weight);
            }
        }
        return 0;
    }

    enum SORT {
        ASC,
        DESC
    }

}
