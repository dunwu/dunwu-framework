package io.github.dunwu.common.util;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

/**
 * 类工具类，扩展 Hutool 的 {@link cn.hutool.core.util.ClassUtil}
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-20
 */
public class ClassUtil extends cn.hutool.core.util.ClassUtil {

    /**
     * 获取指定类及其所有父类的全部字段
     *
     * @param clazz 指定类
     * @return 对象及其父类的所有字段数组
     */
    public static Field[] getAllFields(Class<?> clazz) {
        Set<Field> fieldSet = new LinkedHashSet<>();
        // 为了避免出现死循环，所以设定最大嵌套层数为 10 （正常情况，嵌套层数不会太深）
        int i = 0;
        while (clazz != null && i < 10) {
            i++;
            Collections.addAll(fieldSet, clazz.getDeclaredFields());
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldSet.size()];
        fieldSet.toArray(fields);
        return fields;
    }

}
