package io.github.dunwu.tool.web.log.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 方法执行结果实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-29
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExecuteResult {

    private boolean success;
    private String error;
    private Throwable throwable;

}
