package io.github.dunwu.common.dal.domain;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

/**
 * 滚动翻页查询请求参数类。
 * <p>
 * 用于封装滚动查询（Scroll）的请求参数，通常用于 Elasticsearch 等存储引擎的大数据量分批查询场景。 通过 {@code scrollId} 维持查询上下文，实现高效的数据拉取。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2024-06-05
 */
@Setter
@Getter
public class ScrollQuery implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 默认每页记录数
     */
    public static final int DEFAULT_SIZE = 100;

    /**
     * 上一次滚动分页查询返回的 ID，用于获取下一批数据
     */
    protected String scrollId;

    /**
     * 每页记录数（批次大小）
     */
    protected int size = DEFAULT_SIZE;

}
