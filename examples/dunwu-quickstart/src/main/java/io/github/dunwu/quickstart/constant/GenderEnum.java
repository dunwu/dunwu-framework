package io.github.dunwu.quickstart.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-27
 */
public enum GenderEnum {
    MALE(0, "男"), FEMALE(2, "女");

    GenderEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 标记数据库存的值是code
     */
    @EnumValue
    private final int code;
    private final String desc;

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
