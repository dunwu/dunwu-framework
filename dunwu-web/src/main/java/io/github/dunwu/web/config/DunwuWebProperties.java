package io.github.dunwu.web.config;

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
     * 总开关
     */
    private Boolean enabled = true;

    /**
     * 打印 Http 请求应答 Debug 信息开关
     */
    private Boolean httpDebugEnable = true;

    /**
     * 放开跨域限制开关
     */
    private Boolean corsEnable = true;

    /**
     * http 数据自动格式化开关
     */
    private Boolean formatEnable = true;
}
