package io.github.dunwu.common.dal.domain;

import cn.hutool.core.collection.CollectionUtil;
import lombok.Getter;
import org.springframework.data.domain.*;
import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 自定义分页实现类，扩展了 Spring Data 的 {@link Page} 和 {@link Slice} 接口。
 * <p>
 * 本类完全移植了 {@link org.springframework.data.domain.PageImpl} 的能力，并针对业务场景进行了适配：
 * <ul>
 *     <li><strong>页码从 1 开始</strong>：与 Spring Data 默认的 0-based 页码不同，本类的页码（{@code page}）从 1 开始，更符合人类直觉。</li>
 *     <li><strong>内部自动适配</strong>：内部会自动将 1-based 页码转换为 Spring Data 所需的 0-based 页码进行底层计算。</li>
 *     <li><strong>集成 {@link PageQuery}</strong>：推荐使用 {@link PageQuery} 作为分页查询参数，保持项目内分页规范统一。</li>
 * </ul>
 *
 * @param <T> 分页数据元素的类型
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-09-02
 */
@Getter
public class PageImpl<T> implements Page<T>, Slice<T>, Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 分页查询参数，记录当前的分页请求信息（页码从 1 开始）
     */
    private PageQuery pageQuery;

    /**
     * 当前页的数据内容
     */
    private final List<T> content = new ArrayList<>();

    /**
     * 默认构造器方法
     * <p>
     * 初始化为空列表，页码为 1，每页大小为默认值，总记录数为 0。
     */
    public PageImpl() {
        init(new ArrayList<>(), PageQuery.of(PageQuery.FIRST_PAGE, PageQuery.DEFAULT_SIZE), 0L);
    }

    /**
     * 构造器方法
     *
     * @param content   当前分页的数据列表
     * @param pageQuery 分页查询参数，注：{@link PageQuery} 的页码从 1 开始
     */
    public PageImpl(List<T> content, PageQuery pageQuery) {
        init(content, pageQuery, 0L);
    }

    /**
     * 构造器方法
     *
     * @param content     当前分页的数据列表
     * @param pageRequest Spring Data 的分页请求对象，注：{@link PageRequest} 的页码从 0 开始。本类内部会自动转换为 1-based 页码
     */
    public PageImpl(List<T> content, PageRequest pageRequest) {
        init(content, pageRequest, 0L);
    }

    /**
     * 构造器方法
     *
     * @param content   当前分页的数据列表
     * @param pageQuery 分页查询参数，注：{@link PageQuery} 的页码从 1 开始
     * @param total     总记录数
     */
    public PageImpl(List<T> content, PageQuery pageQuery, long total) {
        init(content, pageQuery, total);
    }

    /**
     * 构造器方法
     *
     * @param content     当前分页的数据列表
     * @param pageRequest Spring Data 的分页请求对象，注：{@link PageRequest} 的页码从 0 开始。本类内部会自动转换为 1-based 页码
     * @param total       总记录数
     */
    public PageImpl(List<T> content, PageRequest pageRequest, long total) {
        init(content, pageRequest, total);
    }

    /**
     * 构造器方法
     *
     * @param content 当前分页的数据列表
     * @param page    当前页码（从 1 开始）
     * @param size    每页记录数
     * @param total   总记录数
     */
    public PageImpl(List<T> content, int page, int size, long total) {
        init(content, PageQuery.of(page, size), total);
    }

    /**
     * 初始化分页数据
     *
     * @param content   当前分页的数据列表
     * @param pageQuery 分页查询参数（1-based）
     * @param total     总记录数
     */
    private void init(List<T> content, PageQuery pageQuery, long total) {
        Assert.notNull(pageQuery, "PageQuery must not be null!");
        init(content, pageQuery.toPageRequest(), total);
    }

    /**
     * 初始化分页数据
     *
     * @param content     当前分页的数据列表
     * @param pageRequest Spring Data 分页请求（0-based）
     * @param total       总记录数
     */
    private void init(List<T> content, PageRequest pageRequest, long total) {

        Assert.notNull(pageRequest, "PageRequest must not be null!");

        final List<T> list;
        list = Objects.requireNonNullElseGet(content, ArrayList::new);

        this.pageQuery = PageQuery.of(pageRequest);
        if (total != 0L) {
            this.total = total;
        } else {
            this.total = pageRequest.toOptional().filter((it) -> !list.isEmpty())
                .filter((it) -> it.getOffset() + (long)it.getPageSize() > total)
                .map((it) -> it.getOffset() + (long)list.size()).orElse(total);
        }

        if (this.total > pageRequest.getPageSize()) {
            List<T> subList = CollectionUtil.sub(list, 0, pageRequest.getPageSize());
            this.content.addAll(subList);
        } else {
            this.content.addAll(list);
        }
    }

    /**
     * 获取当前页码（从 1 开始）
     *
     * @return 当前页码
     */
    @Override
    public int getNumber() {
        return this.pageQuery.getPage();
    }

    /**
     * 获取每页记录数
     *
     * @return 每页大小
     */
    @Override
    public int getSize() {
        return this.pageQuery.getSize();
    }

    /**
     * 获取排序信息
     *
     * @return 排序对象
     */
    @Override
    public Sort getSort() {
        return this.pageQuery.toPageRequest().getSort();
    }

    /**
     * 获取总元素数（同 {@link #getTotal()}）
     *
     * @return 总元素数
     */
    @Override
    public long getTotalElements() {
        return this.getTotal();
    }

    /**
     * 获取总页数
     *
     * @return 总页数，若每页大小为 0 则返回 0
     */
    @Override
    public int getTotalPages() {
        return this.getSize() == 0 ? 0 : (int)Math.ceil((double)this.total / (double)this.getSize());
    }

    /**
     * 判断是否存在下一页
     *
     * @return 如果存在下一页返回 true，否则返回 false
     */
    @Override
    public boolean hasNext() {
        return this.getNumber() < this.getTotalPages();
    }

    /**
     * 判断是否存在上一页
     *
     * @return 如果存在上一页返回 true，否则返回 false
     */
    @Override
    public boolean hasPrevious() {
        return (this.getNumber() > PageQuery.FIRST_PAGE) && (this.getNumber() <= this.getTotalPages() + 1);
    }

    /**
     * 判断是否为第一页
     *
     * @return 如果是第一页返回 true，否则返回 false
     */
    @Override
    public boolean isFirst() {
        return this.getNumber() == PageQuery.FIRST_PAGE;
    }

    /**
     * 判断是否为最后一页
     *
     * @return 如果是最后一页返回 true，否则返回 false
     */
    @Override
    public boolean isLast() {
        return !this.hasNext();
    }

    /**
     * 获取当前页的数据内容（不可变列表）
     *
     * @return 当前页数据列表
     */
    @Override
    public List<T> getContent() {
        return Collections.unmodifiableList(this.content);
    }

    /**
     * 判断当前页是否有数据
     *
     * @return 如果有数据返回 true，否则返回 false
     */
    @Override
    public boolean hasContent() {
        return !this.content.isEmpty();
    }

    /**
     * 获取当前页实际包含的元素数量
     *
     * @return 当前页元素数量
     */
    @Override
    public int getNumberOfElements() {
        return this.content.size();
    }

    /**
     * 获取当前页数据的迭代器
     *
     * @return 数据迭代器
     */
    @Override
    public Iterator<T> iterator() {
        return this.content.iterator();
    }

    /**
     * 获取分页参数对象
     *
     * @return {@link Pageable} 对象
     */
    @Override
    public Pageable getPageable() {
        return this.pageQuery.toPageRequest();
    }

    /**
     * 获取下一页的分页参数
     *
     * @return 下一页的 {@link Pageable}，若无下一页则返回未分页状态
     */
    @Override
    public Pageable nextPageable() {
        return hasNext() ? this.pageQuery.toPageRequest().next() : Pageable.unpaged();
    }

    /**
     * 获取上一页的分页参数
     *
     * @return 上一页的 {@link Pageable}，若无上一页则返回未分页状态
     */
    @Override
    public Pageable previousPageable() {
        return hasPrevious() ? this.pageQuery.toPageRequest().previousOrFirst() : Pageable.unpaged();
    }

    /**
     * 将当前分页内容映射为另一种类型
     *
     * @param converter 转换函数
     * @param <U>       目标类型
     * @return 转换后的新分页对象
     */
    @Override
    public <U> Page<U> map(@NonNull Function<? super T, ? extends U> converter) {
        return new org.springframework.data.domain.PageImpl(this.getConvertedContent(converter), this.getPageable(),
            this.total);
    }

    /**
     * 使用转换函数处理当前页内容
     *
     * @param converter 转换函数
     * @param <U>       目标类型
     * @return 转换后的内容列表
     */
    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {
        Assert.notNull(converter, "Function must not be null!");
        return this.stream().map(converter::apply).collect(Collectors.toList());
    }

    /**
     * 返回分页对象的字符串表示
     *
     * @return 格式化的分页信息字符串
     */
    @Override
    public String toString() {
        String contentType = "UNKNOWN";
        List<T> content = this.getContent();
        if (CollectionUtil.isNotEmpty(content)) {
            contentType = content.get(0).getClass().getName();
        }

        return String.format("Page %s of %d containing %s instances", this.getNumber() + 1, this.getTotalPages(),
            contentType);
    }

    /**
     * 比较两个分页对象是否相等
     *
     * @param obj 比较对象
     * @return 如果相等返回 true，否则返回 false
     */
    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) {
            return true;
        } else if (!(obj instanceof org.springframework.data.domain.PageImpl)) {
            return false;
        } else {
            PageImpl<?> that = (PageImpl)obj;
            return this.total == that.total && super.equals(obj);
        }
    }

    /**
     * 计算哈希值
     *
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        int result = 17;
        result += 31 * Long.hashCode(this.total);
        result += 31 * super.hashCode();
        return result;
    }

    /**
     * 创建空的分页对象
     *
     * @param <T> 数据类型
     * @return 空分页对象
     */
    public static <T> PageImpl<T> of() {
        return of(new ArrayList<>(), PageQuery.FIRST_PAGE, PageQuery.DEFAULT_SIZE, 0);
    }

    /**
     * 根据参数创建分页对象
     *
     * @param content 数据列表
     * @param page    页码（从 1 开始）
     * @param size    每页大小
     * @param total   总记录数
     * @param <T>     数据类型
     * @return 分页对象
     */
    public static <T> PageImpl<T> of(List<T> content, int page, int size, long total) {
        return of(content, PageQuery.of(page, size), total);
    }

    /**
     * 根据分页查询参数创建分页对象（总记录数自动计算）
     *
     * @param content   数据列表
     * @param pageQuery 分页查询参数
     * @param <T>       数据类型
     * @return 分页对象
     */
    public static <T> PageImpl<T> of(List<T> content, PageQuery pageQuery) {
        return of(content, pageQuery, 0L);
    }

    /**
     * 根据分页查询参数创建分页对象
     *
     * @param content   数据列表
     * @param pageQuery 分页查询参数
     * @param total     总记录数
     * @param <T>       数据类型
     * @return 分页对象
     */
    public static <T> PageImpl<T> of(List<T> content, PageQuery pageQuery, long total) {
        // 适配处理：PageQuery page 从 1 开始，而 org.springframework.data.domain.PageImpl 的 page 从 0 开始
        PageRequest pageRequest =
            PageRequest.of(pageQuery.getPage() > 0 ? pageQuery.getPage() - 1 : 0, pageQuery.getSize());
        return new PageImpl<>(content, pageRequest, total);
    }

    /**
     * 根据 Spring Data Pageable 创建分页对象（总记录数自动计算）
     *
     * @param content  数据列表
     * @param pageable 分页参数
     * @param <T>      数据类型
     * @return 分页对象
     */
    public static <T> PageImpl<T> of(List<T> content, Pageable pageable) {
        return of(content, pageable, 0L);
    }

    /**
     * 根据 Spring Data Pageable 创建分页对象
     *
     * @param content  数据列表
     * @param pageable 分页参数
     * @param total    总记录数
     * @param <T>      数据类型
     * @return 分页对象
     */
    public static <T> PageImpl<T> of(List<T> content, Pageable pageable, long total) {
        return new PageImpl<>(content, PageRequest.of(pageable.getPageNumber(), pageable.getPageSize()), total);
    }

    /**
     * 将其他分页对象转换为本类实例
     *
     * @param page 原始分页对象
     * @param <T>  数据类型
     * @return 转换后的 {@link PageImpl} 对象
     */
    public static <T> PageImpl<T> of(Page<T> page) {
        if (page instanceof PageImpl) {
            return (PageImpl<T>)page;
        }

        PageRequest pageRequest = PageRequest.of(page.getNumber(), page.getSize(), page.getSort());
        return new PageImpl<>(page.getContent(), pageRequest, page.getTotalElements());
    }

}
