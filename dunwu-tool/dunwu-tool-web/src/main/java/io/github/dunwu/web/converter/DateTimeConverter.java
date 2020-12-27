package io.github.dunwu.web.converter;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.util.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-14
 */
public class DateTimeConverter implements Converter<String, DateTime> {

    @Override
    public DateTime convert(String dateString) {
        if (StrUtil.isBlank(dateString)) {
            return null;
        }

        DateTime dateTime = null;
        try {
            Date date = DatePattern.NORM_DATETIME_FORMAT.parse(dateString);
            dateTime = DateUtil.date(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dateTime;
    }

}
