package io.github.dunwu.tool.bean.support;

import io.github.dunwu.tool.bean.BeanDesc;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.exceptions.UtilException;
import io.github.dunwu.tool.util.StringUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Bean的值提供者
 *
 * @author looly
 */
public class BeanValueProvider implements ValueProvider<String> {

    final Map<String, BeanDesc.PropDesc> sourcePdMap;

    private final Object source;

    private final boolean ignoreError;

    /**
     * 构造
     *
     * @param bean        Bean
     * @param ignoreCase  是否忽略字段大小写
     * @param ignoreError 是否忽略字段值读取错误
     */
    public BeanValueProvider(Object bean, boolean ignoreCase, boolean ignoreError) {
        this.source = bean;
        this.ignoreError = ignoreError;
        sourcePdMap = BeanUtil.getBeanDesc(source.getClass()).getPropMap(ignoreCase);
    }

    @Override
    public Object value(String key, Type valueType) {
        BeanDesc.PropDesc sourcePd = sourcePdMap.get(key);
        boolean valueTypeIsBool = Boolean.class == valueType || boolean.class == valueType;
        if (null == sourcePd && valueTypeIsBool) {
            //boolean类型字段字段名支持两种方式
            sourcePd = sourcePdMap.get(StringUtil.upperFirstAndAddPre(key, "is"));
        }

        if (null != sourcePd) {
            final Method getter = sourcePd.getGetter();
            if (null != getter) {
                try {
                    return getter.invoke(source);
                } catch (Exception e) {
                    if (!ignoreError) {
                        throw new UtilException(e, "Inject [{}] error!", key);
                    }
                }
            }
        }
        return null;
    }

    @Override
    public boolean containsKey(String key) {
        return sourcePdMap.containsKey(key) || sourcePdMap.containsKey(StringUtil.upperFirstAndAddPre(key, "is"));
    }

}
