package io.github.dunwu.common.dal.desensitized;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import io.github.dunwu.common.dal.desensitized.annotation.Desensitized;
import io.github.dunwu.common.dal.desensitized.annotation.NeedDesensitized;
import io.github.dunwu.common.dal.desensitized.constant.SensitiveTypeEnum;
import io.github.dunwu.common.dal.domain.Result;
import io.github.dunwu.common.io.ColorUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;

import java.lang.reflect.Field;
import java.util.Collection;
import java.util.Map;

/**
 * 数据脱敏工具类
 * <p>
 * 提供对对象、集合、Map、分页结果及统一响应结果的数据脱敏功能。
 * 脱敏逻辑基于 {@link Desensitized} 和 {@link NeedDesensitized} 注解。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-04
 */
@Slf4j
public class DesensitizedUtil extends cn.hutool.core.util.DesensitizedUtil {

    /**
     * 执行数据脱敏
     * <p>
     * 根据传入对象的类型（集合、Map、分页、结果集或普通对象），自动选择相应的脱敏处理逻辑。
     *
     * @param object 待脱敏的对象
     */
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

    /**
     * 对单个对象执行脱敏
     * <p>
     * 检查类是否标注了 {@link NeedDesensitized}，若是，则遍历字段进行脱敏处理。
     * 如果字段是集合或 Map，则递归调用相应的脱敏方法。
     *
     * @param object 待脱敏的对象
     * @throws IllegalAccessException 如果反射访问字段失败
     */
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

    /**
     * 对集合执行脱敏
     *
     * @param list 待脱敏的集合
     */
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

    /**
     * 对 Map 执行脱敏
     * <p>
     * 分别对 Map 的键集合和值集合执行脱敏处理。
     *
     * @param map 待脱敏的 Map
     */
    private static void doDesensitizedInMap(Map<?, ?> map) {
        if (MapUtil.isEmpty(map)) {
            return;
        }

        doDesensitizedInList(map.keySet());
        doDesensitizedInList(map.values());
    }

    /**
     * 对分页结果执行脱敏
     *
     * @param page 待脱敏的分页对象
     */
    private static void doDesensitizedInPage(Page<?> page) {
        if (page == null || CollectionUtil.isEmpty(page.getContent())) {
            return;
        }
        doDesensitizedInList(page.getContent());
    }

    /**
     * 对统一响应结果执行脱敏
     *
     * @param result 待脱敏的 Result 对象
     */
    private static void doDesensitizedInResult(Result result) {
        if (result == null || result.getData() == null) {
            return;
        }
        doDesensitized(result.getData());
    }

    /**
     * 设置字段的脱敏值
     * <p>
     * 根据指定的脱敏类型，获取脱敏后的值并设置到对象字段中。
     * 仅支持 String 类型的字段脱敏，其他类型将被置为 null。
     *
     * @param object 目标对象
     * @param field  目标字段
     * @param type   脱敏类型
     */
    @SneakyThrows
    public static void setDesensitizedValue(Object object, Field field, SensitiveTypeEnum type) {

        if (object == null || type == null) {
            return;
        }

        if (field.getType().isPrimitive()) {
            ColorUtil.BOLD_RED.println(StrUtil.format("{}.{} 标记为 @Desensitized 无效！基础数据类型不支持数据脱敏！",
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

    /**
     * 获取脱敏后的字符串值
     *
     * @param value 原始字符串值
     * @param type  脱敏类型
     * @return 脱敏后的字符串
     */
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
