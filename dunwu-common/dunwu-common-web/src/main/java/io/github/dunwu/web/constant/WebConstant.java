package io.github.dunwu.web.constant;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-29
 */
public interface WebConstant {

    String HTTP_METHOD_OPTIONS = "OPTIONS";

    String PATH_SEPARATOR = "/";

    enum ResponseType {

        HTTP_REPONSE,
        MODEL_AND_VIEW
    }

    enum BrowserType {

        firefox,
        chrome,
        safari,
        msie,
        opera
    }

}
