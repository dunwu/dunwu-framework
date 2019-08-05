package io.github.dunwu.scheduler.dto;

import lombok.Data;
import lombok.ToString;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-01
 */
@Data
@ToString
public class BeanDTO {
    private String beanName;
    private Class<?> beanType;
}
