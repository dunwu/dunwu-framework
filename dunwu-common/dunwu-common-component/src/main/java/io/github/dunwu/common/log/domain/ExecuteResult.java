package io.github.dunwu.common.log.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 方法执行结果实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteResult {

    /** 是否执行成功 */
    private boolean success;

    /** 错误信息 */
    private String error;

    /** 异常对象 */
    private Throwable throwable;

}
