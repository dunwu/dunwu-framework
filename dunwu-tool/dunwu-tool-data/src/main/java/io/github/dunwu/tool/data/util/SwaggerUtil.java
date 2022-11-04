package io.github.dunwu.tool.data.util;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import io.swagger.annotations.ApiModelProperty;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Swagger 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-04
 */
public class SwaggerUtil {

    public static <T> List<Map<String, Object>> getFieldsMapList(Collection<T> list, Class<T> clazz) {
        if (CollectionUtil.isNotEmpty(list)) {
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
                ApiModelProperty property = field.getAnnotation(ApiModelProperty.class);
                if (property != null) {
                    Object value = ReflectUtil.getFieldValue(item, field);
                    map.put(property.value(), value);
                }
            }
            mapList.add(map);
        }
        return mapList;
    }

}
