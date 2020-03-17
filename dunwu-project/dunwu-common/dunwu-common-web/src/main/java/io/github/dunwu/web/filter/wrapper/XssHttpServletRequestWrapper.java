package io.github.dunwu.web.filter.wrapper;

import io.github.dunwu.tool.util.StringUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Whitelist;

import java.util.stream.IntStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

/**
 * 利用 jsoup 清理 http 请求中可能存在的 xss 攻击
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
public class XssHttpServletRequestWrapper extends HttpServletRequestWrapper {

    /**
     * html 白名单元素
     */
    private static final Whitelist WHITE_LIST = Whitelist.basicWithImages();

    /**
     * 配置过滤化参数,不对代码进行格式化
     */
    private static final Document.OutputSettings OUTPUT_SETTING = new Document.OutputSettings().prettyPrint(false);

    private boolean isIncludeRichText;

    public XssHttpServletRequestWrapper(HttpServletRequest request, boolean isIncludeRichText) {
        super(request);
        this.isIncludeRichText = isIncludeRichText;
    }

    /**
     * 覆盖 getParameter方法，将参数名和参数值都做xss过滤 如果需要获得原始的值，则通过 super.getParameterValues(name)来获取
     * getParameterNames,getParameterValues和 getParameterMap也可能需要覆盖
     */
    @Override
    public String getParameter(String name) {
        if (("content".equals(name) || name.endsWith("WithHtml")) && !isIncludeRichText) {
            return super.getParameter(name);
        }
        name = clean(name);
        String value = super.getParameter(name);
        if (StringUtil.isNotBlank(value)) {
            value = clean(value);
        }
        return value;
    }

    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        if (arr != null) {
            IntStream.range(0, arr.length).forEach(i -> arr[i] = clean(arr[i]));
        }
        return arr;
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做 xss过滤 如果需要获得原始的值，则通过super.getHeaders(name)来获取 getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        name = clean(name);
        String value = super.getHeader(name);
        if (StringUtil.isNotBlank(value)) {
            value = clean(value);
        }
        return value;
    }

    private String clean(String content) {
        return Jsoup.clean(content, StringUtil.EMPTY, WHITE_LIST, OUTPUT_SETTING);
    }

}
