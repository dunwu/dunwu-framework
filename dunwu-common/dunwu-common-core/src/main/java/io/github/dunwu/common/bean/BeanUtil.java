package io.github.dunwu.common.bean;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Bean 工具类，扩展 Hutool 的 {@link cn.hutool.core.bean.BeanUtil}，提供集合级别的 Bean 转换能力。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
public class BeanUtil extends cn.hutool.core.bean.BeanUtil {

    /**
     * 深拷贝对象
     *
     * @param source 源对象，提供属性值
     * @param clazz  目标对象的类类型
     * @param <T>    目标对象的类型
     * @return 目标类的新实例，其属性值与源对象相同
     */
    public static <T> T copyProperties(Object source, Class<T> clazz) {
        return JSONUtil.toBean(JSONUtil.toJsonStr(source), clazz);
    }

    /**
     * 将源对象集合转换为目标类型的集合。
     *
     * @param collection 源对象集合
     * @param clazz      目标类型
     * @param <S>        源类型
     * @param <T>        目标类型
     * @return 转换后的目标类型集合
     */
    public static <S, T> Collection<T> toBeanCollection(Collection<S> collection, Class<T> clazz) {
        final Collection<T> targetList = new ArrayList<>();
        if (CollectionUtil.isEmpty(collection)) {
            return targetList;
        }

        for (S o : collection) {
            T item = toBean(o, clazz);
            targetList.add(item);
        }
        return targetList;
    }

    /**
     * 将源对象集合转换为目标类型的 List。
     *
     * @param collection 源对象集合
     * @param clazz      目标类型
     * @param <S>        源类型
     * @param <T>        目标类型
     * @return 转换后的目标类型 List
     */
    public static <S, T> List<T> toBeanList(Collection<S> collection, Class<T> clazz) {
        return new ArrayList<>(toBeanCollection(collection, clazz));
    }

}
