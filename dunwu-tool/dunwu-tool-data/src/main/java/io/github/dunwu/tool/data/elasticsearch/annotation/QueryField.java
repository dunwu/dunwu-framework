package io.github.dunwu.tool.data.elasticsearch.annotation;

import io.github.dunwu.tool.data.constant.QueryJudgeType;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
