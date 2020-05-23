package io.github.dunwu.tool.bean;

import cn.hutool.core.collection.CollectionUtil;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-08
 */
public class BeanUtil extends cn.hutool.core.bean.BeanUtil {

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

    public static <S, T> List<T> toBeanList(List<S> collection, Class<T> clazz) {
        final List<T> targetList = new ArrayList<>();
        if (CollectionUtil.isEmpty(collection)) {
            return targetList;
        }

        for (S o : collection) {
            T item = toBean(o, clazz);
            targetList.add(item);
        }
        return targetList;
    }

}
