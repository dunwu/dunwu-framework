package io.github.dunwu.tool.convert.impl;

import io.github.dunwu.tool.convert.AbstractConverter;
import io.github.dunwu.tool.util.StringUtil;

import java.util.Locale;

/**
 * {@link Locale}对象转换器<br> 只提供String转换支持
 *
 * @author Looly
 * @since 4.5.2
 */
public class LocaleConverter extends AbstractConverter<Locale> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Locale convertInternal(Object value) {
        try {
            String str = convertToStr(value);
            if (StringUtil.isEmpty(str)) {
                return null;
            }

            final String[] items = str.split("_");
            if (items.length == 1) {
                return new Locale(items[0]);
            }
            if (items.length == 2) {
                return new Locale(items[0], items[1]);
            }
            return new Locale(items[0], items[1], items[2]);
        } catch (Exception e) {
            // Ignore Exception
        }
        return null;
    }

}
