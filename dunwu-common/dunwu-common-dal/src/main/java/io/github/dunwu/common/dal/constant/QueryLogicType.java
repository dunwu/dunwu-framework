package io.github.dunwu.common.dal.constant;

/**
 * 查询条件逻辑连接符枚举
 * <p>
 * 用于定义多个查询条件之间的逻辑关系，如 AND（与）或 OR（或）。
 * 通常用于动态构建 SQL 或 Elasticsearch 查询语句。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
public enum QueryLogicType {
    /**
     * 逻辑与 (AND)
     * <p>
     * 表示所有条件必须同时满足
     */
    AND,
    /**
     * 逻辑或 (OR)
     * <p>
     * 表示只需满足其中任意一个条件
     */
    OR
}
