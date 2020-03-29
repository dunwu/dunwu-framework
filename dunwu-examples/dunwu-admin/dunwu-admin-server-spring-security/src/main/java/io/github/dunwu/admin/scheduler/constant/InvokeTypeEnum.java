package io.github.dunwu.admin.scheduler.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-30
 */
public enum InvokeTypeEnum {

    /**
     * 执行组中的一个任务
     */
    ONE_IN_GROUP(0),
    /**
     * 执行组中的所有任务
     */
    ALL_IN_GROUP(1);

    @EnumValue
    @JsonValue
    private Integer key;

    InvokeTypeEnum(Integer key) {
        this.key = key;
    }

    public Integer getKey() {
        return this.key;
    }
}
