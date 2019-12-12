package io.github.dunwu.tool.bean;

import io.github.dunwu.tool.bean.copier.CopyOptions;
import io.github.dunwu.tool.bean.copier.ValueProvider;
import io.github.dunwu.tool.collection.CollectionUtil;
import io.github.dunwu.tool.map.MapUtil;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.UUID;

/**
 * Bean工具单元测试
 *
 * @author Looly
 */
public class BeanUtilTest {

    @Test
    public void isBeanTest() {

        // HashMap不包含setXXX方法，不是bean
        boolean isBean = BeanUtil.isBean(HashMap.class);
        Assertions.assertFalse(isBean);
    }

    @Test
    public void fillBeanTest() {
        Person person = BeanUtil.fillBean(new Person(), new ValueProvider<String>() {

            @Override
            public Object value(String key, Type valueType) {
                switch (key) {
                    case "name":
                        return "张三";
                    case "age":
                        return 18;
                }
                return null;
            }

            @Override
            public boolean containsKey(String key) {
                // 总是存在key
                return true;
            }
        }, CopyOptions.create());

        Assertions.assertEquals(person.getName(), "张三");
        Assertions.assertEquals(person.getAge(), 18);
    }

    @Test
    public void fillBeanWithMapIgnoreCaseTest() {
        Map<String, Object> map = new HashMap<>(3);
        map.put("Name", "Joe");
        map.put("aGe", 12);
        map.put("openId", "DFDFSDFWERWER");
        SubPerson person = BeanUtil.fillBeanWithMapIgnoreCase(map, new SubPerson(), false);
        Assertions.assertEquals(person.getName(), "Joe");
        Assertions.assertEquals(person.getAge(), 12);
        Assertions.assertEquals(person.getOpenid(), "DFDFSDFWERWER");
    }

    @Test
    public void mapToBeanIgnoreCaseTest() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("Name", "Joe");
        map.put("aGe", 12);

        Person person = BeanUtil.toBeanIgnoreCase(map, Person.class, false);
        Assertions.assertEquals("Joe", person.getName());
        Assertions.assertEquals(12, person.getAge());
    }

    @Test
    public void mapToBeanTest() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("a_name", "Joe");
        map.put("b_age", 12);

        // 别名，用于对应bean的字段名
        Map<String, String> mapping = new HashMap<>(2);
        mapping.put("a_name", "name");
        mapping.put("b_age", "age");

        Person person = BeanUtil.toBean(map, Person.class, CopyOptions.create().setFieldMapping(mapping));
        Assertions.assertEquals("Joe", person.getName());
        Assertions.assertEquals(12, person.getAge());
    }

    @Test
    public void beanToMapTest() {
        SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        Map<String, Object> map = BeanUtil.toMap(person);

        Assertions.assertEquals("测试A11", map.get("name"));
        Assertions.assertEquals(14, map.get("age"));
        Assertions.assertEquals("11213232", map.get("openid"));
        // static属性应被忽略
        Assertions.assertFalse(map.containsKey("SUBNAME"));
    }

    @Test
    public void beanToMapTest2() {
        SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        Map<String, Object> map = BeanUtil.toMap(person, true, true);
        Assertions.assertEquals("sub名字", map.get("sub_name"));
    }

    @Test
    public void beanToMapWithLocalDateTimeTest() {
        final LocalDateTime now = LocalDateTime.now();

        SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");
        person.setDate(now);
        person.setDate2(now.toLocalDate());

        Map<String, Object> map = BeanUtil.toMap(person, false, true);
        Assertions.assertEquals(now, map.get("date"));
        Assertions.assertEquals(now.toLocalDate(), map.get("date2"));
    }

    @Test
    public void getPropertyTest() {
        SubPerson person = new SubPerson();
        person.setAge(14);
        person.setOpenid("11213232");
        person.setName("测试A11");
        person.setSubName("sub名字");

        Object name = BeanUtil.getProperty(person, "name");
        Assertions.assertEquals("测试A11", name);
        Object subName = BeanUtil.getProperty(person, "subName");
        Assertions.assertEquals("sub名字", subName);
    }

    @Test
    public void getPropertyDescriptorsTest() {
        HashSet<Object> set = CollectionUtil.newHashSet();
        PropertyDescriptor[] propertyDescriptors = BeanUtil.getPropertyDescriptors(SubPerson.class);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            set.add(propertyDescriptor.getName());
        }
        Assertions.assertTrue(set.contains("age"));
        Assertions.assertTrue(set.contains("id"));
        Assertions.assertTrue(set.contains("name"));
        Assertions.assertTrue(set.contains("openid"));
        Assertions.assertTrue(set.contains("slow"));
        Assertions.assertTrue(set.contains("subName"));
    }

    @Test
    public void copyPropertiesHasBooleanTest() {
        SubPerson p1 = new SubPerson();
        p1.setSlow(true);

        // 测试boolean参数值isXXX形式
        SubPerson p2 = new SubPerson();
        BeanUtil.copyProperties(p1, p2);
        Assertions.assertTrue(p2.getSlow());

        // 测试boolean参数值非isXXX形式
        SubPerson2 p3 = new SubPerson2();
        BeanUtil.copyProperties(p1, p3);
        Assertions.assertTrue(p3.getSlow());
    }

    @Test
    public void copyPropertiesBeanToMapTest() {
        // 测试BeanToMap
        SubPerson p1 = new SubPerson();
        p1.setSlow(true);
        p1.setName("测试");
        p1.setSubName("sub测试");

        Map<String, Object> map = MapUtil.newHashMap();
        BeanUtil.copyProperties(p1, map);
        Assertions.assertTrue((Boolean) map.get("slow"));
        Assertions.assertEquals("测试", map.get("name"));
        Assertions.assertEquals("sub测试", map.get("subName"));
    }

    @Test
    public void copyPropertiesMapToMapTest() {
        // 测试MapToMap
        Map<String, Object> p1 = new HashMap<>();
        p1.put("isSlow", true);
        p1.put("name", "测试");
        p1.put("subName", "sub测试");

        Map<String, Object> map = MapUtil.newHashMap();
        BeanUtil.copyProperties(p1, map);
        Assertions.assertTrue((Boolean) map.get("isSlow"));
        Assertions.assertEquals("测试", map.get("name"));
        Assertions.assertEquals("sub测试", map.get("subName"));
    }

    @Test
    public void trimBeanStrFieldsTest() {
        Person person = new Person();
        person.setAge(1);
        person.setName("  张三 ");
        person.setOpenid(null);
        Person person2 = BeanUtil.trimStrFields(person);

        // 是否改变原对象
        Assertions.assertEquals("张三", person.getName());
        Assertions.assertEquals("张三", person2.getName());
    }

    @Test
    public void test() {
        Person p = new Person("user", 18, "xxx");
        String result = BeanUtil.formatValues(p, ",");
        System.out.println(result);
        Assertions.assertEquals("user,18,xxx", result);

        Person p2 = new Person("user2", 20, null);
        String result2 = BeanUtil.formatValues(p2, ",");
        System.out.println(result2);
        Assertions.assertEquals("user2,20,null", result2);
    }

    // -----------------------------------------------------------------------------------------------------------------
    @Data
    public static class SubPerson extends Person {

        public static final String SUBNAME = "TEST";

        private UUID id;

        private String subName;

        private Boolean slow;

        private LocalDateTime date;

        private LocalDate date2;

    }

    @Data
    public static class SubPerson2 extends Person {

        private String subName;

        // boolean参数值非isXXX形式
        private Boolean slow;

    }

    @Getter
    @Setter
    public static class Person {

        private String name;

        private int age;

        private String openid;

        public Person() {}

        public Person(String name, int age, String openid) {
            this.name = name;
            this.age = age;
            this.openid = openid;
        }

    }

}
