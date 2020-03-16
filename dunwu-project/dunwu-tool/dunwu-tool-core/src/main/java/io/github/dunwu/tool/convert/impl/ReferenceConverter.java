package io.github.dunwu.tool.convert.impl;

import io.github.dunwu.tool.convert.AbstractConverter;
import io.github.dunwu.tool.convert.ConverterRegistry;
import io.github.dunwu.tool.util.StringUtil;
import io.github.dunwu.tool.util.TypeUtil;

import java.lang.ref.Reference;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.lang.reflect.Type;

/**
 * {@link Reference}转换器
 *
 * @author Looly
 * @since 3.0.8
 */
@SuppressWarnings("rawtypes")
public class ReferenceConverter extends AbstractConverter<Reference> {

    private static final long serialVersionUID = 1L;

    private Class<? extends Reference> targetType;

    /**
     * 构造
     *
     * @param targetType {@link Reference}实现类型
     */
    public ReferenceConverter(Class<? extends Reference> targetType) {
        this.targetType = targetType;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Reference<?> convertInternal(Object value) {

        //尝试将值转换为Reference泛型的类型
        Object targetValue = null;
        final Type paramType = TypeUtil.getTypeArgument(targetType);
        if (false == TypeUtil.isUnknow(paramType)) {
            targetValue = ConverterRegistry.getInstance().convert(paramType, value);
        }
        if (null == targetValue) {
            targetValue = value;
        }

        if (this.targetType == WeakReference.class) {
            return new WeakReference(targetValue);
        } else if (this.targetType == SoftReference.class) {
            return new SoftReference(targetValue);
        }

        throw new UnsupportedOperationException(
            StringUtil.format("Unsupport Reference type: {}", this.targetType.getName()));
    }

}
