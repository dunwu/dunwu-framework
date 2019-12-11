package io.github.dunwu.util.parser;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * JSON 工具类
 * <p>
 * 依赖 fastjson 工具包
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019/11/6
 */
public class JsonUtil {

    public static final SerializerFeature[] NORMAL_SERIALIZER_FEATURES =
        new SerializerFeature[] {
            SerializerFeature.WriteMapNullValue,
            SerializerFeature.WriteNullListAsEmpty,
            SerializerFeature.WriteDateUseDateFormat,
            SerializerFeature.DisableCircularReferenceDetect,
        };

    private JsonUtil() {}

}
