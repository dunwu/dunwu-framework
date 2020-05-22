package io.github.dunwu.tool.util.tree;

import java.io.Serializable;

/**
 * 节点接口，提供节点相关的的方法定义
 *
 * @author looly
 * @since 5.2.4
 */
public interface Node extends Comparable<Node> {

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
    Node setId(Serializable id);

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
    Node setPid(Serializable pid);

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
    Node setName(String name);

    /**
     * 获取权重
     *
     * @return 权重
     */
    Integer getLevel();

    /**
     * 设置权重
     *
     * @param level 级别
     * @return this
     */
    Node setLevel(Integer level);

    @SuppressWarnings({ "unchecked", "rawtypes", "NullableProblems" })
    @Override
    default int compareTo(Node node) {
        final Comparable level = this.getLevel();
        if (null != level) {
            final Comparable weightOther = node.getLevel();
            return level.compareTo(weightOther);
        }
        return 0;
    }

}
