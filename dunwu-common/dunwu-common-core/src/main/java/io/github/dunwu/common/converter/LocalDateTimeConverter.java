package io.github.dunwu.common.converter;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.common.util.DateUtil;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * String 转 LocalDateTime 转换器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-06-14
 */
@Slf4j
public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {

    @Override
    public LocalDateTime convert(@NonNull String dateString) {
        if (StrUtil.isBlank(dateString)) {
            return null;
        }

        try {
            Date date = DatePattern.NORM_DATETIME_FORMAT.parse(dateString);
            return DateUtil.toLocalDateTime(date);
        } catch (ParseException e) {
            log.error("解析日期字符串失败: {}", dateString, e);
            return null;
        }
    }

}
