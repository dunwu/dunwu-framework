package io.github.dunwu.common.exception;

import io.github.dunwu.common.constant.CodeMsg;
import io.github.dunwu.common.constant.ResultCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link AuthException} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
@DisplayName("AuthException 单元测试")
class AuthExceptionTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("默认构造使用 AUTH_ERROR 错误码")
        void shouldConstructWithAuthErrorCodeByDefault() {
            AuthException exception = new AuthException();

            assertThat(exception.getCode()).isEqualTo(ResultCode.AUTH_ERROR.getCode());
            assertThat(exception.getMsg()).isEqualTo(ResultCode.AUTH_ERROR.getMsg());
        }

        @Test
        @DisplayName("使用自定义消息构造")
        void shouldConstructWithCustomMessage() {
            AuthException exception = new AuthException("Token 已过期");

            assertThat(exception.getCode()).isEqualTo(ResultCode.AUTH_ERROR.getCode());
            assertThat(exception.getMsg()).isEqualTo("Token 已过期");
        }

        @Test
        @DisplayName("使用 CodeMsg 构造")
        void shouldConstructWithCodeMsg() {
            AuthException exception = new AuthException(ResultCode.AUTH_ERROR);

            assertThat(exception.getCode()).isEqualTo("AUTH_ERROR");
            assertThat(exception.getMsg()).isEqualTo("认证错误");
        }

        @Test
        @DisplayName("使用 CodeMsg 和自定义消息构造")
        void shouldConstructWithCodeMsgAndCustomMessage() {
            AuthException exception = new AuthException(ResultCode.AUTH_ERROR, "权限不足");

            assertThat(exception.getCode()).isEqualTo("AUTH_ERROR");
            assertThat(exception.getMsg()).isEqualTo("权限不足");
        }

        @Test
        @DisplayName("使用错误码和消息构造")
        void shouldConstructWithCodeAndMessage() {
            AuthException exception = new AuthException("AUTH_001", "未授权访问");

            assertThat(exception.getCode()).isEqualTo("AUTH_001");
            assertThat(exception.getMsg()).isEqualTo("未授权访问");
        }

        @Test
        @DisplayName("使用原始异常构造")
        void shouldConstructWithCause() {
            RuntimeException cause = new RuntimeException("认证失败");
            AuthException exception = new AuthException(cause);

            assertThat(exception.getCode()).isEqualTo(ResultCode.AUTH_ERROR.getCode());
            assertThat(exception.getCause()).isEqualTo(cause);
        }

        @Test
        @DisplayName("使用原始异常和自定义消息构造")
        void shouldConstructWithCauseAndMessage() {
            RuntimeException cause = new RuntimeException("认证失败");
            AuthException exception = new AuthException(cause, "登录已过期");

            assertThat(exception.getCode()).isEqualTo(ResultCode.AUTH_ERROR.getCode());
            assertThat(exception.getMsg()).isEqualTo("登录已过期");
            assertThat(exception.getCause()).isEqualTo(cause);
        }

        @Test
        @DisplayName("使用原始异常和 CodeMsg 构造")
        void shouldConstructWithCauseAndCodeMsg() {
            RuntimeException cause = new RuntimeException("底层异常");
            AuthException exception = new AuthException(cause, ResultCode.AUTH_ERROR);

            assertThat(exception.getCode()).isEqualTo("AUTH_ERROR");
            assertThat(exception.getMsg()).isEqualTo("认证错误");
            assertThat(exception.getCause()).isEqualTo(cause);
        }

        @Test
        @DisplayName("使用原始异常、CodeMsg 和自定义消息构造")
        void shouldConstructWithCauseCodeMsgAndMessage() {
            RuntimeException cause = new RuntimeException("底层异常");
            AuthException exception = new AuthException(cause, ResultCode.AUTH_ERROR, "自定义认证错误");

            assertThat(exception.getCode()).isEqualTo("AUTH_ERROR");
            assertThat(exception.getMsg()).isEqualTo("自定义认证错误");
            assertThat(exception.getCause()).isEqualTo(cause);
        }

        @Test
        @DisplayName("使用原始异常、错误码和消息构造")
        void shouldConstructWithCauseCodeAndMessage() {
            RuntimeException cause = new RuntimeException("底层异常");
            AuthException exception = new AuthException(cause, "AUTH_CUSTOM", "自定义消息");

            assertThat(exception.getCode()).isEqualTo("AUTH_CUSTOM");
            assertThat(exception.getMsg()).isEqualTo("自定义消息");
            assertThat(exception.getCause()).isEqualTo(cause);
        }
    }

    @Nested
    @DisplayName("静态工厂方法 of() 测试")
    class OfFactoryMethodTest {

        @Test
        @DisplayName("使用 CodeMsg 创建异常")
        void shouldCreateExceptionWithCodeMsg() {
            AuthException exception = AuthException.of(ResultCode.AUTH_ERROR);

            assertThat(exception.getCode()).isEqualTo("AUTH_ERROR");
            assertThat(exception.getMsg()).isEqualTo("认证错误");
        }

        @Test
        @DisplayName("使用 CodeMsg 并覆盖消息")
        void shouldCreateExceptionWithCodeMsgAndOverrideMsg() {
            AuthException exception = AuthException.of(ResultCode.AUTH_ERROR, "覆盖认证消息");

            assertThat(exception.getCode()).isEqualTo("AUTH_ERROR");
            assertThat(exception.getMsg()).isEqualTo("覆盖认证消息");
        }

        @Test
        @DisplayName("使用原始异常和 CodeMsg 创建")
        void shouldCreateExceptionWithCauseAndCodeMsg() {
            RuntimeException cause = new RuntimeException("底层异常");
            AuthException exception = AuthException.of(cause, ResultCode.AUTH_ERROR);

            assertThat(exception.getCode()).isEqualTo("AUTH_ERROR");
            assertThat(exception.getCause()).isEqualTo(cause);
        }

        @Test
        @DisplayName("使用原始异常、CodeMsg 和自定义消息")
        void shouldCreateExceptionWithCauseCodeMsgAndMsg() {
            RuntimeException cause = new RuntimeException("底层异常");
            AuthException exception = AuthException.of(cause, ResultCode.AUTH_ERROR, "自定义消息");

            assertThat(exception.getCode()).isEqualTo("AUTH_ERROR");
            assertThat(exception.getMsg()).isEqualTo("自定义消息");
            assertThat(exception.getCause()).isEqualTo(cause);
        }
    }

    @Nested
    @DisplayName("静态工厂方法 noStackTrace() 测试")
    class NoStackTraceFactoryMethodTest {

        @Test
        @DisplayName("创建无堆栈异常")
        void shouldCreateNoStackTraceException() {
            AuthException exception = AuthException.noStackTrace(ResultCode.AUTH_ERROR);

            assertThat(exception.getCode()).isEqualTo("AUTH_ERROR");
            assertThat(exception.getMsg()).isEqualTo("认证错误");
            assertThat(exception.getStackTrace()).isEmpty();
        }

        @Test
        @DisplayName("创建无堆栈但保留原始异常")
        void shouldCreateNoStackTraceExceptionWithCause() {
            RuntimeException cause = new RuntimeException("根因");
            AuthException exception = AuthException.noStackTrace(cause, ResultCode.AUTH_ERROR);

            assertThat(exception.getCode()).isEqualTo("AUTH_ERROR");
            assertThat(exception.getCause()).isEqualTo(cause);
            assertThat(exception.getStackTrace()).isEmpty();
        }

        @Test
        @DisplayName("无堆栈异常的 fillInStackTrace 返回自身")
        void shouldReturnSelfInFillInStackTrace() {
            AuthException exception = AuthException.noStackTrace(ResultCode.AUTH_ERROR);
            Throwable result = exception.fillInStackTrace();

            assertThat(result).isSameAs(exception);
        }

        @Test
        @DisplayName("codeMsg 为 null 时抛出 NPE")
        void shouldThrowNPEWhenCodeMsgIsNull() {
            assertThatThrownBy(() -> AuthException.noStackTrace((CodeMsg) null))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("codeMsg must not be null");
        }

        @Test
        @DisplayName("cause 为 null 时抛出 NPE")
        void shouldThrowNPEWhenCauseIsNull() {
            assertThatThrownBy(() -> AuthException.noStackTrace(null, ResultCode.AUTH_ERROR))
                .isInstanceOf(NullPointerException.class)
                .hasMessageContaining("cause must not be null");
        }
    }

    @Nested
    @DisplayName("继承关系测试")
    class InheritanceTest {

        @Test
        @DisplayName("继承自 CodeMsgException")
        void shouldExtendCodeMsgException() {
            AuthException exception = new AuthException();

            assertThat(exception).isInstanceOf(CodeMsgException.class);
        }

        @Test
        @DisplayName("是 RuntimeException 类型")
        void shouldBeRuntimeException() {
            AuthException exception = new AuthException();

            assertThat(exception).isInstanceOf(RuntimeException.class);
        }
    }

    @Nested
    @DisplayName("getErrorMessage() 测试")
    class GetErrorMessageTest {

        @Test
        @DisplayName("格式化消息为 [code]msg 格式")
        void shouldFormatErrorMessage() {
            AuthException exception = new AuthException("AUTH_001", "认证失败");

            assertThat(exception.getErrorMessage()).isEqualTo("[AUTH_001]认证失败");
        }

        @Test
        @DisplayName("默认构造的格式化消息")
        void shouldFormatDefaultErrorMessage() {
            AuthException exception = new AuthException();

            assertThat(exception.getErrorMessage()).isEqualTo("[AUTH_ERROR]认证错误");
        }
    }
}
