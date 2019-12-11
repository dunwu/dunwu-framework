package io.github.dunwu.bean;

import io.github.dunwu.util.time.DateExtUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-11-22
 */
public class TestBeanUtils {

    public static TestBean initJdk8Bean() {
        String[] strArray = { "a", "b", "c" };
        Integer[] intArray = { 1, 2, 3, 4, 5 };
        List<Integer> intList = new ArrayList<>(Arrays.asList(intArray));
        Map<String, Object> map = new HashMap<>();
        map.put("name", "jack");
        map.put("age", 18);
        map.put("length", 175.3f);

        LocalDateTime localDateTime = LocalDateTime.of(2000, 1, 1, 12, 0, 0);
        Date date = DateExtUtil.localDateTime2Date(localDateTime);
        LocalDate localDate = LocalDate.of(1949, 10, 1);
        return new TestBean(10, 1024, 0.5f, 100.0, date, localDateTime, localDate,
            GenderEnum.MALE, strArray, intList, map);
    }

}
