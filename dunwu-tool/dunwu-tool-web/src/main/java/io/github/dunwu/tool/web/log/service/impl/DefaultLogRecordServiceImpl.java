package io.github.dunwu.tool.web.log.service.impl;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.web.log.entity.LogRecord;
import io.github.dunwu.tool.web.log.service.LogRecordService;
import lombok.extern.slf4j.Slf4j;

/**
 * 操作日志存储接口默认实现类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-12-30
 */
@Slf4j
public class DefaultLogRecordServiceImpl implements LogRecordService {

    @Override
    public boolean store(LogRecord record) {
        log.info("【操作日志】{}", JSONUtil.toJsonStr(record));
        return true;
    }

}
