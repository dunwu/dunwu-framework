package io.github.dunwu.common.log.service;

import io.github.dunwu.common.log.domain.OperationLogRecord;

/**
 * 操作日志存储接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-29
 */
public interface OperationLogService {

    /**
     * 保存日志
     *
     * @param record 日志记录
     * @return true / false
     */
    boolean store(OperationLogRecord record);

}
