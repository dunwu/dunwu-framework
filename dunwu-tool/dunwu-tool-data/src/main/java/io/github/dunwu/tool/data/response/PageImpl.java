package io.github.dunwu.tool.data.response;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.tool.data.request.PageQuery;
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
 * <p>
 * 注意：PageImpl 自身页码从 1 开始，内部会自动适配 Spring Data 页码从 0 开始的机制。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-09-02
 */
public class PageImpl<T> implements Page<T>, Slice<T>, Serializable {

    private static final long serialVersionUID = 120513286513796517L;

    private long total;
    private PageQuery pageQuery;
    private final List<T> content = new ArrayList<>();

    /**
     * 默认构造器方法
     */
    public PageImpl() {
        init(new ArrayList<>(), PageQuery.of(PageQuery.FIRST_PAGE, PageQuery.DEFAULT_SIZE), 0L);
    }

    /**
     * 构造器方法
     *
     * @param content   当前分页的数据
     * @param pageQuery 当前分页请求信息，注：{@link PageQuery} 的页码从 1 开始
     */
    public PageImpl(List<T> content, PageQuery pageQuery) {
        init(content, pageQuery, 0L);
    }

    /**
     * 构造器方法
     *
     * @param content     当前分页的数据
     * @param pageRequest 当前分页请求信息，注：{@link PageRequest} 的页码从 0 开始。PageImpl 内部会自动将页码转为从 1 开始
     */
    public PageImpl(List<T> content, PageRequest pageRequest) {
        init(content, pageRequest, 0L);
    }

    /**
     * 构造器方法
     *
     * @param content   当前分页的数据
     * @param pageQuery 当前分页请求信息，注：{@link PageQuery} 的页码从 1 开始
     * @param total     总记录数
     */
    public PageImpl(List<T> content, PageQuery pageQuery, long total) {
        init(content, pageQuery, total);
    }

    /**
     * 构造器方法
     *
     * @param content     当前分页的数据
     * @param pageRequest 当前分页请求信息，注：{@link PageRequest} 的页码从 0 开始。PageImpl 内部会自动将页码转为从 1 开始
     * @param total       总记录数
     */
    public PageImpl(List<T> content, PageRequest pageRequest, long total) {
        init(content, pageRequest, total);
    }

    /**
     * 构造器方法
     *
     * @param content 当前分页的数据
     * @param page    当前页码
     * @param size    每页记录数
     * @param total   总记录数
     */
    public PageImpl(List<T> content, int page, int size, long total) {
        init(content, PageQuery.of(page, size), total);
    }

    private void init(List<T> content, PageQuery pageQuery, long total) {
        Assert.notNull(pageQuery, "PageQuery must not be null!");
        init(content, pageQuery.toPageRequest(), total);
    }

    private void init(List<T> content, PageRequest pageRequest, long total) {

        Assert.notNull(pageRequest, "PageRequest must not be null!");

        final List<T> list;
        if (content == null) {
            list = new ArrayList<>();
        } else {
            list = content;
        }

        this.pageQuery = PageQuery.of(pageRequest);
        if (total != 0L) {
            this.total = total;
        } else {
            this.total = pageRequest.toOptional()
                                    .filter((it) -> !list.isEmpty())
                                    .filter((it) -> it.getOffset() + (long) it.getPageSize() > total)
                                    .map((it) -> it.getOffset() + (long) list.size())
                                    .orElse(total);
        }

        if (this.total > pageRequest.getPageSize()) {
            List<T> subList = CollectionUtil.sub(list, 0, pageRequest.getPageSize());
            this.content.addAll(subList);
        } else {
            this.content.addAll(list);
        }
    }

    @Override
    public int getNumber() {
        return this.pageQuery.getPage();
    }

    @Override
    public int getSize() {
        return this.pageQuery.getSize();
    }

    @Override
    public Sort getSort() {
        return this.pageQuery.toPageRequest().getSort();
    }

    public long getTotal() {
        return this.total;
    }

    @Override
    public long getTotalElements() {
        return this.getTotal();
    }

    @Override
    public int getTotalPages() {
        return this.getSize() == 0 ? 0 : (int) Math.ceil((double) this.total / (double) this.getSize());
    }

    @Override
    public boolean hasNext() {
        return this.getNumber() < this.getTotalPages();
    }

    @Override
    public boolean hasPrevious() {
        return (this.getNumber() > PageQuery.FIRST_PAGE) && (this.getNumber() <= this.getTotalPages() + 1);
    }

    @Override
    public boolean isFirst() {
        return this.getNumber() == PageQuery.FIRST_PAGE;
    }

    @Override
    public boolean isLast() {
        return !this.hasNext();
    }

    @Override
    public List<T> getContent() {
        return Collections.unmodifiableList(this.content);
    }

    @Override
    public boolean hasContent() {
        return !this.content.isEmpty();
    }

    @Override
    public int getNumberOfElements() {
        return this.content.size();
    }

    @Override
    public Iterator<T> iterator() {
        return this.content.iterator();
    }

    @Override
    public Pageable getPageable() {
        return this.pageQuery.toPageRequest();
    }

    @Override
    public Pageable nextPageable() {
        return hasNext() ? this.pageQuery.toPageRequest().next() : Pageable.unpaged();
    }

    @Override
    public Pageable previousPageable() {
        return hasPrevious() ? this.pageQuery.toPageRequest().previousOrFirst() : Pageable.unpaged();
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new org.springframework.data.domain.PageImpl(this.getConvertedContent(converter), this.getPageable(),
            this.total);
    }

    public PageQuery getPageQuery() {
        return pageQuery;
    }

    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
        Assert.notNull(converter, "Function must not be null!");
        return this.stream().map(converter::apply).collect(Collectors.toList());
    }

    @Override
    public String toString() {
        String contentType = "UNKNOWN";
        List<T> content = this.getContent();
        if (content.size() > 0) {
            contentType = content.get(0).getClass().getName();
        }

        return String.format("Page %s of %d containing %s instances", this.getNumber() + 1, this.getTotalPages(),
            contentType);
    }

    @Override
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

    @Override
    public int hashCode() {
        int result = 17;
        result += 31 * (int) (this.total ^ this.total >>> 32);
        result += 31 * super.hashCode();
        return result;
    }

    public static <T> PageImpl<T> of() {
        return of(new ArrayList<>(), PageQuery.FIRST_PAGE, PageQuery.DEFAULT_SIZE, 0);
    }

    public static <T> PageImpl<T> of(List<T> content, int page, int size, long total) {
        return of(content, PageQuery.of(page, size), total);
    }

    public static <T> PageImpl<T> of(List<T> content, PageQuery pageQuery) {
        return of(content, pageQuery, 0L);
    }

    public static <T> PageImpl<T> of(List<T> content, PageQuery pageQuery, long total) {
        // 适配处理：PageQuery page 从 1 开始，而 org.springframework.data.domain.PageImpl 的 page 从 0 开始
        PageRequest pageRequest =
            PageRequest.of(pageQuery.getPage() > 0 ? pageQuery.getPage() - 1 : 0, pageQuery.getSize());
        return new PageImpl<>(content, pageRequest, total);
    }

    public static <T> PageImpl<T> of(List<T> content, Pageable pageable) {
        return of(content, pageable, 0L);
    }

    public static <T> PageImpl<T> of(List<T> content, Pageable pageable, long total) {
        return new PageImpl<>(content, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), total);
    }

    public static <T> PageImpl<T> of(Page<T> page) {
        if (page instanceof PageImpl) {
            return (PageImpl<T>) page;
        }

        PageRequest pageRequest = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
        return new PageImpl<>(page.getContent(), pageRequest, page.getTotalElements());
    }

}
