package io.github.dunwu.tool.data;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * 基础查询实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-24
 */
@Data
@ToString
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 7704551086334591374L;

    /**
     * 当前查询页
     */
    private long current = 1L;

    /**
     * 每页展示记录数
     */
    private long size = 10L;

    public PageQuery() {}

    public PageQuery(long current, long size) {
        this.current = current;
        this.size = size;
    }

}
