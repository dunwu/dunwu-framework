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
@ConfigurationProperties(prefix = "dunwu.mybatis")
public class DunwuMybatisProperties {

    /**
     * 攻击 SQL 阻断解析器开关
     */
    private boolean blockAttackEnabled = true;

    /**
     * 乐观锁开关
     */
    private boolean optimisticLockerEnabled = true;

}
