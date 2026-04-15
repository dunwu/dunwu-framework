package io.github.dunwu.common.dal.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

/**
 * 滚动查询数据实体
 * <p>
 * 用于封装 Elasticsearch 等存储引擎的滚动查询（Scroll）结果，包含滚动 ID、当前批次数据及总量信息。
 *
 * @param <T> 数据类型
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-11-16
 */
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ScrollData<T> implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 滚动查询 ID，用于获取下一批数据
     */
    private String scrollId;

    /**
     * 每页记录数
     */
    private int size = 10;

    /**
     * 总记录数
     */
    private long total = 0L;

    /**
     * 当前批次的数据内容
     */
    private List<T> content;

    /**
     * 判断当前页是否有数据
     *
     * @return 如果有数据返回 true，否则返回 false
     */
    public boolean hasContent() {
        return !this.content.isEmpty();
    }

}
