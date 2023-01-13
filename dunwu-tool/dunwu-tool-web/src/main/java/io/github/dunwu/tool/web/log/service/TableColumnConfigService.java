package io.github.dunwu.tool.web.log.service;

import java.util.Set;

/**
 * 表字段配置服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2023-01-13
 */
public interface TableColumnConfigService {

    /**
     * 返回表字段配置中被锁定的字段集合
     *
     * @param tableName 表名
     * @return /
     */
    Set<String> getLockedColumns(String tableName);

}
