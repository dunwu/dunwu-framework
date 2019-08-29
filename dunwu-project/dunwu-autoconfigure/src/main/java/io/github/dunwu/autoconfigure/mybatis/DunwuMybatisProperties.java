package io.github.dunwu.autoconfigure.mybatis;

import lombok.Data;
import lombok.ToString;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Spring Boot 集成 MyBatis-Plus 配置属性
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-16
 */
@Data
@ToString
@ConfigurationProperties(prefix = "spring.mybatis")
public class DunwuMybatisProperties {
    private Boolean blockAttackEnabled = true;
}
