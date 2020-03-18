package io.github.dunwu.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;

@Data
@ToString
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

}
