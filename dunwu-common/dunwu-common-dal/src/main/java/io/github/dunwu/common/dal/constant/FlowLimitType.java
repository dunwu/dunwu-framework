package io.github.dunwu.common.dal.constant;

/**
 * 流量控制类型枚举
 * <p>
 * 定义了流量控制策略的维度，如基于自定义 Key 或基于客户端 IP 地址。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-25
 */
public enum FlowLimitType {
    /**
     * 自定义维度流控
     * <p>
     * 根据业务自定义的 Key（如用户 ID、租户 ID 等）进行限流
     */
    CUSTOMER,
    /**
     * 基于 IP 地址流控
     * <p>
     * 根据请求来源的客户端 IP 地址进行限流
     */
    IP
}
