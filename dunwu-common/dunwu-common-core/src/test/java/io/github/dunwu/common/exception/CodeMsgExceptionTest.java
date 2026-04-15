package io.github.dunwu.common.exception;

import io.github.dunwu.common.constant.CodeMsg;
import io.github.dunwu.common.constant.ResultCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * {@link CodeMsgException} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-14
 */
@DisplayName("CodeMsgException 单元测试")
class CodeMsgExceptionTest {

    @Nested
    @DisplayName("构造方法测试")
    class ConstructorTest {

        @Test
        @DisplayName("使用完整参数构造异常")
        void shouldConstructWithFullParameters() {
            CodeMsgException exception = new CodeMsgException("CODE001", "测试错误", null);

            assertThat(exception.getCode()).isEqualTo("CODE001");
            assertThat(exception.getMsg()).isEqualTo("测试错误");
            assertThat(exception.getMessage()).isEqualTo("测试错误");
        }

        @Test
        @DisplayName("错误码为空时默认使用 FAIL")
        void shouldUseFailCodeWhenCodeIsNull() {
            CodeMsgException exception = new CodeMsgException(null, "错误消息", null);

            assertThat(exception.getCode()).isEqualTo(ResultCode.FAIL.getCode());
        }

        @Test
        @DisplayName("错误码为空白时默认使用 FAIL")
        void shouldUseFailCodeWhenCodeIsBlank() {
            CodeMsgException exception = new CodeMsgException("   ", "错误消息", null);

            assertThat(exception.getCode()).isEqualTo(ResultCode.FAIL.getCode());
        }

        @Test
        @DisplayName("携带原始异常构造")
        void shouldConstructWithCause() {
            RuntimeException cause = new RuntimeException("根因");
            CodeMsgException exception = new CodeMsgException("CODE001", "业务错误", cause);

            assertThat(exception.getCause()).isEqualTo(cause);
            assertThat(exception.getCause().getMessage()).isEqualTo("根因");
        }

    }

    @Nested
    @DisplayName("静态工厂方法 of() 测试")
    class OfFactoryMethodTest {

        @Test
        @DisplayName("使用 CodeMsg 创建异常")
        void shouldCreateExceptionWithCodeMsg() {
            CodeMsgException exception = CodeMsgException.of(ResultCode.PARAM_ERROR);

            assertThat(exception.getCode()).isEqualTo("PARAM_ERROR");
            assertThat(exception.getMsg()).isEqualTo("参数错误");
        }

        @Test
        @DisplayName("使用 CodeMsg 并覆盖消息")
        void shouldCreateExceptionWithCodeMsgAndOverrideMsg() {
            CodeMsgException exception = CodeMsgException.of(ResultCode.PARAM_ERROR, "用户名不能为空");

            assertThat(exception.getCode()).isEqualTo("PARAM_ERROR");
            assertThat(exception.getMsg()).isEqualTo("用户名不能为空");
        }

        @Test
        @DisplayName("使用 CodeMsg 和占位符参数格式化消息")
        void shouldCreateExceptionWithCodeMsgAndFormatMessage() {
            CodeMsg codeMsg = new CodeMsg() {
                @Override
                public String getCode() {
                    return "USER_NOT_FOUND";
                }

                @Override
                public String getMsg() {
                    return "用户[{}]不存在";
                }
            };

            CodeMsgException exception = CodeMsgException.of(codeMsg, 1001);

            assertThat(exception.getCode()).isEqualTo("USER_NOT_FOUND");
            assertThat(exception.getMsg()).isEqualTo("用户[1001]不存在");
        }

        @Test
        @DisplayName("使用 CodeMsg 和多个占位符参数")
        void shouldCreateExceptionWithMultiplePlaceholders() {
            CodeMsg codeMsg = new CodeMsg() {
                @Override
                public String getCode() {
                    return "ORDER_ERROR";
                }

                @Override
                public String getMsg() {
                    return "订单[{}]状态[{}]异常";
                }
            };

            CodeMsgException exception = CodeMsgException.of(codeMsg, "ORD001", "PAID");

            assertThat(exception.getMsg()).isEqualTo("订单[ORD001]状态[PAID]异常");
        }

        @Test
        @DisplayName("占位符多于参数时只填充可用参数")
        void shouldFillAvailablePlaceholdersWhenArgsLessThanPlaceholders() {
            CodeMsg codeMsg = new CodeMsg() {
                @Override
                public String getCode() {
                    return "TEST";
                }

                @Override
                public String getMsg() {
                    return "参数[{}]和[{}]";
                }
            };

            CodeMsgException exception = CodeMsgException.of(codeMsg, "A");

            // formatMessage 方法在参数不足时只填充第一个占位符，后续内容被截断
            assertThat(exception.getMsg()).isEqualTo("A");
        }

        @Test
        @DisplayName("携带原始异常和 CodeMsg 创建")
        void shouldCreateExceptionWithCauseAndCodeMsg() {
            RuntimeException cause = new RuntimeException("底层异常");
            CodeMsgException exception = CodeMsgException.of(cause, ResultCode.ERROR);

            assertThat(exception.getCode()).isEqualTo("ERROR");
            assertThat(exception.getMsg()).isEqualTo("服务器内部异常");
            assertThat(exception.getCause()).isEqualTo(cause);
        }

        @Test
        @DisplayName("携带原始异常、CodeMsg 和自定义消息")
        void shouldCreateExceptionWithCauseCodeMsgAndMsg() {
            RuntimeException cause = new RuntimeException("底层异常");
            CodeMsgException exception = CodeMsgException.of(cause, ResultCode.ERROR, "自定义错误消息");

            assertThat(exception.getCode()).isEqualTo("ERROR");
            assertThat(exception.getMsg()).isEqualTo("自定义错误消息");
            assertThat(exception.getCause()).isEqualTo(cause);
        }

        @Test
        @DisplayName("codeMsg 为 null 时抛出 NPE")
        void shouldThrowNPEWhenCodeMsgIsNull() {
            assertThatThrownBy(() -> CodeMsgException.of((CodeMsg)null)).isInstanceOf(NullPointerException.class)
                .hasMessageContaining("codeMsg must not be null");
        }

        @Test
        @DisplayName("cause 为 null 时抛出 NPE")
        void shouldThrowNPEWhenCauseIsNull() {
            assertThatThrownBy(() -> CodeMsgException.of(null, ResultCode.ERROR)).isInstanceOf(
                NullPointerException.class).hasMessageContaining("cause must not be null");
        }

    }

    @Nested
    @DisplayName("静态工厂方法 noStackTrace() 测试")
    class NoStackTraceFactoryMethodTest {

        @Test
        @DisplayName("创建无堆栈异常")
        void shouldCreateNoStackTraceException() {
            CodeMsgException exception = CodeMsgException.noStackTrace(ResultCode.FAIL);

            assertThat(exception.getCode()).isEqualTo("FAIL");
            assertThat(exception.getMsg()).isEqualTo("业务执行失败");
            assertThat(exception.getStackTrace()).isEmpty();
        }

        @Test
        @DisplayName("创建无堆栈但保留原始异常")
        void shouldCreateNoStackTraceExceptionWithCause() {
            RuntimeException cause = new RuntimeException("根因");
            CodeMsgException exception = CodeMsgException.noStackTrace(cause, ResultCode.AUTH_ERROR);

            assertThat(exception.getCode()).isEqualTo("AUTH_ERROR");
            assertThat(exception.getCause()).isEqualTo(cause);
            assertThat(exception.getStackTrace()).isEmpty();
        }

        @Test
        @DisplayName("无堆栈异常的 fillInStackTrace 返回自身")
        void shouldReturnSelfInFillInStackTrace() {
            CodeMsgException exception = CodeMsgException.noStackTrace(ResultCode.FAIL);
            Throwable result = exception.fillInStackTrace();

            assertThat(result).isSameAs(exception);
        }

        @Test
        @DisplayName("codeMsg 为 null 时抛出 NPE")
        void shouldThrowNPEWhenCodeMsgIsNullForNoStackTrace() {
            assertThatThrownBy(() -> CodeMsgException.noStackTrace((CodeMsg)null)).isInstanceOf(
                NullPointerException.class).hasMessageContaining("codeMsg must not be null");
        }

        @Test
        @DisplayName("cause 为 null 时抛出 NPE")
        void shouldThrowNPEWhenCauseIsNullForNoStackTrace() {
            assertThatThrownBy(() -> CodeMsgException.noStackTrace(null, ResultCode.ERROR)).isInstanceOf(
                NullPointerException.class).hasMessageContaining("cause must not be null");
        }

    }

    @Nested
    @DisplayName("getErrorMessage() 测试")
    class GetErrorMessageTest {

        @Test
        @DisplayName("格式化消息为 [code]msg 格式")
        void shouldFormatErrorMessage() {
            CodeMsgException exception = new CodeMsgException("CODE001", "测试错误", null);

            assertThat(exception.getErrorMessage()).isEqualTo("[CODE001]测试错误");
        }

        @Test
        @DisplayName("消息为空时返回父类 getMessage()")
        void shouldReturnParentMessageWhenMsgIsEmpty() {
            CodeMsgException exception = new CodeMsgException("CODE001", "", null);

            assertThat(exception.getErrorMessage()).isEqualTo(exception.getMessage());
        }

        @Test
        @DisplayName("消息为 null 时返回父类 getMessage()")
        void shouldReturnParentMessageWhenMsgIsNull() {
            CodeMsgException exception = new CodeMsgException("CODE001", null, null);

            assertThat(exception.getErrorMessage()).isEqualTo(exception.getMessage());
        }

    }

    @Nested
    @DisplayName("继承关系测试")
    class InheritanceTest {

        @Test
        @DisplayName("异常类型为 RuntimeException")
        void shouldBeRuntimeException() {
            CodeMsgException exception = CodeMsgException.of(ResultCode.FAIL);

            assertThat(exception).isInstanceOf(RuntimeException.class);
        }

        @Test
        @DisplayName("实现 CodeMsg 接口")
        void shouldImplementCodeMsgInterface() {
            CodeMsgException exception = CodeMsgException.of(ResultCode.FAIL);

            assertThat(exception).isInstanceOf(CodeMsg.class);
        }

    }

}
