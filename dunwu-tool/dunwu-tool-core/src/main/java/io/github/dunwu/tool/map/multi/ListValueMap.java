package io.github.dunwu.tool.map.multi;

import java.util.*;

/**
 * 值作为集合List的Map实现，通过调用putValue可以在相同key时加入多个值，多个值用集合表示
 *
 * @param <K> 键类型
 * @param <V> 值类型
 * @author looly
 * @since 4.3.3
 */
public class ListValueMap<K, V> extends CollectionValueMap<K, V> {

    private static final long serialVersionUID = 6044017508487827899L;

    // ------------------------------------------------------------------------- Constructor start

    /**
     * 构造
     */
    public ListValueMap() {
        this(DEFAULT_INITIAL_CAPACITY);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     */
    public ListValueMap(int initialCapacity) {
        this(initialCapacity, DEFAULT_LOAD_FACTOR);
    }

    /**
     * 构造
     *
     * @param initialCapacity 初始大小
     * @param loadFactor      加载因子
     */
    public ListValueMap(int initialCapacity, float loadFactor) {
        super(new HashMap<K, Collection<V>>(initialCapacity, loadFactor));
    }

    /**
     * 构造
     *
     * @param m Map
     */
    public ListValueMap(Map<? extends K, ? extends Collection<V>> m) {
        this(DEFAULT_LOAD_FACTOR, m);
    }

    /**
     * 构造
     *
     * @param loadFactor 加载因子
     * @param m          Map
     */
    public ListValueMap(float loadFactor, Map<? extends K, ? extends Collection<V>> m) {
        this(m.size(), loadFactor);
        this.putAll(m);
    }
    // ------------------------------------------------------------------------- Constructor end

    @Override
    public List<V> get(Object key) {
        return (List<V>) super.get(key);
    }

    @Override
    protected Collection<V> createCollction() {
        return new ArrayList<>(DEFAULT_COLLCTION_INITIAL_CAPACITY);
    }

}
