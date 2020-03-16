package io.github.dunwu.tool.convert;

import io.github.dunwu.tool.collection.CollectionUtil;
import io.github.dunwu.tool.lang.TypeReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.*;

/**
 * 转换为集合测试
 *
 * @author looly
 */
public class ConvertToCollectionTest {

    @Test
    public void toCollectionTest() {
        Object[] a = { "a", "你", "好", "", 1 };
        List<?> list = (List<?>) Convert.convert(Collection.class, a);
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("你", list.get(1));
        Assertions.assertEquals("好", list.get(2));
        Assertions.assertEquals("", list.get(3));
        Assertions.assertEquals(1, list.get(4));
    }

    @Test
    public void toListTest() {
        Object[] a = { "a", "你", "好", "", 1 };
        List<?> list = Convert.toList(a);
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("你", list.get(1));
        Assertions.assertEquals("好", list.get(2));
        Assertions.assertEquals("", list.get(3));
        Assertions.assertEquals(1, list.get(4));
    }

    @Test
    public void toListTest2() {
        Object[] a = { "a", "你", "好", "", 1 };
        List<String> list = Convert.toList(String.class, a);
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("你", list.get(1));
        Assertions.assertEquals("好", list.get(2));
        Assertions.assertEquals("", list.get(3));
        Assertions.assertEquals("1", list.get(4));
    }

    @Test
    public void toListTest3() {
        Object[] a = { "a", "你", "好", "", 1 };
        List<String> list = Convert.toList(String.class, a);
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("你", list.get(1));
        Assertions.assertEquals("好", list.get(2));
        Assertions.assertEquals("", list.get(3));
        Assertions.assertEquals("1", list.get(4));
    }

    @Test
    public void toListTest4() {
        Object[] a = { "a", "你", "好", "", 1 };
        List<String> list = Convert.convert(new TypeReference<List<String>>() {}, a);
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("你", list.get(1));
        Assertions.assertEquals("好", list.get(2));
        Assertions.assertEquals("", list.get(3));
        Assertions.assertEquals("1", list.get(4));
    }

    @Test
    public void strToListTest() {
        String a = "a,你,好,123";
        List<?> list = Convert.toList(a);
        Assertions.assertEquals(4, list.size());
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("你", list.get(1));
        Assertions.assertEquals("好", list.get(2));
        Assertions.assertEquals("123", list.get(3));

        String b = "a";
        List<?> list2 = Convert.toList(b);
        Assertions.assertEquals(1, list2.size());
        Assertions.assertEquals("a", list2.get(0));
    }

    @Test
    public void strToListTest2() {
        String a = "a,你,好,123";
        List<String> list = Convert.toList(String.class, a);
        Assertions.assertEquals(4, list.size());
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("你", list.get(1));
        Assertions.assertEquals("好", list.get(2));
        Assertions.assertEquals("123", list.get(3));
    }

    @Test
    public void numberToListTest() {
        Integer i = 1;
        ArrayList<?> list = Convert.convert(ArrayList.class, i);
        Assertions.assertTrue(i == list.get(0));

        BigDecimal b = BigDecimal.ONE;
        ArrayList<?> list2 = Convert.convert(ArrayList.class, b);
        Assertions.assertEquals(b, list2.get(0));
    }

    @Test
    public void toLinkedListTest() {
        Object[] a = { "a", "你", "好", "", 1 };
        List<?> list = Convert.convert(LinkedList.class, a);
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("你", list.get(1));
        Assertions.assertEquals("好", list.get(2));
        Assertions.assertEquals("", list.get(3));
        Assertions.assertEquals(1, list.get(4));
    }

    @Test
    public void toSetTest() {
        Object[] a = { "a", "你", "好", "", 1 };
        LinkedHashSet<?> set = Convert.convert(LinkedHashSet.class, a);
        ArrayList<?> list = CollectionUtil.newArrayList(set);
        Assertions.assertEquals("a", list.get(0));
        Assertions.assertEquals("你", list.get(1));
        Assertions.assertEquals("好", list.get(2));
        Assertions.assertEquals("", list.get(3));
        Assertions.assertEquals(1, list.get(4));
    }

}
