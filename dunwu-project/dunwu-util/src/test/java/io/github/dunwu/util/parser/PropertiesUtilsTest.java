package io.github.dunwu.util.parser;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

public class PropertiesUtilsTest {

	@Test
	void testLoadFromFile() throws IOException {
		Properties properties = PropertiesUtils
				.loadFromFile("classpath://application.properties");

		assertThat(PropertiesUtils.getInt(properties, "dunwu.min", 0)).isEqualTo(1);
		assertThat(PropertiesUtils.getInt(properties, "dunwu.max", 0)).isEqualTo(10);

		assertThat(PropertiesUtils.getLong(properties, "dunwu.min", 0L)).isEqualTo(1);
		assertThat(PropertiesUtils.getLong(properties, "dunwu.max", 0L)).isEqualTo(10);

		assertThat(PropertiesUtils.getDouble(properties, "dunwu.min", 0d)).isEqualTo(1);
		assertThat(PropertiesUtils.getDouble(properties, "dunwu.max", 0d)).isEqualTo(10);

		assertThat(PropertiesUtils.getString(properties, "dunwu.min", "")).isEqualTo("1");
		assertThat(PropertiesUtils.getString(properties, "dunwu.max", "")).isEqualTo("10");

		assertThat(PropertiesUtils.getBoolean(properties, "dunwu.enabled", false))
				.isTrue();

		assertThat(PropertiesUtils.toList(properties, "dunwu.nodes")).isNotEmpty();
	}

	@Test
	void testLoadFromString() throws IOException {
		Properties properties = PropertiesUtils
				.loadFromString("dunwu.min=1\ndunwu.max=10\ndunwu.enabled=true");
		assertThat(PropertiesUtils.getInt(properties, "dunwu.min", 0)).isEqualTo(1);
		assertThat(PropertiesUtils.getInt(properties, "dunwu.max", 0)).isEqualTo(10);
		assertThat(PropertiesUtils.getBoolean(properties, "dunwu.enabled", false))
				.isTrue();
	}

	@Test
	void testToMap() throws IOException {
		Properties properties = PropertiesUtils
				.loadFromString("dunwu.min=1\ndunwu.max=10\ndunwu.enabled=true");
		Map<String, String> map = PropertiesUtils.toMap(properties);
		assertThat(map).isNotEmpty();
	}

	@Test
	public void testToProperties() {
		Map<String, String> map = new HashMap<>(2);
		map.put("key1", "value1");
		map.put("key2", "value2");
		Properties properties = PropertiesUtils.toProperties(map);
		assertThat(properties).isNotEmpty();
	}

}
