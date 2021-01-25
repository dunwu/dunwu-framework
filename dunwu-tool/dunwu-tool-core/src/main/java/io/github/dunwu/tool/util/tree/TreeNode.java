package io.github.dunwu.tool.util.tree;

import java.util.Collection;
import java.util.Map;

/**
 * 树节点 每个属性都可以在{@link TreeNodeConfig}中被重命名<br> 在你的项目里它可以是部门实体、地区实体等任意类树节点实体 类树节点实体: 包含key，父Key.不限于这些属性的可以构造成一颗树的实体对象
 *
 * @author liangbaikai
 * @author Zhang Peng
 */
@SuppressWarnings("rawtypes")
public class TreeNode implements Node<TreeNode> {

    /**
     * ID
     */
    private Comparable id;

    /**
     * 父节点ID
     */
    private Comparable pid;

    /**
     * 名称
     */
    private String name;

    /**
     * 权重。默认0
     */
    private Comparable<?> weight = 0;

    /**
     * 排序方式，默认降序，即权重越大排序越靠前
     */
    private SORT sort = SORT.DESC;

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
     * @param id     ID
     * @param pid    父节点ID
     * @param name   名称
     * @param weight 级别
     */
    public TreeNode(Comparable id, Comparable pid, String name, Integer weight) {
        this.id = id;
        this.pid = pid;
        this.name = name;
        if (weight != null) {
            this.weight = weight;
        }
    }

    @Override
    public Comparable getId() {
        return id;
    }

    @Override
    public TreeNode setId(Comparable id) {
        this.id = id;
        return this;
    }

    @Override
    public Comparable getPid() {
        return this.pid;
    }

    @Override
    public TreeNode setPid(Comparable pid) {
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
    public Comparable<?> getWeight() {
        return weight;
    }

    @Override
    @SuppressWarnings("all")
    public TreeNode setWeight(Comparable weight) {
        this.weight = weight;
        return this;
    }

    @Override
    public SORT getSort() {
        return sort;
    }

    @Override
    public TreeNode setSort(SORT sort) {
        this.sort = sort;
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
