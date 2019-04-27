package io.github.dunwu.quickstart.constant;

import com.baomidou.mybatisplus.annotation.EnumValue;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-27
 */
public enum DeletedEnum {
    UNDELETE(0, "未删除"), DELETED(2, "已删除");

    DeletedEnum(int code, String desc) {
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
