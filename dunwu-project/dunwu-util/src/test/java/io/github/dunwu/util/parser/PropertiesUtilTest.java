package io.github.dunwu.util.parser;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesUtilTest {

	@Test
	void testLoadFromFile() throws IOException {
		Properties properties = PropertiesUtil
				.loadFromFile("classpath://application.properties");

		assertThat(PropertiesUtil.getInt(properties, "dunwu.min", 0)).isEqualTo(1);
		assertThat(PropertiesUtil.getInt(properties, "dunwu.max", 0)).isEqualTo(10);

		assertThat(PropertiesUtil.getLong(properties, "dunwu.min", 0L)).isEqualTo(1);
		assertThat(PropertiesUtil.getLong(properties, "dunwu.max", 0L)).isEqualTo(10);

		assertThat(PropertiesUtil.getDouble(properties, "dunwu.min", 0d)).isEqualTo(1);
		assertThat(PropertiesUtil.getDouble(properties, "dunwu.max", 0d)).isEqualTo(10);

		assertThat(PropertiesUtil.getString(properties, "dunwu.min", "")).isEqualTo("1");
		assertThat(PropertiesUtil.getString(properties, "dunwu.max", "")).isEqualTo("10");

		assertThat(PropertiesUtil.getBoolean(properties, "dunwu.enabled", false))
				.isTrue();

		assertThat(PropertiesUtil.getList(properties, "dunwu.nodes")).isNotEmpty();
	}

	@Test
	void testLoadFromString() throws IOException {
		Properties properties = PropertiesUtil
				.loadFromString("dunwu.min=1\ndunwu.max=10\ndunwu.enabled=true");
		assertThat(PropertiesUtil.getInt(properties, "dunwu.min", 0)).isEqualTo(1);
		assertThat(PropertiesUtil.getInt(properties, "dunwu.max", 0)).isEqualTo(10);
		assertThat(PropertiesUtil.getBoolean(properties, "dunwu.enabled", false))
				.isTrue();
	}

	@Test
	void testToMap() throws IOException {
		Properties properties = PropertiesUtil
				.loadFromString("dunwu.min=1\ndunwu.max=10\ndunwu.enabled=true");
		Map<String, String> map = PropertiesUtil.toMap(properties);
		assertThat(map).isNotEmpty();
	}

	@Test
	public void testToProperties() {
		Map<String, String> map = new HashMap<>(2);
		map.put("key1", "value1");
		map.put("key2", "value2");
		Properties properties = PropertiesUtil.toProperties(map);
		assertThat(properties).isNotEmpty();
	}

}
