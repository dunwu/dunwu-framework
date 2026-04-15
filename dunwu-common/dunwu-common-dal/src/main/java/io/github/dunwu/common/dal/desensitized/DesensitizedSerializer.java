package io.github.dunwu.common.dal.desensitized;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import io.github.dunwu.common.dal.desensitized.annotation.Desensitized;
import io.github.dunwu.common.dal.desensitized.constant.SensitiveTypeEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

/**
 * 数据脱敏序列化器
 * <p>
 * 基于 Jackson 的 {@link ContextualSerializer} 实现，用于在 JSON 序列化过程中对敏感字段进行脱敏处理。 该序列化器会根据字段上标注的 {@link Desensitized} 注解中的
 * {@link SensitiveTypeEnum} 类型执行相应的脱敏逻辑。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-11-04
 */
@Slf4j
@NoArgsConstructor
@AllArgsConstructor
public class DesensitizedSerializer extends JsonSerializer<String> implements ContextualSerializer {

    /**
     * 脱敏类型
     */
    private SensitiveTypeEnum type;

    /**
     * 执行序列化操作
     * <p>
     * 如果值不为空且脱敏类型已设置，则调用 {@link DesensitizedUtil} 进行脱敏并写入 JSON 生成器。
     *
     * @param value         待序列化的字符串值
     * @param jsonGenerator JSON 生成器
     * @param serializers   序列化提供者
     */
    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers) {
        if (StrUtil.isNotBlank(value) && null != type) {
            String desensitizedValue = DesensitizedUtil.getDesensitizedValue(value, type);
            try {
                jsonGenerator.writeString(desensitizedValue);
            } catch (IOException e) {
                log.error("序列化失敗", e);
            }
        }
    }

    /**
     * 创建上下文序列化器
     * <p>
     * 检查目标字段是否为 String 类型，并查找 {@link Desensitized} 注解。 如果找到注解，则返回配置了相应脱敏类型的 {@link DesensitizedSerializer} 实例。
     *
     * @param serializerProvider 序列化提供者
     * @param property           当前序列化的 Bean 属性
     * @return 配置好的序列化器实例
     * @throws JsonMappingException 如果映射发生错误
     */
    @Override
    public JsonSerializer<?> createContextual(SerializerProvider serializerProvider, BeanProperty property)
        throws JsonMappingException {
        if (property != null) {
            if (Objects.equals(property.getType().getRawClass(), String.class)) {
                Desensitized desensitized = property.getAnnotation(Desensitized.class);
                if (desensitized == null) {
                    desensitized = property.getContextAnnotation(Desensitized.class);
                }
                if (desensitized != null) {
                    return new DesensitizedSerializer(desensitized.type());
                }
            }
            return serializerProvider.findValueSerializer(property.getType(), property);
        }
        return serializerProvider.findNullValueSerializer(null);
    }

}
