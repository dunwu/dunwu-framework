package io.github.dunwu.common.util;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 读取 Properties 的工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-22
 */
public class PropertiesUtil {

    /**
     * 从 Properties 中获取 Boolean 值
     *
     * @param properties   属性集
     * @param key          键
     * @param defaultValue 默认值
     * @return Boolean 值
     */
    public static Boolean getBoolean(Properties properties, String key, Boolean defaultValue) {
        String value = getString(properties, key, String.valueOf(defaultValue));
        return Boolean.valueOf(value);
    }

    /**
     * 从 Properties 中获取 String 值
     *
     * @param properties   属性集
     * @param key          键
     * @param defaultValue 默认值
     * @return String 值
     */
    public static String getString(Properties properties, String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * 从 Properties 中获取 Double 值
     *
     * @param properties   属性集
     * @param key          键
     * @param defaultValue 默认值
     * @return Double 值
     */
    public static Double getDouble(Properties properties, String key, Double defaultValue) {
        String value = getString(properties, key, String.valueOf(defaultValue));
        return Double.valueOf(value);
    }

    /**
     * 从 Properties 中获取 Float 值
     *
     * @param properties   属性集
     * @param key          键
     * @param defaultValue 默认值
     * @return Float 值
     */
    public static Float getFloat(Properties properties, String key, Float defaultValue) {
        String value = getString(properties, key, String.valueOf(defaultValue));
        return Float.valueOf(value);
    }

    /**
     * 从 Properties 中获取 Integer 值
     *
     * @param properties   属性集
     * @param key          键
     * @param defaultValue 默认值
     * @return Integer 值
     */
    public static Integer getInt(Properties properties, String key, Integer defaultValue) {
        String value = getString(properties, key, String.valueOf(defaultValue));
        return Integer.valueOf(value);
    }

    /**
     * 从 Properties 中获取 Long 值
     *
     * @param properties   属性集
     * @param key          键
     * @param defaultValue 默认值
     * @return Long 值
     */
    public static Long getLong(Properties properties, String key, Long defaultValue) {
        String value = getString(properties, key, String.valueOf(defaultValue));
        return Long.valueOf(value);
    }

    /**
     * 从 Properties 中获取 Short 值
     *
     * @param properties   属性集
     * @param key          键
     * @param defaultValue 默认值
     * @return Short 值
     */
    public static Short getShort(Properties properties, String key, Short defaultValue) {
        String value = getString(properties, key, String.valueOf(defaultValue));
        return Short.valueOf(value);
    }

    /**
     * 从多个文件路径加载 properties
     */
    public static Properties loadFromFile(String... files) throws IOException {
        if (ArrayUtil.isEmpty(files)) {
            return null;
        }

        Properties properties = new Properties();
        for (String file : files) {
            properties.putAll(loadFromFile(file));
        }
        return properties;
    }

    /**
     * 从文件路径加载 properties。
     * <p>
     * 默认使用 utf-8编码解析文件
     * <p>
     * 路径支持从外部文件或resources文件加载, "file://"或无前缀代表外部文件, "classpath://"代表resources
     */
    public static Properties loadFromFile(String file) throws IOException {
        Properties properties = new Properties();
        InputStream inputStream = ResourceUtil.getStreamSafe(file);
        Reader reader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
        properties.load(reader);
        return properties;
    }

    /**
     * 从字符串内容加载 Properties
     */
    public static Properties loadFromString(String content) throws IOException {
        Properties properties = new Properties();
        Reader reader = new StringReader(content);
        properties.load(reader);
        return properties;
    }

    /**
     * 将 Properties 中包含指定 key 的属性值转为 List
     *
     * @param properties 属性集
     * @param key        键的匹配关键字
     * @return 匹配的值列表
     */
    public static List<String> toList(Properties properties, String key) {
        List<String> list = new ArrayList<>();
        Map<String, String> map = toMap(properties);
        for (Map.Entry<String, String> entity : map.entrySet()) {
            if (entity.getKey().contains(key)) {
                list.add(entity.getValue());
            }
        }
        return list;
    }

    /**
     * 将 Properties 转为 Map
     *
     * @param properties 属性集
     * @return 转换后的 Map
     */
    public static Map<String, String> toMap(Properties properties) {
        Set<Map.Entry<Object, Object>> entrySet = properties.entrySet();
        Map<String, String> map = new HashMap<>(entrySet.size());
        for (Object o : entrySet) {
            Map.Entry entry = (Map.Entry)o;
            map.put((String)entry.getKey(), (String)entry.getValue());
        }
        return map;
    }

    /**
     * 将 Map 转为 Properties
     *
     * @param map Map 对象
     * @return 转换后的 Properties
     */
    public static Properties toProperties(final Map map) {
        Properties answer = new Properties();
        if (map != null) {
            for (Iterator iter = map.entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry entry = (Map.Entry)iter.next();
                Object key = entry.getKey();
                Object value = entry.getValue();
                answer.put(key, value);
            }
        }
        return answer;
    }

}
