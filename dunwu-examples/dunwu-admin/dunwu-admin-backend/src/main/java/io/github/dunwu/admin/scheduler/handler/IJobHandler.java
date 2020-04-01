package io.github.dunwu.admin.scheduler.handler;

import io.github.dunwu.common.BaseResult;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-04
 */
public interface IJobHandler {

    BaseResult execute(String params) throws Exception;

}
