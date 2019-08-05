package io.github.dunwu.core;

import lombok.*;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Data
@ToString(callSuper = true)
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class PageResult<T> extends BaseResult {

    private static final long serialVersionUID = 1L;

    private PageData data;

    public PageResult() {}

    public PageResult(BaseResult result) {
        super(result);
        this.data = null;
    }

    public PageResult(IAppCode appCode) {
        super(appCode);
    }

    public PageResult(PageData data, Boolean success, String code, String message) {
        super(success, code, message);
        this.data = data;
    }

    public PageResult(PageData data, Boolean success, String code, List<String> messages) {
        super(success, code, messages);
        this.data = data;
    }

    public PageResult(Collection<T> list, Page page, Boolean success, String code, String message) {
        super(success, code, message);
        data = new PageData();
        data.setList(list);
        data.setPage(page);
    }

    public PageResult(Collection<T> list, Page page, Boolean success, String code, String message, Object... params) {
        super(success, code, message, params);
        data = new PageData();
        data.setList(list);
        data.setPage(page);
    }

    public PageResult(Collection<T> list, Page page, Boolean success, String code, List<String> messages) {
        super(success, code, messages);
        data = new PageData();
        data.setList(list);
        data.setPage(page);
    }

    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public class PageData implements Serializable {

        private static final long serialVersionUID = 1L;

        /**
         * 当前查询页的数据列表
         */
        protected Collection<T> list;

        /**
         * 分页信息
         */
        private Page page;
    }


    /**
     * 分页信息
     */
    @Data
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Page implements Serializable {

        private static final long serialVersionUID = 1L;

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

        /**
         * 总页数
         */
        private Long pages = 0L;
    }
}
