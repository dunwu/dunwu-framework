package io.github.dunwu.tool.util.tree;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * 树节点 每个属性都可以在{@link TreeNodeConfig}中被重命名<br> 在你的项目里它可以是部门实体、地区实体等任意类树节点实体 类树节点实体: 包含key，父Key.不限于这些属性的可以构造成一颗树的实体对象
 *
 * @author liangbaikai
 * @author Zhang Peng
 */
public class TreeNode implements Node<TreeNode> {

    /**
     * ID
     */
    private Serializable id;

    /**
     * 父节点ID
     */
    private Serializable pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 级别 越小优先级越高 默认0
     */
    private Integer level = 0;

    private Collection<TreeNode> children;

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
    public TreeNode(Serializable id, Serializable pid, String name, Integer level) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        if (level != null) {
            this.level = level;
        }
    }

    @Override
    public Serializable getId() {
        return id;
    }

    @Override
    public TreeNode setId(Serializable id) {
        this.id = id;
        return this;
    }

    @Override
    public Serializable getPid() {
        return this.pid;
    }

    @Override
    public TreeNode setPid(Serializable pid) {
        this.pid = pid;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public TreeNode setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public Integer getLevel() {
        return level;
    }

    @Override
    public TreeNode setLevel(Integer level) {
        this.level = level;
        return this;
    }

    @Override
    public Collection<TreeNode> getChildren() {
        return children;
    }

    @Override
    public TreeNode setChildren(Collection<TreeNode> children) {
        this.children = children;
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
    public TreeNode setExtra(Map<String, Object> extra) {
        this.extra = extra;
        return this;
    }

    public Map<String, Object> getFull() {
        return full;
    }

    public TreeNode setFull(Map<String, Object> full) {
        this.full = full;
        return this;
    }

}
