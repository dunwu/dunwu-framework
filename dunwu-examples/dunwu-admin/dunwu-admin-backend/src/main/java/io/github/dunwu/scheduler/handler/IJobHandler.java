package io.github.dunwu.scheduler.handler;

import io.github.dunwu.data.core.BaseResult;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-04
 */
public interface IJobHandler {

    BaseResult execute(String params) throws Exception;

}
