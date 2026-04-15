package io.github.dunwu.common.web.jsp;

import jakarta.servlet.jsp.JspException;
import jakarta.servlet.jsp.tagext.TagSupport;

import java.io.IOException;

/**
 * JSP Block 标签，用于定义可被覆盖的 JSP 内容块
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-05
 */
public class BlockTag extends TagSupport {

    private static final long serialVersionUID = 5981092740359798010L;

    /** 覆盖内容的前缀标识 */
    private static final String BLOCK = "__jsp_override__";

    /** 块名称 */
    protected String name;

    /**
     * 处理标签开始，判断是否需要覆盖内容
     *
     * @return 标签体处理标识
     */
    @Override
    public int doStartTag() {
        return getOverriedContent() == null ? EVAL_BODY_INCLUDE : SKIP_BODY;
    }

    /**
     * 处理标签结束，输出覆盖后的内容
     *
     * @return 页面后续处理标识
     * @throws JspException 如果输出失败
     */
    @Override
    public int doEndTag() throws JspException {
        String overriedContent = getOverriedContent();
        if (overriedContent == null) {
            return EVAL_PAGE;
        }

        try {
            pageContext.getOut().write(overriedContent);
        } catch (IOException e) {
            throw new JspException("try to override jsp content failed, block name:" + name, e);
        }
        return EVAL_PAGE;
    }

    /**
     * 获取被覆盖的内容
     *
     * @return 覆盖后的内容字符串
     */
    private String getOverriedContent() {
        String newName = BLOCK + name;
        return (String)pageContext.getRequest().getAttribute(newName);
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
