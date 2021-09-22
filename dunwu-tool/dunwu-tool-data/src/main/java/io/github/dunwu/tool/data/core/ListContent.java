package io.github.dunwu.tool.data.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-28
 */
public class ListContent<T> {

    protected long totalElements;
    protected List<T> content = new ArrayList<>();

    public ListContent() {}

    public ListContent(List<T> content) {
        this.content = content;
        this.totalElements = content.size();
    }

    public ListContent(List<T> content, long totalElements) {
        this.content = content;
        this.totalElements = totalElements;
    }

    public int getNumberOfElements() {
        return content.size();
    }

    public long getTotalElements() {
        return totalElements;
    }

    public ListContent<T> setTotalElements(long totalElements) {
        this.totalElements = totalElements;
        return this;
    }

    public boolean hasContent() {
        return !content.isEmpty();
    }

    public List<T> getContent() {
        return Collections.unmodifiableList(content);
    }

    public Iterator<T> iterator() {
        return content.iterator();
    }

}
