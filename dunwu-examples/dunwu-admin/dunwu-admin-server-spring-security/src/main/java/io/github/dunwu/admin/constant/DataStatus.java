package io.github.dunwu.admin.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;
import io.github.dunwu.common.constant.IIntegerStringEnum;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-25
 */
public enum DataStatus implements IIntegerStringEnum {

    VALID(0, "有效"),
    INVALID(1, "无效");

    /**
     * 标记数据库存的值是 key
     */
    @EnumValue
    private final int key;

    private final String value;

    DataStatus(int key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public int getKey() {
        return this.key;
    }

    @Override
    public String getValue() {
        return this.value;
    }
}
