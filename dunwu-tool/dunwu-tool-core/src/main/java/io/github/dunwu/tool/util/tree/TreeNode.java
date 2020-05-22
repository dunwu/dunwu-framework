package io.github.dunwu.tool.util.tree;

import java.util.Map;

/**
 * 树节点 每个属性都可以在{@link TreeNodeConfig}中被重命名<br> 在你的项目里它可以是部门实体、地区实体等任意类树节点实体 类树节点实体: 包含key，父Key.不限于这些属性的可以构造成一颗树的实体对象
 *
 * @param <T> ID类型
 * @author liangbaikai
 */
public class TreeNode<T> implements Node<T> {

    /**
     * ID
     */
    private T id;

    /**
     * 父节点ID
     */
    private T pid;

    /**
     * 名称
     */
    private CharSequence name;

    /**
     * 级别 越小优先级越高 默认0
     */
    private Comparable<?> level = 0;

    /**
     * 扩展字段
     */
    private Map<String, Object> extra;

    /**
     * 所有字段
     */
    private Map<String, Object> full;

    /**
     * 空构造
     */
    public TreeNode() { }

    /**
     * 构造
     *
     * @param id    ID
     * @param pid   父节点ID
     * @param name  名称
     * @param level 级别
     */
    public TreeNode(T id, T pid, String name, Comparable<?> level) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        if (level != null) {
            this.level = level;
        }
    }

    @Override
    public T getId() {
        return id;
    }

    @Override
    public TreeNode<T> setId(T id) {
        this.id = id;
        return this;
    }

    @Override
    public T getPid() {
        return this.pid;
    }

    @Override
    public TreeNode<T> setPid(T pid) {
        this.pid = pid;
        return this;
    }

    @Override
    public CharSequence getName() {
        return name;
    }

    @Override
    public TreeNode<T> setName(CharSequence name) {
        this.name = name;
        return this;
    }

    @Override
    public Comparable<?> getLevel() {
        return level;
    }

    @Override
    public TreeNode<T> setLevel(Comparable<?> level) {
        this.level = level;
        return this;
    }

    /**
     * 获取扩展字段
     *
     * @return 扩展字段Map
     * @since 5.2.5
     */
    public Map<String, Object> getExtra() {
        return extra;
    }

    /**
     * 设置扩展字段
     *
     * @param extra 扩展字段
     * @return this
     * @since 5.2.5
     */
    public TreeNode<T> setExtra(Map<String, Object> extra) {
        this.extra = extra;
        return this;
    }

    public Map<String, Object> getFull() {
        return full;
    }

    public TreeNode<T> setFull(Map<String, Object> full) {
        this.full = full;
        return this;
    }

}
