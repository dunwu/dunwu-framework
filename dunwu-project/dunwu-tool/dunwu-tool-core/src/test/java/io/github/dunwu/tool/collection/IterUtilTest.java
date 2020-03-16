package io.github.dunwu.tool.collection;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Map;

/**
 * {@link IterUtil} 单元测试
 *
 * @author looly
 */
public class IterUtilTest {

    @Test
    public void countMapTest() {
        ArrayList<String> list = CollectionUtil.newArrayList("a", "b", "c", "c", "a", "b", "d");
        Map<String, Integer> countMap = IterUtil.countMap(list);

        Assertions.assertEquals(Integer.valueOf(2), countMap.get("a"));
        Assertions.assertEquals(Integer.valueOf(2), countMap.get("b"));
        Assertions.assertEquals(Integer.valueOf(2), countMap.get("c"));
        Assertions.assertEquals(Integer.valueOf(1), countMap.get("d"));
    }

    @Test
    public void fieldValueMapTest() {
        ArrayList<Car> carList =
            CollectionUtil.newArrayList(new Car("123", "大众"), new Car("345", "奔驰"), new Car("567", "路虎"));
        Map<String, Car> carNameMap = IterUtil.fieldValueMap(carList, "carNumber");

        Assertions.assertEquals("大众", carNameMap.get("123").getCarName());
        Assertions.assertEquals("奔驰", carNameMap.get("345").getCarName());
        Assertions.assertEquals("路虎", carNameMap.get("567").getCarName());
    }

    @Test
    public void joinTest() {
        ArrayList<String> list = CollectionUtil.newArrayList("1", "2", "3", "4");
        String join = IterUtil.join(list, ":");
        Assertions.assertEquals("1:2:3:4", join);

        ArrayList<Integer> list1 = CollectionUtil.newArrayList(1, 2, 3, 4);
        String join1 = IterUtil.join(list1, ":");
        Assertions.assertEquals("1:2:3:4", join1);

        ArrayList<String> list2 = CollectionUtil.newArrayList("1", "2", "3", "4");
        String join2 = IterUtil.join(list2, ":", "\"", "\"");
        Assertions.assertEquals("\"1\":\"2\":\"3\":\"4\"", join2);
    }

    public static class Car {

        private String carNumber;

        private String carName;

        public Car(String carNumber, String carName) {
            this.carNumber = carNumber;
            this.carName = carName;
        }

        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }

        public String getCarName() {
            return carName;
        }

        public void setCarName(String carName) {
            this.carName = carName;
        }

    }

}
