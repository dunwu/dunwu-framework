package io.github.dunwu.tool.util;

import io.github.dunwu.tool.collection.CollectionUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

/**
 * EnumUtil单元测试
 *
 * @author looly
 */
public class EnumUtilTest {

    @Test
    public void getNamesTest() {
        List<String> names = EnumUtil.getNames(TestEnum.class);
        Assertions.assertEquals(CollectionUtil.newArrayList("TEST1", "TEST2", "TEST3"), names);
    }

    @Test
    public void getFieldValuesTest() {
        List<Object> types = EnumUtil.getFieldValues(TestEnum.class, "type");
        Assertions.assertEquals(CollectionUtil.newArrayList("type1", "type2", "type3"), types);
    }

    @Test
    public void getFieldNamesTest() {
        List<String> names = EnumUtil.getFieldNames(TestEnum.class);
        Assertions.assertEquals(CollectionUtil.newArrayList("type", "name"), names);
    }

    @Test
    public void likeValueOfTest() {
        TestEnum value = EnumUtil.likeValueOf(TestEnum.class, "type2");
        Assertions.assertEquals(TestEnum.TEST2, value);
    }

    @Test
    public void getEnumMapTest() {
        Map<String, TestEnum> enumMap = EnumUtil.getEnumMap(TestEnum.class);
        Assertions.assertEquals(TestEnum.TEST1, enumMap.get("TEST1"));
    }

    @Test
    public void getNameFieldMapTest() {
        Map<String, Object> enumMap = EnumUtil.getNameFieldMap(TestEnum.class, "type");
        Assertions.assertEquals("type1", enumMap.get("TEST1"));
    }

    @Test
    void getEnumSet() {
        EnumSet<TestEnum> set = EnumUtil.getEnumSet(TestEnum.class);
        Assertions.assertEquals(3, set.size());
    }

    @Test
    void getEnumMap() {
        Map<String, TestEnum> map = EnumUtil.getEnumMap(TestEnum.class);
        Assertions.assertEquals(3, map.size());
    }

    public enum TestEnum {
        TEST1("type1"),
        TEST2("type2"),
        TEST3("type3");

        private String type;

        private String name;

        TestEnum(String type) {
            this.type = type;
        }

        public String getType() {
            return this.type;
        }

        public String getName() {
            return this.name;
        }
    }

}
