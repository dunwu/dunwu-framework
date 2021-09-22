package io.github.dunwu.tool.data.core;

import io.github.dunwu.tool.data.core.constant.ResultStatus;
import io.github.dunwu.tool.data.core.constant.Status;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 含有响应数据分页信息的应答消息实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-06
 */
@Data
@ToString
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
public class PageResult<T> extends BaseResult {

    private static final long serialVersionUID = 1L;

    /**
     * 应答数据实体
     */
    private Page<T> data;

    /**
     * 构造成功的 {@link PageResult}
     *
     * @param data 被分页实体 {@link Page} 包装的应答数据对象
     */
    public PageResult(Page<T> data) {
        super(ResultStatus.OK);
        this.data = data;
    }

    /**
     * 根据 {@link Status} 构造 {@link PageResult}
     *
     * @param status {@link Status}（应答状态）
     */
    public PageResult(final Status status) {
        super(status);
    }

    /**
     * 根据另一个 {@link PageResult} 构造 {@link PageResult}
     *
     * @param result {@link PageResult}
     */
    public PageResult(final PageResult<T> result) {
        super(result);
    }

    /**
     * 构造 {@link PageResult}
     *
     * @param code    状态码 {@link Status}
     * @param message 响应状态消息
     */
    public PageResult(final int code, final String message) {
        super(code, message);
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，构造 {@link PageResult}
     *
     * @param code     响应状态错误码
     * @param template 响应状态消息模板
     * @param params   响应状态消息参数
     */
    public PageResult(final int code, final String template, Object... params) {
        super(code, template, params);
    }

    /**
     * 构造 {@link PageResult}
     *
     * @param code     响应状态错误码
     * @param messages 响应状态消息列表
     */
    public PageResult(final int code, final List<String> messages) {
        super(code, messages);
    }

    /**
     * 根据模板字符串以及参数，组装响应消息，返回成功的 {@link PageResult}
     *
     * @param data 被分页实体 {@link Page} 包装的应答数据对象
     * @param <T>  数据类型
     * @return {@link PageResult}
     */
    public static <T> PageResult<T> ok(Page<T> data) {
        return new PageResult<>(data);
    }

}
