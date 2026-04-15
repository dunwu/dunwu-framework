package io.github.dunwu.common.web.filter;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.common.web.filter.wrapper.XssHttpServletRequestWrapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * XSS 防御过滤器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
@Slf4j
public class XssFilter implements Filter {

    /**
     * 是否过滤富文本内容
     */
    private boolean flag = false;

    /** 排除的 URL 列表 */
    private List<String> excludes = new ArrayList<>();

    /**
     * 初始化过滤器配置
     *
     * @param filterConfig 过滤器配置对象
     */
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

    /**
     * 执行过滤逻辑，对请求进行 XSS 处理
     *
     * @param request  Servlet 请求
     * @param response Servlet 响应
     * @param chain    过滤器链
     * @throws IOException      如果发生 I/O 错误
     * @throws ServletException 如果发生 Servlet 异常
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        if (handleExcludeUrl(req)) {
            chain.doFilter(request, response);
            return;
        }
        XssHttpServletRequestWrapper xssRequest = new XssHttpServletRequestWrapper((HttpServletRequest)request, flag);
        chain.doFilter(xssRequest, response);
    }

    /**
     * 销毁过滤器资源
     */
    @Override
    public void destroy() {

    }

    /**
     * 判断当前请求 URL 是否在排除列表中
     *
     * @param request HTTP 请求
     * @return 如果在排除列表中返回 true，否则返回 false
     */
    private boolean handleExcludeUrl(HttpServletRequest request) {
        if (CollectionUtil.isEmpty(excludes)) {
            return false;
        }
        String url = request.getServletPath();
        return excludes.stream().map(pattern -> Pattern.compile("^" + pattern)).map(p -> p.matcher(url))
            .anyMatch(Matcher::find);
    }

}
