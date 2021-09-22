package io.github.dunwu.tool.web.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.web.filter.wrapper.XssHttpServletRequestWrapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

/**
 * XSS 防御过滤器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
public class XssFilter implements Filter {

    private final transient Logger log = LoggerFactory.getLogger(this.getClass());

    /**
     * 是否过滤富文本内容
     */
    private boolean flag = false;

    private List<String> excludes = new ArrayList<>();

    @Override
    public void init(FilterConfig filterConfig) {
        if (log.isDebugEnabled()) {
            log.debug("开启 io.github.dunwu.web.filter.XssFilter");
        }
        String isIncludeRichText = filterConfig.getInitParameter("isIncludeRichText");
        if (StrUtil.isNotBlank(isIncludeRichText)) {
            flag = BooleanUtil.toBoolean(isIncludeRichText);
        }
        String temp = filterConfig.getInitParameter("excludes");
        if (temp != null) {
            String[] url = temp.split(",");
            excludes.addAll(Arrays.asList(url));
        }
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response,
        FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        if (handleExcludeUrl(req)) {
            chain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest) request, flag);
        chain.doFilter(xssRequest, response);
    }

    @Override
    public void destroy() {

    }

    private boolean handleExcludeUrl(HttpServletRequest request) {
        if (CollectionUtil.isEmpty(excludes)) {
            return false;
        }
        String url = request.getServletPath();
        return excludes.stream()
            .map(pattern -> Pattern.compile("^" + pattern))
            .map(p -> p.matcher(url))
            .anyMatch(Matcher::find);
    }

}
