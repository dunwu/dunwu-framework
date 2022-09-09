package io.github.dunwu.tool.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * {@link org.springframework.data.domain.PageImpl} 扩展实现类
 * 
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-09-02
 */
public class PageImpl<T> extends org.springframework.data.domain.PageImpl<T> {

    public PageImpl(List<T> content, PageQuery pageQuery, long total) {
        // 注：PageQuery page 从 1 开始，而 org.springframework.data.domain.PageImpl 的 page 从 0 开始
        this(content, PageRequest.of(pageQuery.getPage() > 0 ? pageQuery.getPage() - 1 : 0, pageQuery.getSize()), total);
    }

    public PageImpl(List<T> content, int page, int size, long total) {
        this(content, PageRequest.of(page, size), total);
    }

    public PageImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PageImpl(List<T> content) {
        super(content);
    }
}
