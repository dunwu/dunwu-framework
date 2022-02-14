package io.github.dunwu.autoconfigure.web;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Web Mvc 配置属性
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-16
 */
@Data
@ToString
@Accessors(chain = true)
@ConfigurationProperties(prefix = "dunwu.web")
public class DunwuWebProperties {

    /** Web 定制配置开启 */
    private boolean enabled = true;
    /** 打印 Http 请求应答 Debug 信息开关 */
    private boolean httpDebugEnabled = true;
    /** debug 路径 */
    private List<String> debugPath = Collections.singletonList("/**");
    /** http 数据自动格式化开关 */
    private boolean formatEnabled = true;
    /** WebSocket 开关 */
    private boolean websocketEnabled = false;
    /** 资源路径配置，key:映射路径,value:实际路径 */
    private Map<String, String> resources = new HashMap<>();

}
