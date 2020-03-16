package io.github.dunwu.autoconfigure.web;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * dunwu-web 配置属性
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-16
 */
@Data
@ToString
@ConfigurationProperties(prefix = "dunwu.web")
public class DunwuWebProperties {

    /**
     * Dunwu Web 扩展配置开启
     */
    private boolean enabled = true;

    /**
     * 打印 Http 请求应答 Debug 信息开关
     */
    private boolean httpDebugEnabled = true;

    /**
     * http 数据自动格式化开关
     */
    private boolean formatEnabled = true;

    /**
     * web 安全配置
     */
    private final Security security = new Security();

    @Data
    @ToString
    public static class Security {

        private boolean enabled = true;

        /**
         * 放开跨域限制开关
         */
        private boolean corsEnabled = true;

        /**
         * 允许跨域的路径
         */
        private String corsPath;

        private String authTokenKey = "token";

    }

}
