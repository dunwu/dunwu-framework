package io.github.dunwu.tool.web.converter;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.util.DateUtil;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-14
 */
public class LocalDateConverter implements Converter<String, LocalDate> {

    @Override
    public LocalDate convert(String dateString) {
        if (StrUtil.isBlank(dateString)) {
            return null;
        }

        LocalDate localDate = null;
        try {
            Date date = DatePattern.NORM_DATETIME_FORMAT.parse(dateString);
            LocalDateTime localDateTime = DateUtil.toLocalDateTime(date);
            localDate = localDateTime.toLocalDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return localDate;
    }

}
