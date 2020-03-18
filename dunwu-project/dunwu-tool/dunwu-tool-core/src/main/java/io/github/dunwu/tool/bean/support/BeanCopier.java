package io.github.dunwu.tool.bean.support;

import io.github.dunwu.tool.bean.BeanDesc;
import io.github.dunwu.tool.bean.BeanUtil;
import io.github.dunwu.tool.collection.CollectionUtil;
import io.github.dunwu.tool.convert.Convert;
import io.github.dunwu.tool.exceptions.UtilException;
import io.github.dunwu.tool.lang.ParameterizedTypeImpl;
import io.github.dunwu.tool.lang.copier.Copier;
import io.github.dunwu.tool.map.MapUtil;
import io.github.dunwu.tool.util.ArrayUtil;
import io.github.dunwu.tool.util.ObjectUtil;
import io.github.dunwu.tool.util.StringUtil;
import io.github.dunwu.tool.util.TypeUtil;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Map;

/**
 * Bean拷贝
 *
 * @param <T> 目标对象类型
 * @author looly
 * @since 3.2.3
 */
public class BeanCopier<T> implements Copier<T>, Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 源对象
     */
    private final Object source;

    /**
     * 目标对象
     */
    private final T dest;

    /**
     * 目标的类型（用于泛型类注入）
     */
    private final Type destType;

    /**
     * 拷贝选项
     */
    private final BeanOptions beanOptions;

    /**
     * 构造
     *
     * @param source      来源对象，可以是Bean或者Map
     * @param dest        目标Bean对象
     * @param destType    目标的泛型类型，用于标注有泛型参数的Bean对象
     * @param beanOptions 拷贝属性选项
     */
    public BeanCopier(Object source, T dest, Type destType, BeanOptions beanOptions) {
        this.source = source;
        this.dest = dest;
        this.destType = destType;
        this.beanOptions = beanOptions;
    }

    /**
     * 创建BeanCopier
     *
     * @param <T>         目标Bean类型
     * @param source      来源对象，可以是Bean或者Map
     * @param dest        目标Bean对象
     * @param beanOptions 拷贝属性选项
     * @return BeanCopier
     */
    public static <T> BeanCopier<T> create(Object source, T dest, BeanOptions beanOptions) {
        return create(source, dest, dest.getClass(), beanOptions);
    }

    /**
     * 创建BeanCopier
     *
     * @param <T>         目标Bean类型
     * @param source      来源对象，可以是Bean或者Map
     * @param dest        目标Bean对象
     * @param destType    目标的泛型类型，用于标注有泛型参数的Bean对象
     * @param beanOptions 拷贝属性选项
     * @return BeanCopier
     */
    public static <T> BeanCopier<T> create(Object source, T dest, Type destType, BeanOptions beanOptions) {
        return new BeanCopier<>(source, dest, destType, beanOptions);
    }

    /**
     * 获取指定字段名对应的映射值
     *
     * @param mapping   反向映射Map
     * @param fieldName 字段名
     * @return 映射值，无对应值返回字段名
     * @since 4.1.10
     */
    private static String mappingKey(Map<String, String> mapping, String fieldName) {
        if (MapUtil.isEmpty(mapping)) {
            return fieldName;
        }
        return ObjectUtil.defaultIfNull(mapping.get(fieldName), fieldName);
    }

    @Override
    @SuppressWarnings("unchecked")
    public T copy() {
        if (null != this.source) {
            if (this.source instanceof ValueProvider) {
                // 目标只支持Bean
                valueProviderToBean((ValueProvider<String>) this.source, this.dest);
            } else if (this.source instanceof Map) {
                if (this.dest instanceof Map) {
                    mapToMap((Map<?, ?>) this.source, (Map<?, ?>) this.dest);
                } else {
                    mapToBean((Map<?, ?>) this.source, this.dest);
                }
            } else {
                if (this.dest instanceof Map) {
                    beanToMap(this.source, (Map<?, ?>) this.dest);
                } else {
                    beanToBean(this.source, this.dest);
                }
            }
        }
        return this.dest;
    }

    /**
     * Bean和Bean之间属性拷贝
     *
     * @param providerBean 来源Bean
     * @param destBean     目标Bean
     */
    private void beanToBean(Object providerBean, Object destBean) {
        valueProviderToBean(
            new BeanValueProvider(providerBean, this.beanOptions.ignoreCase, this.beanOptions.ignoreError), destBean);
    }

    /**
     * Map转Bean属性拷贝
     *
     * @param map  Map
     * @param bean Bean
     */
    private void mapToBean(Map<?, ?> map, Object bean) {
        valueProviderToBean(new MapValueProvider(map, this.beanOptions.ignoreCase), bean);
    }

    /**
     * Map转Map
     *
     * @param source 源Map
     * @param dest   目标Map
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void mapToMap(Map source, Map dest) {
        if (null != dest && null != source) {
            dest.putAll(source);
        }
    }

    /**
     * 对象转Map
     *
     * @param bean      bean对象
     * @param targetMap 目标的Map
     * @since 4.1.22
     */
    @SuppressWarnings({ "rawtypes", "unchecked" })
    private void beanToMap(Object bean, Map targetMap) {
        final Collection<BeanDesc.PropDesc> props = BeanUtil.getBeanDesc(bean.getClass()).getProps();
        final HashSet<String> ignoreSet =
            (null != this.beanOptions.ignoreProperties) ? CollectionUtil.newHashSet(this.beanOptions.ignoreProperties)
                : null;
        final BeanOptions beanOptions = this.beanOptions;

        String key;
        Method getter;
        Object value;
        for (BeanDesc.PropDesc prop : props) {
            key = prop.getFieldName();
            // 过滤class属性
            // 得到property对应的getter方法
            getter = prop.getGetter();
            if (null != getter) {
                // 只读取有getter方法的属性
                try {
                    value = getter.invoke(bean);
                } catch (Exception e) {
                    if (beanOptions.ignoreError) {
                        continue;// 忽略反射失败
                    } else {
                        throw new UtilException(e, "Get value of [{}] error!", prop.getFieldName());
                    }
                }
                if (CollectionUtil.contains(ignoreSet, key)) {
                    // 目标属性值被忽略或值提供者无此key时跳过
                    continue;
                }
                if (null == value && beanOptions.ignoreNullValue) {
                    continue;// 当允许跳过空时，跳过
                }
                if (bean.equals(value)) {
                    continue;// 值不能为bean本身，防止循环引用
                }
                targetMap.put(mappingKey(beanOptions.fieldMapping, key), value);
            }
        }
    }

    /**
     * 值提供器转Bean
     *
     * @param valueProvider 值提供器
     * @param bean          Bean
     */
    private void valueProviderToBean(ValueProvider<String> valueProvider, Object bean) {
        if (null == valueProvider) {
            return;
        }

        final BeanOptions beanOptions = this.beanOptions;
        Class<?> actualEditable = bean.getClass();
        if (null != beanOptions.editable) {
            // 检查限制类是否为target的父类或接口
            if (!beanOptions.editable.isInstance(bean)) {
                throw new IllegalArgumentException(
                    StringUtil.format("Target class [{}] not assignable to Editable class [{}]",
                        bean.getClass().getName(), beanOptions.editable.getName()));
            }
            actualEditable = beanOptions.editable;
        }
        final HashSet<String> ignoreSet =
            (null != beanOptions.ignoreProperties) ? CollectionUtil.newHashSet(beanOptions.ignoreProperties) : null;
        final Map<String, String> fieldReverseMapping = beanOptions.getReversedMapping();

        final Collection<BeanDesc.PropDesc> props = BeanUtil.getBeanDesc(actualEditable).getProps();
        String fieldName;
        Object value;
        Method setterMethod;
        Class<?> propClass;
        for (BeanDesc.PropDesc prop : props) {
            // 获取值
            fieldName = prop.getFieldName();
            if (CollectionUtil.contains(ignoreSet, fieldName)) {
                // 目标属性值被忽略或值提供者无此key时跳过
                continue;
            }
            final String providerKey = mappingKey(fieldReverseMapping, fieldName);
            if (!valueProvider.containsKey(providerKey)) {
                // 无对应值可提供
                continue;
            }
            setterMethod = prop.getSetter();
            if (null == setterMethod) {
                // Setter方法不存在跳过
                continue;
            }

            Type firstParamType = TypeUtil.getFirstParamType(setterMethod);
            if (firstParamType instanceof ParameterizedType) {
                // 参数为泛型参数类型，解析对应泛型类型为真实类型
                ParameterizedType tmp = (ParameterizedType) firstParamType;
                Type[] actualTypeArguments = tmp.getActualTypeArguments();
                if (TypeUtil.hasTypeVeriable(actualTypeArguments)) {
                    // 泛型对象中含有未被转换的泛型变量
                    actualTypeArguments = TypeUtil.getActualTypes(this.destType, setterMethod.getDeclaringClass(),
                        tmp.getActualTypeArguments());
                    if (ArrayUtil.isNotEmpty(actualTypeArguments)) {
                        // 替换泛型变量为实际类型
                        firstParamType =
                            new ParameterizedTypeImpl(actualTypeArguments, tmp.getOwnerType(), tmp.getRawType());
                    }
                }
            } else if (firstParamType instanceof TypeVariable) {
                // 参数为泛型，查找其真实类型（适用于泛型方法定义于泛型父类）
                firstParamType =
                    TypeUtil.getActualType(this.destType, setterMethod.getDeclaringClass(), firstParamType);
            }

            value = valueProvider.value(providerKey, firstParamType);
            if (null == value && beanOptions.ignoreNullValue) {
                continue;// 当允许跳过空时，跳过
            }
            if (bean.equals(value)) {
                continue;// 值不能为bean本身，防止循环引用
            }

            try {
                // valueProvider在没有对值做转换且当类型不匹配的时候，执行默认转换
                propClass = prop.getFieldClass();
                if (!propClass.isInstance(value)) {
                    value = Convert.convert(propClass, value);
                    if (null == value && beanOptions.ignoreNullValue) {
                        continue;// 当允许跳过空时，跳过
                    }
                }

                // 执行set方法注入值
                setterMethod.invoke(bean, value);
            } catch (Exception e) {
                if (!beanOptions.ignoreError) {
                    throw new UtilException(e, "Inject [{}] error!", prop.getFieldName());
                }
                // 忽略注入失败
            }
        }
    }

}
