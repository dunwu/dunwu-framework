package io.github.dunwu.common.log.service.impl;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.common.log.domain.OperationLogRecord;
import io.github.dunwu.common.log.service.OperationLogService;
import lombok.extern.slf4j.Slf4j;

/**
 * 操作日志存储接口默认实现类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
@Slf4j
public class DefaultOperationLogServiceImpl implements OperationLogService {

    /**
     * 存储操作日志记录
     *
     * @param record 操作日志记录实体
     * @return true 表示存储成功
     */
    @Override
    public boolean store(OperationLogRecord record) {
        log.info("【操作日志】{}", JSONUtil.toJsonStr(record));
        return true;
    }

}
