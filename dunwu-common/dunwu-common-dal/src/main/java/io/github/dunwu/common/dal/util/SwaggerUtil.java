package io.github.dunwu.common.dal.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import io.swagger.v3.oas.annotations.media.Schema;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Swagger 注解辅助工具类
 * <p>
 * 提供基于 Swagger {@link Schema} 注解的反射操作，例如提取实体字段描述并转换为 Map 结构，
 * 常用于动态表头生成或元数据展示。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-04
 */
public class SwaggerUtil {

    private SwaggerUtil() {
    }

    /**
     * 将实体列表转换为包含 Swagger 描述信息的 Map 列表
     * <p>
     * 遍历集合中的每个对象，提取被 {@link Schema} 注解修饰的字段，
     * 以注解中的 description 作为 Key，字段值作为 Value 构建 Map。
     *
     * @param list  实体对象集合
     * @param clazz 实体类类型
     * @param <T>   实体类型
     * @return 包含字段描述和值的 Map 列表
     */
    public static <T> List<Map<String, Object>> getFieldsMapList(Collection<T> list, Class<T> clazz) {
        if (CollectionUtil.isEmpty(list)) {
            return new ArrayList<>();
        }

        if (clazz == null) {
            return new ArrayList<>();
        }

        List<Map<String, Object>> mapList = new ArrayList<>();
        for (T item : list) {
            Map<String, Object> map = new LinkedHashMap<>();
            Field[] fields = ReflectUtil.getFields(clazz);
            for (Field field : fields) {
                Schema schema = field.getAnnotation(Schema.class);
                if (schema != null) {
                    Object value = ReflectUtil.getFieldValue(item, field);
                    map.put(schema.description(), value);
                }
            }
            mapList.add(map);
        }
        return mapList;
    }

}
