package io.github.dunwu.tool.convert.impl;

import io.github.dunwu.tool.convert.AbstractConverter;
import io.github.dunwu.tool.util.BooleanUtil;
import io.github.dunwu.tool.util.StringUtil;

/**
 * 字符转换器
 *
 * @author Looly
 */
public class CharacterConverter extends AbstractConverter<Character> {

    private static final long serialVersionUID = 1L;

    @Override
    protected Character convertInternal(Object value) {
        if (char.class == value.getClass()) {
            return Character.valueOf((char) value);
        } else if (value instanceof Boolean) {
            return BooleanUtil.toCharacter((Boolean) value);
        } else if (boolean.class == value.getClass()) {
            return BooleanUtil.toCharacter((boolean) value);
        } else {
            final String valueStr = convertToStr(value);
            if (StringUtil.isNotBlank(valueStr)) {
                return Character.valueOf(valueStr.charAt(0));
            }
        }
        return null;
    }

}
