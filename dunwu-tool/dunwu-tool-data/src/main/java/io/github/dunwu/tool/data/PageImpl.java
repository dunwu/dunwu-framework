package io.github.dunwu.tool.data;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * {@link Page} 扩展实现类，完全移植了 {@link PageImpl} 的能力
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-09-02
 */
public class PageImpl<T> implements Page<T>, Slice<T>, Serializable {

    private static final long serialVersionUID = 120513286513796517L;

    private long total;
    private PageQuery pageQuery;
    private final List<T> content = new ArrayList<>();

    public PageImpl() {
        this.pageQuery = PageQuery.of(1, 10);
        this.total = 0;
    }

    public PageImpl(List<T> content, PageQuery pageQuery) {
        Assert.notNull(pageQuery, "PageRequest must not be null!");
        this.content.addAll(content);
        this.pageQuery = pageQuery;
    }

    public PageImpl(List<T> content, PageRequest pageRequest) {
        Assert.notNull(pageRequest, "PageRequest must not be null!");
        this.content.addAll(content);
        this.pageQuery = PageQuery.of(pageRequest);
    }

    public PageImpl(List<T> content, PageQuery pageQuery, long total) {
        Assert.notNull(pageQuery, "PageQuery must not be null!");
        this.content.addAll(content);
        this.pageQuery = pageQuery;
        PageRequest pageRequest = pageQuery.toPageRequest();
        this.total = pageRequest.toOptional()
                                .filter((it) -> !content.isEmpty())
                                .filter((it) -> it.getOffset() + (long) it.getPageSize() > total)
                                .map((it) -> it.getOffset() + (long) content.size())
                                .orElse(total);
    }

    public PageImpl(List<T> content, PageRequest pageRequest, long total) {
        Assert.notNull(pageRequest, "PageRequest must not be null!");
        this.content.addAll(content);
        this.pageQuery = PageQuery.of(pageRequest);
        this.total = pageRequest.toOptional()
                                .filter((it) -> !content.isEmpty())
                                .filter((it) -> it.getOffset() + (long) it.getPageSize() > total)
                                .map((it) -> it.getOffset() + (long) content.size())
                                .orElse(total);
    }

    public PageImpl(List<T> content, int page, int size, long total) {
        this.content.addAll(content);
        this.pageQuery = PageQuery.of(page, size);
        this.total = total;
    }

    public int getNumber() {
        return this.pageQuery.getPage();
    }

    public int getSize() {
        return this.pageQuery.getSize();
    }

    public int getNumberOfElements() {
        return this.content.size();
    }

    public boolean hasNext() {
        return this.getNumber() + 1 < this.getTotalPages();
    }

    public boolean hasPrevious() {
        return this.getNumber() > 0;
    }

    @Override
    public Pageable nextPageable() {
        PageRequest pageRequest = pageQuery.toPageRequest();
        return hasNext() ? pageRequest.next() : Pageable.unpaged();
    }

    @Override
    public Pageable previousPageable() {
        PageRequest pageRequest = pageQuery.toPageRequest();
        return hasPrevious() ? pageRequest.previousOrFirst() : Pageable.unpaged();
    }

    public boolean isFirst() {
        return !this.hasPrevious();
    }

    public boolean isLast() {
        return !this.hasNext();
    }

    public boolean hasContent() {
        return !this.content.isEmpty();
    }

    @Override
    public Sort getSort() {
        PageRequest pageRequest = pageQuery.toPageRequest();
        return pageRequest.getSort();
    }

    public List<T> getContent() {
        return Collections.unmodifiableList(this.content);
    }

    public Iterator<T> iterator() {
        return this.content.iterator();
    }

    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
        Assert.notNull(converter, "Function must not be null!");
        return this.stream().map(converter::apply).collect(Collectors.toList());
    }

    public int getTotalPages() {
        return this.getSize() == 0 ? 1 : (int) Math.ceil((double) this.total / (double) this.getSize());
    }

    public long getTotalElements() {
        return this.total;
    }

    public long getTotal() {
        return this.total;
    }

    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new org.springframework.data.domain.PageImpl(this.getConvertedContent(converter), this.getPageable(),
            this.total);
    }

    public String toString() {
        String contentType = "UNKNOWN";
        List<T> content = this.getContent();
        if (content.size() > 0) {
            contentType = content.get(0).getClass().getName();
        }

        return String.format("Page %s of %d containing %s instances", this.getNumber() + 1, this.getTotalPages(),
            contentType);
    }

    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof org.springframework.data.domain.PageImpl)) {
            return false;
        } else {
            PageImpl<?> that = (PageImpl) obj;
            return this.total == that.total && super.equals(obj);
        }
    }

    public int hashCode() {
        int result = 17;
        result += 31 * (int) (this.total ^ this.total >>> 32);
        result += 31 * super.hashCode();
        return result;
    }

    public static <T> PageImpl<T> of() {
        return new PageImpl<>(new ArrayList<>(), PageRequest.of(1, 10), 0);
    }

    public static <T> PageImpl<T> of(List<T> content) {
        return new PageImpl<>(content, PageRequest.of(1, 10), null == content ? 0L : (long) content.size());
    }

    public static <T> PageImpl<T> of(List<T> content, PageQuery pageQuery) {
        return new PageImpl<>(content, pageQuery);
    }

    public static <T> PageImpl<T> of(List<T> content, Pageable pageable) {
        return new PageImpl<>(content, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()));
    }

    public static <T> PageImpl<T> of(List<T> content, PageQuery pageQuery, long total) {
        // 适配处理：PageQuery page 从 1 开始，而 org.springframework.data.domain.PageImpl 的 page 从 0 开始
        PageRequest pageRequest =
            PageRequest.of(pageQuery.getPage() > 0 ? pageQuery.getPage() - 1 : 0, pageQuery.getSize());
        return new PageImpl<>(content, pageRequest, total);
    }

    public static <T> PageImpl<T> of(List<T> content, Pageable pageable, long total) {
        return new PageImpl<>(content, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), total);
    }

    public static <T> PageImpl<T> of(List<T> content, int page, int size, long total) {
        return new PageImpl<>(content, PageRequest.of(page, size), total);
    }

    public static <T> PageImpl<T> of(Page<T> page) {
        if (page instanceof PageImpl) {
            return (PageImpl<T>) page;
        }

        PageRequest pageRequest = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
        return new PageImpl<>(page.getContent(), pageRequest, page.getTotalElements());
    }

}
