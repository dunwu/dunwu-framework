package io.github.dunwu.common.io.ansi;

/**
 * ANSI 元素接口，表示一个 ANSI 转义码元素
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-10-30
 */
public interface AnsiElement {

    /**
     * 获取 ANSI 转义码
     *
     * @return ANSI 转义码字符串
     */
    String toString();

}
