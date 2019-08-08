package io.github.dunwu.quickstart.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.github.dunwu.constant.INumberEnum;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-27
 */
public enum GenderEnum implements INumberEnum {
    MALE(0, "男"),
    FEMALE(1, "女");

    GenderEnum(int key, String value) {
        this.key = key;
        this.value = value;
    }

    @EnumValue
    private final int key;
    private final String value;

    @Override
    public int getKey() {
        return key;
    }

    @Override
    public String getValue() {
        return value;
    }
}
