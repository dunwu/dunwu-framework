package io.github.dunwu.quickstart.scheduler.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-02
 */
public enum TriggerStatusEnum {

    /**
     * 运行中
     */
    EXECUTING(0),

    /**
     * 暂停
     */
    PAUSED(1);

    @EnumValue
    @JsonValue
    private Integer key;

    TriggerStatusEnum(Integer key) {
        this.key = key;
    }

    public Integer getKey() {
        return this.key;
    }
}
