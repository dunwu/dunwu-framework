package io.github.dunwu.dlock.db;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-23
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class LockDTO implements Serializable {

    private static final long serialVersionUID = 1978465348204002240L;

    private Long id;
    private String key;
    private Long time;
    private TimeUnit timeUnit;
    private Long expire;
}
