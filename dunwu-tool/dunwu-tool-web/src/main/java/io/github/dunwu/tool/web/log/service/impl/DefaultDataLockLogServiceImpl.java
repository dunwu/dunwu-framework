package io.github.dunwu.tool.web.log.service.impl;

import cn.hutool.json.JSONUtil;
import io.github.dunwu.tool.web.log.entity.DataLockLogRecord;
import io.github.dunwu.tool.web.log.service.DataLockLogService;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;

/**
 * 默认数据锁定日志服务实现类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-29
 */
@Slf4j
public class DefaultDataLockLogServiceImpl implements DataLockLogService {

    @Override
    public boolean batchSave(Collection<DataLockLogRecord> records) {
        log.info("批量保存【数据锁定日志】{}", JSONUtil.toJsonStr(records));
        return true;
    }

}
