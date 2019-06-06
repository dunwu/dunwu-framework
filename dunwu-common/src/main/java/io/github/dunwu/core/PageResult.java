package io.github.dunwu.core;

import java.util.Collection;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
public class PageResult<T> extends BaseResult {

    private static final long serialVersionUID = -344119212693006365L;

    private PageData data;

    public PageResult(BaseResult result) {
        this.success = result.getSuccess();
        this.code = result.getCode();
        this.msg = result.getMsg();
        this.data = null;
    }

    public PageResult(Boolean success, String code, String msg, PageData data) {
        super(success, code, msg);
        this.data = data;
    }

    public PageResult(Boolean success, String code, String msg, Collection<T> list, Page page) {
        super(success, code, msg);
        data = new PageData();
        data.setList(list);
        data.setPage(page);
    }

    public PageData getData() {
        return data;
    }

    public void setData(PageData data) {
        this.data = data;
    }

    public class PageData {
        /**
         * 当前查询页的数据列表
         */
        protected Collection<T> list;

        /**
         * 分页信息
         */
        private Page page;

        public Collection<T> getList() {
            return list;
        }

        public void setList(Collection<T> list) {
            this.list = list;
        }

        public Page getPage() {
            return page;
        }

        public void setPage(Page page) {
            this.page = page;
        }
    }
}
