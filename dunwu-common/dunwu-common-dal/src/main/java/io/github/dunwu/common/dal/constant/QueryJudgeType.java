package io.github.dunwu.common.dal.constant;

/**
 * 查询条件判断类型枚举
 * <p>
 * 用于定义动态查询构建时的条件判断逻辑，例如等于、模糊匹配、包含、非空等。
 * 通常配合 {@link io.github.dunwu.common.dal.annotation.QueryField} 注解使用。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
public enum QueryJudgeType {
    /**
     * 等于 (Equals)
     */
    EQUALS,
    /**
     * 不等于 (Not Equals)
     */
    NOT_EQUALS,
    /**
     * 模糊匹配 (Like)
     * <p>
     * 通常对应 SQL 中的 {@code LIKE '%value%'}
     */
    LIKE,
    /**
     * 不匹配 (Not Like)
     * <p>
     * 通常对应 SQL 中的 {@code NOT LIKE '%value%'}
     */
    NOT_LIKE,
    /**
     * 在集合中 (In)
     * <p>
     * 通常对应 SQL 中的 {@code IN (...)}
     */
    IN,
    /**
     * 不在集合中 (Not In)
     * <p>
     * 通常对应 SQL 中的 {@code NOT IN (...)}
     */
    NOT_IN,
    /**
     * 为空 (Is Null)
     * <p>
     * 通常对应 SQL 中的 {@code IS NULL}
     */
    IS_NULL,
    /**
     * 不为空 (Is Not Null)
     * <p>
     * 通常对应 SQL 中的 {@code IS NOT NULL}
     */
    IS_NOT_NULL
}
