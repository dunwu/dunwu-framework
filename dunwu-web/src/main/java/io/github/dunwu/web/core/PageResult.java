package io.github.dunwu.web.core;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Zhang Peng
 * @since 2019-04-11
 */
public class PageResult<T> extends BaseResult implements Serializable {

    private static final long serialVersionUID = -3393865095117099214L;

    /**
     * 当前查询页的数据列表
     */
    private List<T> list;

    /**
     * 分页信息
     */
    private Pagination pagination;

    public PageResult() {
        list = new LinkedList<>();
    }

    public PageResult(Boolean success, Integer code, String msg, List<T> list, Pagination pagination) {
        super(success, code, msg);
        this.list = list;
        this.pagination = pagination;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Pagination getPagination() {
        return pagination;
    }

    public void setPagination(Pagination pagination) {
        this.pagination = pagination;
    }

    @Override
    public String toString() {
        return "PageResult{" + "list=" + list + ", pagination=" + pagination + ", success=" + success + ", code=" + code
            + ", msg='" + msg + '\'' + '}';
    }

    public static class Pagination implements Serializable {

        private static final long serialVersionUID = 3446302974711251406L;

        /**
         * 当前查询页
         */
        private Integer index = 1;

        /**
         * 每页展示记录数
         */
        private Integer page = 10;

        /**
         * 总记录数
         */
        private Long total = 0L;

        public Pagination() {}

        public Pagination(Integer index, Integer page, Long total) {
            this.index = index;
            this.page = page;
            this.total = total;
        }

        public Integer getIndex() {
            return index;
        }

        public void setIndex(Integer index) {
            this.index = index;
        }

        public Integer getPage() {
            return page;
        }

        public void setPage(Integer page) {
            this.page = page;
        }

        public Long getTotal() {
            return total;
        }

        public void setTotal(Long total) {
            this.total = total;
        }

        @Override
        public String toString() {
            return "Pagination{" + "index=" + index + ", page=" + page + ", total=" + total + '}';
        }
    }
}
