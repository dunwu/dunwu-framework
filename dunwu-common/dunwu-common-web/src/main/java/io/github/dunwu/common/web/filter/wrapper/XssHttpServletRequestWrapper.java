package io.github.dunwu.common.web.filter.wrapper;

import cn.hutool.core.util.StrUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.safety.Safelist;

import java.util.stream.IntStream;

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
    private static final Safelist WHITE_LIST = Safelist.basicWithImages();

    /**
     * 配置过滤化参数,不对代码进行格式化
     */
    private static final Document.OutputSettings OUTPUT_SETTING = new Document.OutputSettings().prettyPrint(false);

    /** 是否包含富文本内容 */
    private boolean isIncludeRichText;

    /**
     * 构造方法
     *
     * @param request           HTTP 请求
     * @param isIncludeRichText 是否包含富文本内容
     */
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
        if (StrUtil.isNotBlank(value)) {
            value = clean(value);
        }
        return value;
    }

    /**
     * 获取参数值数组，并进行 XSS 过滤
     *
     * @param name 参数名
     * @return 过滤后的参数值数组
     */
    @Override
    public String[] getParameterValues(String name) {
        String[] arr = super.getParameterValues(name);
        if (arr != null) {
            IntStream.range(0, arr.length).forEach(i -> arr[i] = clean(arr[i]));
        }
        return arr;
    }

    /**
     * 使用 Jsoup 清理内容，防止 XSS 攻击
     *
     * @param content 待清理的内容
     * @return 清理后的内容
     */
    private String clean(String content) {
        return Jsoup.clean(content, StrUtil.EMPTY, WHITE_LIST, OUTPUT_SETTING);
    }

    /**
     * 覆盖getHeader方法，将参数名和参数值都做 xss过滤 如果需要获得原始的值，则通过super.getHeaders(name)来获取 getHeaderNames 也可能需要覆盖
     */
    @Override
    public String getHeader(String name) {
        name = clean(name);
        String value = super.getHeader(name);
        if (StrUtil.isNotBlank(value)) {
            value = clean(value);
        }
        return value;
    }

}
