package io.github.dunwu.tool.parser.yaml;

import org.yaml.snakeyaml.Yaml;

/**
 * Yaml 工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-04-27
 */
public class YamlUtil {

    public static <T> T parse(String yamlContent, Class<T> clazz) {
        Yaml yaml = new Yaml();
        yaml.load(yamlContent);
        return yaml.loadAs(yamlContent, clazz);
    }

}
