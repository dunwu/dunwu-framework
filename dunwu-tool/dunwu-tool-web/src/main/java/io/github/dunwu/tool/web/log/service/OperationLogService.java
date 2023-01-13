package io.github.dunwu.tool.web.log.service;

import io.github.dunwu.tool.web.log.entity.OperationLogRecord;

/**
 * 操作日志存储接口
 *
 * @author peng.zhang
 * @date 2021-09-29
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
