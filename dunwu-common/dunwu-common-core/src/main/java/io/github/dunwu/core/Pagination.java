package io.github.dunwu.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

public class Pagination<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 当前查询页的数据列表
     */
    protected Collection<T> list = Collections.emptyList();

    /**
     * 当前查询页
     */
    protected long current = 1L;

    /**
     * 每页展示记录数
     */
    protected long size = 10L;

    /**
     * 总记录数
     */
    protected long total = 0L;

    public Pagination() {}

    public Pagination(long current, long size, long total) {
        this.current = current;
        this.size = size;
        this.total = total;
    }

    public Pagination(long current, long size, long total, Collection<T> list) {
        this.current = current;
        this.size = size;
        this.total = total;
        this.list = list;
    }

    @Override
    public String toString() {
        return "Pagination{" + "list=" + list + ", current=" + current + ", size=" + size
            + ", total=" + total + '}';
    }

    public Collection<T> getList() {
        return list;
    }

    public Pagination<T> setList(Collection<T> list) {
        this.list = list;
        return this;
    }

    public long getCurrent() {
        return current;
    }

    public Pagination<T> setCurrent(long current) {
        this.current = current;
        return this;
    }

    public long getSize() {
        return size;
    }

    public Pagination<T> setSize(long size) {
        this.size = size;
        return this;
    }

    public long getTotal() {
        return total;
    }

    public Pagination<T> setTotal(long total) {
        this.total = total;
        return this;
    }

}
