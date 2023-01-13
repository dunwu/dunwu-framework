package io.github.dunwu.tool.web.log.service.impl;

import io.github.dunwu.tool.web.log.service.TableColumnConfigService;

import java.util.HashSet;
import java.util.Set;

/**
 * 表字段配置服务默认实现
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-01-13
 */
public class DefaultTableColumnConfigServiceImpl implements TableColumnConfigService {

    @Override
    public Set<String> getLockedColumns(String tableName) {
        return new HashSet<>(0);
    }

}
