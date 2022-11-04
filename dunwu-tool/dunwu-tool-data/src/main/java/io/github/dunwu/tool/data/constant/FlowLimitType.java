package io.github.dunwu.tool.data.constant;

/**
 * 流控类型枚举
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-25
 */
public enum FlowLimitType {
    /**
     * 传统类型
     */
    CUSTOMER,
    /**
     * 根据 IP地址限制
     */
    IP
}
