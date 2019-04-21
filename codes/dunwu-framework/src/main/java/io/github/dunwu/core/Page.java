package io.github.dunwu.core;

/**
 * @author Zhang Peng
 * @since 2019-04-21
 */
public class Page {
    private static final long serialVersionUID = 3446302974711251406L;

    /**
     * 当前查询页
     */
    private Long current = 1L;

    /**
     * 每页展示记录数
     */
    private Long size = 10L;

    /**
     * 总记录数
     */
    private Long total = 0L;

    public Page() {}

    public Page(Long current, Long page, Long total) {
        this.current = current;
        this.size = page;
        this.total = total;
    }

    public Long getCurrent() {
        return current;
    }

    public void setCurrent(Long current) {
        this.current = current;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "Pagination{" + "current=" + current + ", size=" + size + ", total=" + total + '}';
    }
}
