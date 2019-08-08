package io.github.dunwu.quickstart.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.github.dunwu.constant.INumberEnum;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-27
 */
public enum DeletedEnum implements INumberEnum {
    UNDELETE(0, "未删除"),
    DELETED(1, "已删除");

    DeletedEnum(int key, String value) {
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
