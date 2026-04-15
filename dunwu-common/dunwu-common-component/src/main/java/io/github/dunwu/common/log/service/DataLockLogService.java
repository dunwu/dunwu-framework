package io.github.dunwu.common.log.service;

import io.github.dunwu.common.log.domain.DataLockLogRecord;

import java.util.Collection;

/**
 * 数据锁定日志服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-29
 */
public interface DataLockLogService {

    /**
     * 批量保存日志
     *
     * @param records 日志记录
     * @return 是否保存成功
     */
    boolean batchSave(Collection<DataLockLogRecord> records);

}
