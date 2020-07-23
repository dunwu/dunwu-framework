package io.github.dunwu.tool.util.tree;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;

/**
 * 通过转换器将你的实体转化为TreeNodeMap节点实体 属性都存在此处,属性有序，可支持排序
 *
 * @author liangbaikai
 * @since 5.2.1
 */
@SuppressWarnings("rawtypes")
public class Tree extends LinkedHashMap<String, Object> implements Node<Tree> {

    private static final long serialVersionUID = 1L;

    private TreeNodeConfig treeNodeConfig;
    private Tree parent;

    public Tree() {
        this(null);
    }

    /**
     * 构造
     *
     * @param treeNodeConfig TreeNode配置
     */
    public Tree(TreeNodeConfig treeNodeConfig) {
        super();
        this.treeNodeConfig = ObjectUtil.defaultIfNull(treeNodeConfig, TreeNodeConfig.DEFAULT_CONFIG);
    }

    /**
     * 获取父节点
     *
     * @return 父节点
     * @since 5.2.4
     */
    public Tree getParent() {
        return parent;
    }

    /**
     * 获取ID对应的节点，如果有多个ID相同的节点，只返回第一个。<br> 此方法只查找此节点及子节点，采用广度优先遍历。
     *
     * @param id ID
     * @return 节点
     * @since 5.2.4
     */
    public Tree getNode(Serializable id) {
        return TreeUtil.getNode(this, id);
    }

    /**
     * 获取所有父节点名称列表
     *
     * <p>
     * 比如有个人在研发1部，他上面有研发部，接着上面有技术中心<br> 返回结果就是：[研发一部, 研发中心, 技术中心]
     *
     * @param id                 节点ID
     * @param includeCurrentNode 是否包含当前节点的名称
     * @return 所有父节点名称列表
     * @since 5.2.4
     */
    public Collection<CharSequence> getParentsName(Serializable id, boolean includeCurrentNode) {
        return TreeUtil.getParentsName(getNode(id), includeCurrentNode);
    }

    /**
     * 获取所有父节点名称列表
     *
     * <p>
     * 比如有个人在研发1部，他上面有研发部，接着上面有技术中心<br> 返回结果就是：[研发一部, 研发中心, 技术中心]
     *
     * @param includeCurrentNode 是否包含当前节点的名称
     * @return 所有父节点名称列表
     * @since 5.2.4
     */
    public Collection<CharSequence> getParentsName(boolean includeCurrentNode) {
        return TreeUtil.getParentsName(this, includeCurrentNode);
    }

    /**
     * 设置父节点
     *
     * @param parent 父节点
     * @return this
     * @since 5.2.4
     */
    public Tree setParent(Tree parent) {
        this.parent = parent;
        if (null != parent) {
            this.setPid(parent.getId());
        }
        return this;
    }

    @Override
    public Comparable getId() {
        return (Comparable) this.get(treeNodeConfig.getIdKey());
    }

    @Override
    public Tree setId(Comparable id) {
        this.put(treeNodeConfig.getIdKey(), id);
        return this;
    }

    @Override
    public Comparable getPid() {
        return (Comparable) this.get(treeNodeConfig.getPidKey());
    }

    @Override
    public Tree setPid(Comparable pid) {
        this.put(treeNodeConfig.getPidKey(), pid);
        return this;
    }

    @Override
    public String getName() {
        return (String) this.get(treeNodeConfig.getNameKey());
    }

    @Override
    public Tree setName(String name) {
        this.put(treeNodeConfig.getNameKey(), name);
        return this;
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Comparable<?> getWeight() {
        return (Comparable) this.get(treeNodeConfig.getWeightKey());
    }

    @Override
    @SuppressWarnings("rawtypes")
    public Tree setWeight(Comparable weight) {
        this.put(treeNodeConfig.getWeightKey(), weight);
        return this;
    }

    @Override
    public SORT getSort() {
        return (SORT) this.get(treeNodeConfig.getSortKey());
    }

    @Override
    public Tree setSort(SORT sort) {
        this.put(treeNodeConfig.getSortKey(), sort);
        return this;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Collection<Tree> getChildren() {
        return (Collection<Tree>) this.get(treeNodeConfig.getChildrenKey());
    }

    @Override
    public Tree setChildren(Collection<Tree> children) {
        this.put(treeNodeConfig.getChildrenKey(), children);
        return this;
    }

    /**
     * 扩展属性
     *
     * @param key   键
     * @param value 扩展值
     */
    public void putExtra(String key, Object value) {
        Assert.notEmpty(key, "Key must be not empty !");
        this.put(key, value);
    }

    public <T> T toBean(Class<T> clazz) {
        return BeanUtil.mapToBean(this, clazz, false);
    }

}
