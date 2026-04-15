package io.github.dunwu.common.dal.domain;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.common.constant.CodeMsg;
import io.github.dunwu.common.constant.ResultCode;
import io.github.dunwu.common.exception.DefaultException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serial;
import java.io.Serializable;
import java.util.Collection;
import java.util.Objects;

/**
 * 统一应答消息实体
 * <p>
 * 用于封装后端接口的返回结果，包含状态码、响应信息。所有 REST 接口或 RPC 调用的返回值都应继承或包装此类，以保证返回格式的统一性。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-21
 */
@Getter
@Setter
@ToString
@Accessors(chain = true)
public class Result implements CodeMsg, Serializable {

    /**
     * 序列化版本号
     */
    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 状态码
     * <p>
     * 通常 "0" 或 {@link ResultCode#SUCCESS} 的 code 表示成功，其他值表示失败。
     */
    protected String code;

    /**
     * 响应信息
     * <p>
     * 通常用于开发者调试、日志记录或详细的错误描述。
     */
    protected String msg;

    /**
     * 默认构造方法
     * <p>
     * 初始化为成功状态 ({@link ResultCode#SUCCESS})
     */
    public Result() {
        this(ResultCode.SUCCESS);
    }

    /**
     * 根据 {@link CodeMsg} 构造 {@link Result}
     *
     * @param codeMsg 应答状态详情对象
     */
    public Result(final CodeMsg codeMsg) {
        if (codeMsg == null) {
            throw new DefaultException(ResultCode.PARAM_ERROR, "参数不能为 null！");
        }
        this.code = codeMsg.getCode();
        this.msg = codeMsg.getMsg();
    }

    /**
     * 根据 {@link CodeMsg}、响应信息构造
     *
     * @param codeMsg 应答状态详情对象
     * @param msg     自定义响应信息
     */
    public Result(final CodeMsg codeMsg, final String msg) {
        this(codeMsg.getCode(), msg);
    }

    /**
     * 根据状态码及消息集合构造
     *
     * @param code     状态码
     * @param messages 消息集合
     */
    public Result(final String code, final Collection<String> messages) {
        this(code, messages == null ? null : CollectionUtil.join(messages, ","));
    }

    /**
     * 全参构造方法
     *
     * @param code 状态码
     * @param msg  响应信息
     */
    public Result(final String code, final String msg) {
        if (code == null) {
            throw new DefaultException(ResultCode.PARAM_ERROR, "状态码不能为 null！");
        }
        this.code = code;
        this.msg = msg;
    }

    /**
     * 判断当前对象与指定对象是否相等
     * <p>
     * 比较规则：code 和 msg 均相同则视为相等
     *
     * @param o 待比较对象
     * @return 如果相等返回 true，否则返回 false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Result)) {
            return false;
        }
        Result result = (Result)o;
        return Objects.equals(code, result.code) && Objects.equals(msg, result.msg);
    }

    /**
     * 计算哈希码
     *
     * @return 基于 code 和 msg 的哈希值
     */
    @Override
    public int hashCode() {
        return Objects.hash(code, msg);
    }

    /**
     * 判断结果是否成功
     *
     * @return 如果状态码为成功代码返回 true，否则返回 false
     */
    public boolean isOk() {
        return ResultCode.SUCCESS.getCode().equals(this.code);
    }

    /**
     * 判断结果是否失败
     *
     * @return 如果状态码不是成功代码返回 true，否则返回 false
     */
    public boolean isFail() {
        return !isOk();
    }

    /**
     * 获取响应数据
     * <p>
     * 基础 Result 类不支持携带数据，调用此方法将抛出 {@link DefaultException}。 子类（如 {@link DataResult}）应重写此方法以返回实际数据。
     *
     * @return 响应数据
     * @throws DefaultException 始终抛出，提示不支持该操作
     */
    public Object getData() {
        throw new DefaultException(ResultCode.NOT_SUPPORT);
    }

}
