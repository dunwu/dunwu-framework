package io.github.dunwu.common.web.jsp;

import jakarta.servlet.jsp.tagext.BodyContent;
import jakarta.servlet.jsp.tagext.BodyTagSupport;

/**
 * JSP Override 标签，用于覆盖 BlockTag 定义的内容块
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-05
 */
public class OverrideTag extends BodyTagSupport {

    private static final long serialVersionUID = 1692569211455126528L;

    /** 覆盖内容的前缀标识 */
    private static final String BLOCK = "__jsp_override__";

    /** 块名称 */
    protected String name;

    /**
     * 处理标签开始，判断是否已被覆盖
     *
     * @return 标签体处理标识
     */
    @Override
    public int doStartTag() {
        return isOverrided() ? SKIP_BODY : EVAL_BODY_BUFFERED;
    }

    /**
     * 处理标签结束，将内容存储到请求属性中
     *
     * @return 页面后续处理标识
     */
    @Override
    public int doEndTag() {
        if (isOverrided()) {
            return EVAL_PAGE;
        }
        BodyContent b = getBodyContent();
        String newName = BLOCK + name;

        pageContext.getRequest().setAttribute(newName, b.getString());
        return EVAL_PAGE;
    }

    /**
     * 判断当前块是否已被覆盖
     *
     * @return 如果已被覆盖返回 true，否则返回 false
     */
    private boolean isOverrided() {
        String newName = BLOCK + name;
        return pageContext.getRequest().getAttribute(newName) != null;
    }

    /**
     * 设置块名称
     *
     * @param name 块名称
     */
    public void setName(String name) {
        this.name = name;
    }

}
