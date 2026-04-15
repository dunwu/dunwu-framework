package io.github.dunwu.common.web.constant;

/**
 * Web 模块常量定义
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-29
 */
public final class WebConstant {

    /** HTTP OPTIONS 方法 */
    public static final String HTTP_METHOD_OPTIONS = "OPTIONS";

    /** 路径分隔符 */
    public static final String PATH_SEPARATOR = "/";

    private WebConstant() {
        throw new UnsupportedOperationException("Utility class, do not instantiate");
    }

    /** 响应类型枚举 */
    public enum ResponseType {

        /** HTTP 响应 */
        HTTP_RESPONSE,
        /** 模型和视图 */
        MODEL_AND_VIEW
    }

    /** 浏览器类型枚举 */
    public enum BrowserType {

        /** Firefox 浏览器 */
        FIREFOX,
        /** Chrome 浏览器 */
        CHROME,
        /** Safari 浏览器 */
        SAFARI,
        /** IE 浏览器 */
        MSIE,
        /** Opera 浏览器 */
        OPERA
    }

}
