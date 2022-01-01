// package io.github.dunwu.autoconfigure.log;
//
// import io.github.dunwu.tool.web.log.support.LogRecordConfigureSelector;
// import org.springframework.context.annotation.AdviceMode;
// import org.springframework.context.annotation.Import;
//
// import java.lang.annotation.*;
//
// /**
//  * 操作日志功能开启注解
//  *
//  * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
//  * @since 2021-12-30
//  */
// @Target(ElementType.TYPE)
// @Retention(RetentionPolicy.RUNTIME)
// @Documented
// @Import(LogRecordConfigureSelector.class)
// public @interface EnableOperationLog {
//
//     String appName();
//
//     /**
//      * Indicate how caching advice should be applied. The default is {@link AdviceMode#PROXY}.
//      *
//      * @return 代理方式
//      * @see AdviceMode
//      */
//     AdviceMode mode() default AdviceMode.PROXY;
//
// }
