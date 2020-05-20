package io.github.dunwu.tool.util;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-05-20
 */
public class ClassUtil extends cn.hutool.core.util.ClassUtil {

    /**
     * @param clazz 指定类
     * @return 对象及其父类的所有字段
     */
    public static Field[] getAllFields(Class<?> clazz) {
        Set<Field> fieldSet = new HashSet<>();
        // 为了避免出现死循环，所以设定最大嵌套层数为 10 （正常情况，嵌套层数不会太深）
        int i = 0;
        while (clazz != null && i < 10) {
            i++;
            fieldSet.addAll(Arrays.asList(clazz.getDeclaredFields()));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldSet.size()];
        fieldSet.toArray(fields);
        return fields;
    }

}
