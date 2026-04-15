package io.github.dunwu.common.log.service.impl;

import io.github.dunwu.common.log.service.TableColumnConfigService;

import java.util.HashSet;
import java.util.Set;

/**
 * 表字段配置服务默认实现
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-13
 */
public class DefaultTableColumnConfigServiceImpl implements TableColumnConfigService {

    /**
     * 获取锁定的列
     *
     * @param tableName 表名
     * @return 锁定的列集合
     */
    @Override
    public Set<String> getLockedColumns(String tableName) {
        return new HashSet<>(0);
    }

}
