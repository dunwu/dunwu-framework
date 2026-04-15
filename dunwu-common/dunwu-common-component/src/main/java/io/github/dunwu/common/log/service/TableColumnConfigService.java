package io.github.dunwu.common.log.service;

import java.util.Set;

/**
 * 表字段配置服务
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2023-01-13
 */
public interface TableColumnConfigService {

    /**
     * 获取指定表中被锁定的字段集合
     *
     * @param tableName 表名
     * @return 被锁定的字段名称集合
     */
    Set<String> getLockedColumns(String tableName);

}
