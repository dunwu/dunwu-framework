package io.github.dunwu.tool.convert.impl;

import io.github.dunwu.tool.convert.AbstractConverter;
import io.github.dunwu.tool.date.DateUtil;
import io.github.dunwu.tool.util.StringUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * 日期转换器
 *
 * @author Looly
 */
public class CalendarConverter extends AbstractConverter<Calendar> {

    private static final long serialVersionUID = 1L;

    /**
     * 日期格式化
     */
    private String format;

    @Override
    protected Calendar convertInternal(Object value) {
        // Handle Date
        if (value instanceof Date) {
            return DateUtil.toCalendar((Date) value);
        }

        // Handle Long
        if (value instanceof Long) {
            //此处使用自动拆装箱
            return DateUtil.toCalendar((Long) value);
        }

        final String valueStr = convertToStr(value);
        return DateUtil.toCalendar(
            StringUtil.isBlank(format) ? DateUtil.parse(valueStr) : DateUtil.parse(valueStr, format));
    }

    /**
     * 获取日期格式
     *
     * @return 设置日期格式
     */
    public String getFormat() {
        return format;
    }

    /**
     * 设置日期格式
     *
     * @param format 日期格式
     */
    public void setFormat(String format) {
        this.format = format;
    }

}
