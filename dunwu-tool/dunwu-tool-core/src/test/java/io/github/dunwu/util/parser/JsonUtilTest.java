package io.github.dunwu.util.parser;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.github.dunwu.bean.GenderEnum;
import io.github.dunwu.bean.User;
import io.github.dunwu.util.io.AnsiSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;

import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;

class JsonUtilTest {

    private static Word word;

    private static User user;

    @RepeatedTest(3)
    @DisplayName("将对象转为数组输出")
    void beanToArray() {
        AnsiSystem.BLUE.println("普通输出");
        String oldJosn = JSON.toJSONString(user);
        System.out.println(oldJosn);
        assertThat(oldJosn).isEqualTo("{\"id\":0,\"name\":\"user\",\"sex\":\"MALE\"}");

        AnsiSystem.BLUE.println("将对象转为数组输出");
        String newJosn = JSON.toJSONString(user, SerializerFeature.BeanToArray);
        System.out.println(newJosn);
        assertThat(newJosn).isEqualTo("[null,0,\"user\",\"MALE\"]");
    }

    @BeforeEach
    void beforeEach() {
        user = new User(0, "user", null, GenderEnum.MALE);

        List<User> list = new ArrayList<>();
        User user1 = new User(1, "user1", null, GenderEnum.MALE);
        User user2 = new User(2, "user2", null, GenderEnum.FEMALE);
        User user3 = new User(3, "user3", null, GenderEnum.FEMALE);
        list.add(user3);
        list.add(user2);
        list.add(null);
        list.add(user1);

        Map<String, Object> map = new HashMap(7);
        map.put("mapa", "mapa");
        map.put("mapo", "mapo");
        map.put("mapz", "mapz");
        map.put("user1", user1);
        map.put("user3", user3);
        map.put("user4", null);
        map.put("list", list);

        word = new Word();
        word.setA("a");
        word.setB(2);
        word.setC(true);
        word.setD(null);
        word.setE("");
        word.setF(null);
        word.setDate(LocalDate.of(1949, 10, 1));
        word.setList(list);
        word.setMap(map);
    }

    @RepeatedTest(3)
    @DisplayName("自定义格式化输出")
    void customFormat() {
        word.setList(null);
        word.setMap(null);

        AnsiSystem.BLUE.println("普通输出");
        String wordOldJosn = JSON.toJSONString(word);
        System.out.println(wordOldJosn);
        assertThat(wordOldJosn).isEqualTo(
            "{\"a\":\"a\",\"b\":2,\"c\":true,\"date\":\"1949-10-01\",\"e\":\"\"}");
        String userOldJosn = JSON.toJSONString(user);
        System.out.println(userOldJosn);

        AnsiSystem.BLUE.println("格式化输出");
        String wordNewJosn = JSON.toJSONString(word, JsonUtil.NORMAL_SERIALIZER_FEATURES);
        System.out.println(wordNewJosn);
        assertThat(wordNewJosn).isEqualTo(
            "{\"a\":\"a\",\"b\":2,\"c\":true,\"d\":null,\"date\":\"1949-10-01\",\"e\":\"\",\"f\":null,\"list\":[],\"map\":null}");
        String userNewJosn = JSON.toJSONString(user);
        System.out.println(userNewJosn);
    }

    @RepeatedTest(3)
    @DisplayName("序列化和反序列化")
    void parseObject() {
        String oldJosn = JSON.toJSONString(user);
        System.out.println(oldJosn);
        assertThat(oldJosn).isEqualTo("{\"id\":0,\"name\":\"user\",\"sex\":\"MALE\"}");

        User newUser = JSON.parseObject(oldJosn, User.class);
        assertThat(newUser).isEqualTo(user);
    }

    @RepeatedTest(3)
    @DisplayName("美化输出")
    void prettyFormat() {
        AnsiSystem.BLUE.println("普通输出");
        String oldJosn = JSON.toJSONString(word);
        System.out.println(oldJosn);
        assertThat(oldJosn)
            .isEqualTo(
                "{\"a\":\"a\",\"b\":2,\"c\":true,\"date\":\"1949-10-01\",\"e\":\"\",\"list\":[{\"id\":3,\"name\":\"user3\",\"sex\":\"FEMALE\"},{\"id\":2,\"name\":\"user2\",\"sex\":\"FEMALE\"},null,{\"id\":1,\"name\":\"user1\",\"sex\":\"MALE\"}],\"map\":{\"mapo\":\"mapo\",\"user1\":{\"$ref\":\"$.list[3]\"},\"mapa\":\"mapa\",\"list\":[{\"$ref\":\"$.list[0]\"},{\"$ref\":\"$.list[1]\"},null,{\"$ref\":\"$.list[3]\"}],\"mapz\":\"mapz\",\"user3\":{\"$ref\":\"$.list[0]\"}}}");

        AnsiSystem.BLUE.println("美化输出");
        String newJosn = JSON.toJSONString(word, SerializerFeature.PrettyFormat);
        System.out.println(newJosn);
        assertThat(newJosn)
            .isEqualTo("{\n"
                + "\t\"a\":\"a\",\n"
                + "\t\"b\":2,\n"
                + "\t\"c\":true,\n"
                + "\t\"date\":\"1949-10-01\",\n"
                + "\t\"e\":\"\",\n"
                + "\t\"list\":[\n"
                + "\t\t{\n"
                + "\t\t\t\"id\":3,\n"
                + "\t\t\t\"name\":\"user3\",\n"
                + "\t\t\t\"sex\":\"FEMALE\"\n"
                + "\t\t},\n"
                + "\t\t{\n"
                + "\t\t\t\"id\":2,\n"
                + "\t\t\t\"name\":\"user2\",\n"
                + "\t\t\t\"sex\":\"FEMALE\"\n"
                + "\t\t},\n"
                + "\t\tnull,\n"
                + "\t\t{\n"
                + "\t\t\t\"id\":1,\n"
                + "\t\t\t\"name\":\"user1\",\n"
                + "\t\t\t\"sex\":\"MALE\"\n"
                + "\t\t}\n"
                + "\t],\n"
                + "\t\"map\":{\n"
                + "\t\t\"mapo\":\"mapo\",\n"
                + "\t\t\"user1\":{\"$ref\":\"$.list[3]\"},\n"
                + "\t\t\"mapa\":\"mapa\",\n"
                + "\t\t\"list\":[\n"
                + "\t\t\t{\"$ref\":\"$.list[0]\"},\n"
                + "\t\t\t{\"$ref\":\"$.list[1]\"},\n"
                + "\t\t\tnull,\n"
                + "\t\t\t{\"$ref\":\"$.list[3]\"}\n"
                + "\t\t],\n"
                + "\t\t\"mapz\":\"mapz\",\n"
                + "\t\t\"user3\":{\"$ref\":\"$.list[0]\"}\n"
                + "\t}\n"
                + "}");
    }

    @RepeatedTest(3)
    @DisplayName("Date 使用 ISO8601 格式输出")
    void useISO8601DateFormat() {
        AnsiSystem.BLUE.println("普通输出");
        String oldJosn = JSON.toJSONString(word);
        System.out.println(oldJosn);
        assertThat(oldJosn).isEqualTo(
            "{\"a\":\"a\",\"b\":2,\"c\":true,\"date\":\"1949-10-01\",\"e\":\"\",\"list\":[{\"id\":3,\"name\":\"user3\",\"sex\":\"FEMALE\"},{\"id\":2,\"name\":\"user2\",\"sex\":\"FEMALE\"},null,{\"id\":1,\"name\":\"user1\",\"sex\":\"MALE\"}],\"map\":{\"mapo\":\"mapo\",\"user1\":{\"$ref\":\"$.list[3]\"},\"mapa\":\"mapa\",\"list\":[{\"$ref\":\"$.list[0]\"},{\"$ref\":\"$.list[1]\"},null,{\"$ref\":\"$.list[3]\"}],\"mapz\":\"mapz\",\"user3\":{\"$ref\":\"$.list[0]\"}}}");

        AnsiSystem.BLUE.println("Date 使用 ISO8601 格式输出");
        String newJosn = JSON.toJSONString(word, SerializerFeature.UseISO8601DateFormat);
        System.out.println(newJosn);
        assertThat(newJosn).isEqualTo(
            "{\"a\":\"a\",\"b\":2,\"c\":true,\"date\":\"1949-10-01\",\"e\":\"\",\"list\":[{\"id\":3,\"name\":\"user3\",\"sex\":\"FEMALE\"},{\"id\":2,\"name\":\"user2\",\"sex\":\"FEMALE\"},null,{\"id\":1,\"name\":\"user1\",\"sex\":\"MALE\"}],\"map\":{\"mapo\":\"mapo\",\"user1\":{\"$ref\":\"$.list[3]\"},\"mapa\":\"mapa\",\"list\":[{\"$ref\":\"$.list[0]\"},{\"$ref\":\"$.list[1]\"},null,{\"$ref\":\"$.list[3]\"}],\"mapz\":\"mapz\",\"user3\":{\"$ref\":\"$.list[0]\"}}}");
    }

    @RepeatedTest(3)
    @DisplayName("json 字符串使用单引号（默认为双引号）")
    void useSingleQuotes() {
        // 注意：Date 每次被序列化的结果不一致
        AnsiSystem.BLUE.println("双引号 json 字符串");
        String doubleQuotesStr = JSON.toJSONString(word);
        System.out.println(JSON.toJSONString(doubleQuotesStr));
        AnsiSystem.BLUE.println("单引号 json 字符串");
        String singleQuotesStr = JSON.toJSONString(word, SerializerFeature.UseSingleQuotes);
        System.out.println(singleQuotesStr);
    }

    @RepeatedTest(3)
    @DisplayName("全局修改日期格式")
    void writeDateUseDateFormat() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+8"));
        calendar.set(1949, Calendar.OCTOBER, 1, 0, 0, 0);
        user.setBirthday(calendar.getTime());

        AnsiSystem.BLUE.println("普通输出");
        String oldJosn = JSON.toJSONString(user);
        System.out.println(oldJosn);

        AnsiSystem.BLUE.println("全局修改日期格式");
        JSON.DEFFAULT_DATE_FORMAT = "yyyy-MM-dd";
        String newJson = JSON.toJSONString(user, SerializerFeature.WriteDateUseDateFormat);
        System.out.println(newJson);
        assertThat(newJson)
            .isEqualTo("{\"birthday\":\"1949-10-01\",\"id\":0,\"name\":\"user\",\"sex\":\"MALE\"}");
    }

    @RepeatedTest(3)
    @DisplayName("字段如果为 null，输出为 null")
    void writeMapNullValue() {
        word.setDate(null);
        word.setList(null);
        word.setMap(null);

        AnsiSystem.BLUE.println("list 为 null 不输出");
        String oldJosn = JSON.toJSONString(word);
        System.out.println(oldJosn);
        assertThat(oldJosn)
            .isEqualTo("{\"a\":\"a\",\"b\":2,\"c\":true,\"e\":\"\"}");

        AnsiSystem.BLUE.println("list 为 null 输出 []");
        String newJosn = JSON.toJSONString(word, SerializerFeature.WriteMapNullValue);
        System.out.println(newJosn);
        assertThat(newJosn).isEqualTo(
            "{\"a\":\"a\",\"b\":2,\"c\":true,\"d\":null,\"date\":null,\"e\":\"\",\"f\":null,\"list\":null,\"map\":null}");
    }

    @RepeatedTest(3)
    @DisplayName("List 字段如果为 null，输出为 []")
    void writeNullListAsEmpty() {
        word.setList(null);
        word.setMap(null);

        AnsiSystem.BLUE.println("list 为 null 不输出");
        String oldJosn = JSON.toJSONString(word);
        System.out.println(oldJosn);
        assertThat(oldJosn)
            .isEqualTo("{\"a\":\"a\",\"b\":2,\"c\":true,\"date\":\"1949-10-01\",\"e\":\"\"}");

        AnsiSystem.BLUE.println("list 为 null 输出 []");
        String newJosn = JSON.toJSONString(word, SerializerFeature.WriteNullListAsEmpty);
        System.out.println(newJosn);
        assertThat(newJosn)
            .isEqualTo("{\"a\":\"a\",\"b\":2,\"c\":true,\"date\":\"1949-10-01\",\"e\":\"\",\"list\":[]}");
    }

    @RepeatedTest(3)
    @DisplayName("字符串值如果为 null，输出为空字符串（默认不输出）")
    void writeNullStringAsEmpty() {
        word.setList(null);
        word.setMap(null);

        AnsiSystem.BLUE.println("字符串值如果为 null，不输出");
        String oldJosn = JSON.toJSONString(word);
        System.out.println(oldJosn);
        assertThat(oldJosn)
            .isEqualTo("{\"a\":\"a\",\"b\":2,\"c\":true,\"date\":\"1949-10-01\",\"e\":\"\"}");

        AnsiSystem.BLUE.println("字符串值如果为 null，输出为空字符串");
        String newJosn = JSON.toJSONString(word, SerializerFeature.WriteNullStringAsEmpty);
        System.out.println(newJosn);
        assertThat(newJosn)
            .isEqualTo("{\"a\":\"a\",\"b\":2,\"c\":true,\"date\":\"1949-10-01\",\"e\":\"\",\"f\":\"\"}");
    }

    // @formatter:off
	static class Word {

		private Integer d;

		private String e;

		private String f;

		private String a;

		private int b;

		private boolean c;

		private LocalDate date;

		private Map<String, Object> map;

		private List<User> list;

		public Integer getD() {
			return d;
		}

		public void setD(Integer d) {
			this.d = d;
		}

		public String getE() {
			return e;
		}

		public void setE(String e) {
			this.e = e;
		}

		public String getF() {
			return f;
		}

		public void setF(String f) {
			this.f = f;
		}

		public String getA() {
			return a;
		}

		public void setA(String a) {
			this.a = a;
		}

		public int getB() {
			return b;
		}

		public void setB(int b) {
			this.b = b;
		}

		public boolean isC() {
			return c;
		}

		public void setC(boolean c) {
			this.c = c;
		}

		public LocalDate getDate() {
			return date;
		}

		public void setDate(LocalDate date) {
			this.date = date;
		}

		public Map<String, Object> getMap() {
			return map;
		}

		public void setMap(Map<String, Object> map) {
			this.map = map;
		}

		public List<User> getList() {
			return list;
		}

		public void setList(List<User> list) {
			this.list = list;
		}

	}
	// @formatter:on
}
