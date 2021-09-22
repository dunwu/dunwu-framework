package io.github.dunwu.tool.data.core;

import lombok.Getter;
import lombok.ToString;
import org.springframework.data.domain.*;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@ToString
public class Pagination<T> extends ListContent<T> implements Page<T> {

    @Getter
    private final Pageable pageable;

    public Pagination() {
        this.pageable = PageRequest.of(1, 10);
    }

    public Pagination(List<T> content, Pageable pageable, long totalElements) {
        this.content.addAll(content);
        this.pageable = pageable;
        this.totalElements = totalElements;
    }

    public Pagination(List<T> content, int number, int size, long totalElements) {
        this.content.addAll(content);
        this.pageable = PageRequest.of(number, size);
        this.totalElements = totalElements;
    }

    @Override
    public int getNumber() {
        return pageable.isPaged() ? pageable.getPageNumber() : 0;
    }

    @Override
    public int getSize() {
        return pageable.isPaged() ? pageable.getPageSize() : content.size();
    }

    @Override
    public boolean hasPrevious() {
        return getNumber() > 0;
    }

    @Override
    public boolean isFirst() {
        return !hasPrevious();
    }

    @Override
    public boolean isLast() {
        return !hasNext();
    }

    @Override
    public boolean hasNext() {
        return getNumber() + 1 < getTotalPages();
    }

    @Override
    public Pageable nextPageable() {
        return hasNext() ? pageable.next() : Pageable.unpaged();
    }

    @Override
    public Pageable previousPageable() {
        return hasPrevious() ? pageable.previousOrFirst() : Pageable.unpaged();
    }

    @Override
    public Sort getSort() {
        return pageable.getSort();
    }

    @Override
    public Iterator<T> iterator() {
        return content.iterator();
    }

    @Override
    public int getTotalPages() {
        return getSize() == 0 ? 1 : (int) Math.ceil((double) totalElements / (double) getSize());
    }

    @Override
    public <U> Page<U> map(Function<? super T, ? extends U> converter) {
        return new PageImpl<>(getConvertedContent(converter), getPageable(), totalElements);
    }

    protected <U> List<U> getConvertedContent(Function<? super T, ? extends U> converter) {

        Assert.notNull(converter, "Function must not be null!");

        return this.stream().map(converter::apply).collect(Collectors.toList());
    }

}
