package io.github.dunwu.tool.convert.impl;

import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.bean.support.BeanCopier;
import io.github.dunwu.tool.bean.support.BeanOptions;
import io.github.dunwu.tool.bean.support.ValueProvider;
import io.github.dunwu.tool.convert.AbstractConverter;
import io.github.dunwu.tool.map.MapProxy;
import io.github.dunwu.tool.util.ReflectUtil;
import io.github.dunwu.tool.util.TypeUtil;

import java.lang.reflect.Type;
import java.util.Map;

/**
 * Bean转换器，支持：
 * <pre>
 * Map =》 Bean
 * Bean =》 Bean
 * ValueProvider =》 Bean
 * </pre>
 *
 * @param <T> Bean类型
 * @author Looly
 * @since 4.0.2
 */
public class BeanConverter<T> extends AbstractConverter<T> {

    private static final long serialVersionUID = 1L;

    private final Type beanType;

    private final Class<T> beanClass;

    private final BeanOptions beanOptions;

    /**
     * 构造，默认转换选项，注入失败的字段忽略
     *
     * @param beanType 转换成的目标Bean类型
     */
    public BeanConverter(Type beanType) {
        this(beanType, BeanOptions.create().setIgnoreError(true));
    }

    /**
     * 构造
     *
     * @param beanType    转换成的目标Bean类
     * @param beanOptions Bean转换选项参数
     */
    @SuppressWarnings("unchecked")
    public BeanConverter(Type beanType, BeanOptions beanOptions) {
        this.beanType = beanType;
        this.beanClass = (Class<T>) TypeUtil.getClass(beanType);
        this.beanOptions = beanOptions;
    }

    /**
     * 构造，默认转换选项，注入失败的字段忽略
     *
     * @param beanClass 转换成的目标Bean类
     */
    public BeanConverter(Class<T> beanClass) {
        this(beanClass, BeanOptions.create().setIgnoreError(true));
    }

    @Override
    protected T convertInternal(Object value) {
        if (value instanceof Map || value instanceof ValueProvider || BeanUtil.isBean(value.getClass())) {
            if (value instanceof Map && this.beanClass.isInterface()) {
                // 将Map动态代理为Bean
                return MapProxy.create((Map<?, ?>) value).toProxyBean(this.beanClass);
            }

            //限定被转换对象类型
            return BeanCopier.create(value, ReflectUtil.newInstanceIfPossible(this.beanClass), this.beanType,
                this.beanOptions).copy();
        }
        return null;
    }

    @Override
    public Class<T> getTargetType() {
        return this.beanClass;
    }

}
