package io.github.dunwu.data.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-15
 */
@Data
public class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    protected Long id;
}
