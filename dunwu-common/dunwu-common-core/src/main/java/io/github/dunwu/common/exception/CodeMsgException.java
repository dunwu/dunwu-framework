package io.github.dunwu.common.exception;

import io.github.dunwu.common.constant.CodeMsg;
import io.github.dunwu.common.constant.ResultCode;
import lombok.Getter;

import java.io.Serial;
import java.util.Objects;

/**
 * 业务通用异常基类
 * <p>
 * 生产级设计原则：
 * <p>
 * <li>不可变性：所有状态字段均为 final，确保异常抛出后状态不被篡改，线程安全。</li>
 * <li>API 收敛：移除 public 构造器，统一通过静态工厂方法 {@code of()} 创建，降低心智负担。</li>
 * <li>动态消息模板：支持 {@code of(CodeMsg, Object...)} 自动填充占位符，避免业务层手动拼接。</li>
 * <li>堆栈优化：提供 {@code noStackTrace()} 系列方法，适用于高频校验或网关包装场景。</li>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2021-09-25
 */
@Getter
public class CodeMsgException extends RuntimeException implements CodeMsg {

    @Serial
    private static final long serialVersionUID = 1L;

    /** 业务状态码 */
    private final String code;
    /** 业务错误消息 */
    private final String msg;

    /**
     * 核心全参构造方法（仅供子类继承）
     *
     * @param code  错误码
     * @param msg   错误消息
     * @param cause 原始异常
     */
    protected CodeMsgException(String code, String msg, Throwable cause) {
        super(msg, cause);
        this.code = isBlank(code) ? ResultCode.FAIL.getCode() : code;
        this.msg = msg;
    }

    /**
     * 获取格式化的异常消息，格式为 [code]msg
     * <p>
     * 注意：不重写 {@code getMessage()} 以兼容 Spring 默认异常处理机制及全局日志框架，需要格式化输出时请调用此方法。
     *
     * @return 格式化的异常消息
     */
    public String getErrorMessage() {
        if (msg != null && !msg.isEmpty()) {
            return "[" + code + "]" + msg;
        }
        return super.getMessage();
    }

    // ==================== 静态工厂方法 ====================

    /**
     * 创建指定 CodeMsg 的异常
     */
    public static CodeMsgException of(CodeMsg codeMsg) {
        Objects.requireNonNull(codeMsg, "codeMsg must not be null");
        return new CodeMsgException(codeMsg.getCode(), codeMsg.getMsg(), null);
    }

    /**
     * 创建指定 CodeMsg 并覆盖消息的异常
     */
    public static CodeMsgException of(CodeMsg codeMsg, String msg) {
        Objects.requireNonNull(codeMsg, "codeMsg must not be null");
        return new CodeMsgException(codeMsg.getCode(), msg, null);
    }

    /**
     * 创建指定 CodeMsg 并自动填充消息模板的异常
     * <p>
     * 示例：{@code CodeMsg.USER_NOT_FOUND} 的 msg 为 "用户[{}]不存在"，
     * 调用 {@code of(CodeMsg.USER_NOT_FOUND, userId)} 将生成 "用户[1001]不存在"。
     *
     * @param codeMsg 错误码信息
     * @param args    消息模板占位符参数
     */
    public static CodeMsgException of(CodeMsg codeMsg, Object... args) {
        Objects.requireNonNull(codeMsg, "codeMsg must not be null");
        String formattedMsg =
            (args == null || args.length == 0) ? codeMsg.getMsg() : formatMessage(codeMsg.getMsg(), args);
        return new CodeMsgException(codeMsg.getCode(), formattedMsg, null);
    }

    /**
     * 创建带原始异常的异常实例
     */
    public static CodeMsgException of(Throwable cause, CodeMsg codeMsg) {
        Objects.requireNonNull(cause, "cause must not be null");
        Objects.requireNonNull(codeMsg, "codeMsg must not be null");
        return new CodeMsgException(codeMsg.getCode(), codeMsg.getMsg(), cause);
    }

    /**
     * 创建带原始异常并覆盖消息的异常实例
     */
    public static CodeMsgException of(Throwable cause, CodeMsg codeMsg, String msg) {
        Objects.requireNonNull(cause, "cause must not be null");
        Objects.requireNonNull(codeMsg, "codeMsg must not be null");
        return new CodeMsgException(codeMsg.getCode(), msg, cause);
    }

    /**
     * 创建不打印堆栈的轻量级异常（适用于高频参数校验）
     * <p>
     * 注意：调用此方法创建的异常将不包含调用堆栈信息，以提升性能。
     *
     * @param codeMsg 错误码信息
     * @return 轻量级异常实例
     */
    public static CodeMsgException noStackTrace(CodeMsg codeMsg) {
        Objects.requireNonNull(codeMsg, "codeMsg must not be null");
        return new NoStackTraceException(codeMsg.getCode(), codeMsg.getMsg(), null);
    }

    /**
     * 创建不打印堆栈但保留原始异常的异常实例
     * <p>
     * 适用于网关转发、重试包装等场景，剥离当前业务层堆栈但保留根因。
     *
     * @param cause   原始异常
     * @param codeMsg 错误码信息
     * @return 轻量级异常实例
     */
    public static CodeMsgException noStackTrace(Throwable cause, CodeMsg codeMsg) {
        Objects.requireNonNull(cause, "cause must not be null");
        Objects.requireNonNull(codeMsg, "codeMsg must not be null");
        return new NoStackTraceException(cause, codeMsg.getCode(), codeMsg.getMsg());
    }

    // ==================== 内部辅助方法 ====================

    /**
     * 简易字符串判空，替代 Hutool 依赖
     */
    private static boolean isBlank(String str) {
        return str == null || str.trim().isEmpty();
    }

    /**
     * 简易消息格式化，支持 {} 占位符，兼容 SLF4J 风格
     */
    private static String formatMessage(String template, Object... args) {
        if (template == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int argIndex = 0;
        int templateIndex = 0;
        while (templateIndex < template.length()) {
            int placeholderIndex = template.indexOf("{}", templateIndex);
            if (placeholderIndex == -1) {
                sb.append(template.substring(templateIndex));
                break;
            }
            sb.append(template, templateIndex, placeholderIndex);
            if (argIndex < args.length) {
                sb.append(args[argIndex++]);
            } else {
                sb.append("{}");
            }
            templateIndex = placeholderIndex + 2;
        }
        return sb.toString();
    }

    /**
     * 无堆栈异常内部实现类
     * <p>
     * 使用静态内部类替代匿名内部类，避免序列化及编译器警告问题。
     */
    private static class NoStackTraceException extends CodeMsgException {

        @Serial
        private static final long serialVersionUID = 1L;

        public NoStackTraceException(String code, String msg, Throwable cause) {
            super(code, msg, cause);
        }

        public NoStackTraceException(Throwable cause, String code, String msg) {
            super(code, msg, cause);
        }

        @Override
        public synchronized Throwable fillInStackTrace() {
            return this;
        }

    }

}
