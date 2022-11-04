package io.github.dunwu.tool.data.desensitized;

import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.BeanProperty;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.ContextualSerializer;
import io.github.dunwu.tool.data.desensitized.annotation.Desensitized;
import io.github.dunwu.tool.data.desensitized.constant.SensitiveTypeEnum;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.util.Objects;

/**
 * 数据脱敏序列化器
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-11-04
 */
@NoArgsConstructor
@AllArgsConstructor
public class DesensitizedSerializer extends JsonSerializer<String> implements ContextualSerializer {

    private SensitiveTypeEnum type;

    @Override
    public void serialize(String value, JsonGenerator jsonGenerator, SerializerProvider serializers) {
        if (StrUtil.isNotBlank(value) && null != type) {
            String desensitizedValue = DesensitizedUtil.getDesensitizedValue(value, type);
            try {
                jsonGenerator.writeString(desensitizedValue);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

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
