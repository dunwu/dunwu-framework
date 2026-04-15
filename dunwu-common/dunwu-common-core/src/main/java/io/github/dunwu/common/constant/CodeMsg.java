package io.github.dunwu.common.constant;

/**
 * 请求 / 应答状态接口
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
public interface CodeMsg {

    /**
     * 获取状态码
     *
     * @return 状态码
     */
    String getCode();

    /**
     * 获取响应信息
     *
     * @return 响应信息
     */
    String getMsg();

}
