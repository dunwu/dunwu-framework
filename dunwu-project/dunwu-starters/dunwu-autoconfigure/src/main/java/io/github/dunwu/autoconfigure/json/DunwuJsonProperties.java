package io.github.dunwu.autoconfigure.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-16
 */
@Data
@ToString
@ConfigurationProperties(prefix = "dunwu.json")
public class DunwuJsonProperties {

    private final Jackson jackson = new Jackson();

    private Type type = Type.Jackson;

    enum Type {
        Jackson,
        Fastjon
    }

    @Data
    public static class Jackson {

        // 通过该方法对mapper对象进行设置，所有序列化的对象都将按改规则进行系列化
        // Include.Include.ALWAYS 默认
        // Include.NON_DEFAULT 属性为默认值不序列化
        // Include.NON_EMPTY 属性为 空（""） 或者为 NULL 都不序列化，则返回的json是没有这个字段的。这样对移动端会更省流量
        // Include.NON_NULL 属性为NULL 不序列化
        private JsonInclude.Include include = JsonInclude.Include.NON_EMPTY;

        /**
         * 是否允许出现单引号
         */
        private boolean allowSingleQuotes = true;

    }

}
