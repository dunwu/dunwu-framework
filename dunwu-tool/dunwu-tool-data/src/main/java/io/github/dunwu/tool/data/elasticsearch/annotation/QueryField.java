package io.github.dunwu.tool.data.elasticsearch.annotation;

import io.github.dunwu.tool.data.constant.enums.QueryJudgeType;

import java.lang.annotation.*;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-12-17
 */
@Documented
@Inherited
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface QueryField {

    String value() default "";

    QueryJudgeType judgeType() default QueryJudgeType.Equals;

}
