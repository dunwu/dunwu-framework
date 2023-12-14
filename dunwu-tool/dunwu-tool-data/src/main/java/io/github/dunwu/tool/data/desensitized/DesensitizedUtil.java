package io.github.dunwu.tool.data.desensitized;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.tool.data.desensitized.annotation.Desensitized;
import io.github.dunwu.tool.data.desensitized.annotation.NeedDesensitized;
import io.github.dunwu.tool.data.desensitized.constant.SensitiveTypeEnum;
import io.github.dunwu.tool.data.response.Result;
import io.github.dunwu.tool.io.AnsiColorUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

@Slf4j
public class DesensitizedUtil extends cn.hutool.core.util.DesensitizedUtil {

    public static void doDesensitized(Object object) {

        if (null == object) {
            return;
        }

        try {
            if (object instanceof Collection) {
                doDesensitizedInList((Collection<?>) object);
            } else if (object instanceof Map) {
                doDesensitizedInMap((Map<?, ?>) object);
            } else if (object instanceof Page) {
                doDesensitizedInPage((Page<?>) object);
            } else if (object instanceof Result) {
                doDesensitizedInResult((Result) object);
            } else {
                doDesensitizedInObject(object);
            }
        } catch (Exception e) {
            log.error("removeSensitiveInfo error", e);
        }
    }

    private static void doDesensitizedInObject(Object object) throws IllegalAccessException {
        if (null == object.getClass().getAnnotation(NeedDesensitized.class)) {
            return;
        }

        Field[] fields = ReflectUtil.getFields(object.getClass());
        for (Field field : fields) {
            field.setAccessible(true);

            Desensitized annotation = field.getAnnotation(Desensitized.class);
            if (null != annotation) {
                setDesensitizedValue(object, field, annotation.type());
            } else if (Collection.class.isAssignableFrom(field.getType())) {
                doDesensitizedInList((Collection<?>) field.get(object));
            } else if (Map.class.isAssignableFrom(field.getType())) {
                doDesensitizedInMap((Map<?, ?>) field.get(object));
            } else if (Page.class.isAssignableFrom(field.getType())) {
                doDesensitizedInPage((Page<?>) field.get(object));
            } else {
                Class<?> clazz = field.getType();
                if (null != clazz.getAnnotation(NeedDesensitized.class)) {
                    doDesensitized(field.get(object));
                }
            }
        }
    }

    private static void doDesensitizedInList(Collection<?> list) {
        if (CollectionUtil.isEmpty(list)) {
            return;
        }

        Object firstElement = CollectionUtil.getFirst(list);
        if (firstElement == null) {
            return;
        }

        if (null != firstElement.getClass().getAnnotation(NeedDesensitized.class)) {
            for (Object obj : list) {
                doDesensitized(obj);
            }
        }
    }

    private static void doDesensitizedInMap(Map<?, ?> map) {
        if (MapUtil.isEmpty(map)) {
            return;
        }

        doDesensitizedInList(map.keySet());
        doDesensitizedInList(map.values());
    }

    private static void doDesensitizedInPage(Page<?> page) {
        if (page == null || CollectionUtil.isEmpty(page.getContent())) {
            return;
        }
        doDesensitizedInList(page.getContent());
    }

    private static void doDesensitizedInResult(Result result) {
        if (result == null || result.getData() == null) {
            return;
        }
        doDesensitized(result.getData());
    }

    @SneakyThrows
    public static void setDesensitizedValue(Object object, Field field, SensitiveTypeEnum type) {

        if (object == null || type == null) {
            return;
        }

        if (field.getType().isPrimitive()) {
            AnsiColorUtil.BOLD_RED.println(StrUtil.format("{}.{} 标记为 @Desensitized 无效！基础数据类型不支持数据脱敏！",
                object.getClass().getName(), field.getName(), field.getType().getName()));
            return;
        }

        if (field.getType() == String.class) {
            String value = (String) ReflectUtil.getFieldValue(object, field);
            String desensitizedValue = getDesensitizedValue(value, type);
            ReflectUtil.setFieldValue(object, field, desensitizedValue);
        } else {
            // 字符串以外的数据类型脱敏处理，只能将其置为 null
            field.set(object, null);
        }
    }

    public static String getDesensitizedValue(String value, SensitiveTypeEnum type) {
        if (StrUtil.isNotBlank(value) && null != type) {
            switch (type) {
                case ALL:
                    int length = Math.min(value.length(), 6);
                    return StrUtil.repeat('*', length);
                case NONE:
                    return null;
                case CHINESE_NAME:
                    return DesensitizedUtil.chineseName(value);
                case ID_CARD:
                    return DesensitizedUtil.idCardNum(value, 4, 4);
                case FIXED_PHONE:
                    return DesensitizedUtil.fixedPhone(value);
                case MOBILE_PHONE:
                    return DesensitizedUtil.mobilePhone(value);
                case ADDRESS:
                    return DesensitizedUtil.address(value, 8);
                case EMAIL:
                    return DesensitizedUtil.email(value);
                case PASSWORD:
                    return DesensitizedUtil.password(value);
                case CAR_LICENSE:
                    return DesensitizedUtil.carLicense(value);
                case BANK_CARD:
                    return DesensitizedUtil.bankCard(value);
                default:
                    break;
            }
        }
        return value;
    }

}
