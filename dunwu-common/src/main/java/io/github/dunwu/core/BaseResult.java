package io.github.dunwu.core;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-21
 */
@Data
@ToString
public class BaseResult implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 处理结果，默认为处理成功
     */
    protected Boolean success;

    /**
     * 错误码
     */
    protected String code;

    /**
     * 错误描述。
     */
    protected List<String> messages;

    public BaseResult() {
        this.messages = new LinkedList<>();
    }

    public BaseResult(IAppCode appCode) {
        if (IAppCode.SUCCESS_VALUE.equals(appCode.code())) {
            this.success = true;
        } else {
            this.success = false;
        }
        this.code = appCode.code();
        this.messages.addAll(Collections.singletonList(appCode.message()));
    }

    public BaseResult(BaseResult result) {
        this.success = result.getSuccess();
        this.code = result.getCode();
        this.messages = result.getMessages();
    }

    public BaseResult(Boolean success, String code, String... messages) {
        this.success = success;
        this.code = code;
        this.messages.addAll(Arrays.asList(messages));
    }
}
