package io.github.dunwu.tool.util;

import io.github.dunwu.tool.lang.Editor;
import io.github.dunwu.tool.lang.Filter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Map;

/**
 * {@link ArrayUtil} 数组工具单元测试
 *
 * @author Looly
 */
public class ArrayUtilTest {

    final boolean[] boolArray = new boolean[] { true, true, false, false };

    final byte[] byteArray = "hello".getBytes();

    final char[] charArray = new char[] { 'a', 'b', 'c', 'd', 'e' };

    final short[] shortArray = new short[] { 1, 2, 3, 4, 5 };

    final int[] intArray = new int[] { 1, 2, 3, 4, 5 };

    final long[] longArray = new long[] { 1L, 2L, 3L, 4L, 5L };

    final float[] floatArray = new float[] { 1.0f, 2.0f, 3.0f, 4.0f, 5.0f };

    final double[] doubleArray = new double[] { 1.0, 2.0, 3.0, 4.0, 5.0 };

    final String[] stringArray = new String[] { "A", "B", "C", "D", "E" };

    @Test
    public void isEmptyTest() {
        int[] a = {};
        Assertions.assertTrue(ArrayUtil.isEmpty(a));
        Assertions.assertTrue(ArrayUtil.isEmpty((Object) a));
        int[] b = null;
        Assertions.assertTrue(ArrayUtil.isEmpty(b));
        Object c = null;
        Assertions.assertTrue(ArrayUtil.isEmpty(c));

        Object d = new Object[] { "1", "2", 3, 4D };
        boolean isEmpty = ArrayUtil.isEmpty(d);
        Assertions.assertFalse(isEmpty);
        d = new Object[0];
        isEmpty = ArrayUtil.isEmpty(d);
        Assertions.assertTrue(isEmpty);
        d = null;
        isEmpty = ArrayUtil.isEmpty(d);
        Assertions.assertTrue(isEmpty);
    }

    @Test
    public void isNotEmptyTest() {
        int[] a = { 1, 2 };
        Assertions.assertTrue(ArrayUtil.isNotEmpty(a));
    }

    @Test
    public void newArrayTest() {
        String[] newArray = ArrayUtil.newArray(String.class, 3);
        Assertions.assertEquals(3, newArray.length);
    }

    @Test
    public void cloneTest() {
        Integer[] b = { 1, 2, 3 };
        Integer[] cloneB = ArrayUtil.clone(b);
        Assertions.assertArrayEquals(b, cloneB);

        int[] a = { 1, 2, 3 };
        int[] clone = ArrayUtil.clone(a);
        Assertions.assertArrayEquals(a, clone);
    }

    @Test
    public void filterTest() {
        Integer[] a = { 1, 2, 3, 4, 5, 6 };
        Integer[] filter = ArrayUtil.filter(a, (Editor<Integer>) t -> (t % 2 == 0) ? t : null);
        Assertions.assertArrayEquals(filter, new Integer[] { 2, 4, 6 });
    }

    @Test
    public void filterTestForFilter() {
        Integer[] a = { 1, 2, 3, 4, 5, 6 };
        Integer[] filter = ArrayUtil.filter(a, (Filter<Integer>) t -> t % 2 == 0);
        Assertions.assertArrayEquals(filter, new Integer[] { 2, 4, 6 });
    }

    @Test
    public void filterTestForEditor() {
        Integer[] a = { 1, 2, 3, 4, 5, 6 };
        Integer[] filter = ArrayUtil.filter(a, (Editor<Integer>) t -> (t % 2 == 0) ? t * 10 : t);
        Assertions.assertArrayEquals(filter, new Integer[] { 1, 20, 3, 40, 5, 60 });
    }

    @Test
    public void indexOfTest() {
        Integer[] a = { 1, 2, 3, 4, 5, 6 };
        int index = ArrayUtil.indexOf(a, 3);
        Assertions.assertEquals(2, index);

        long[] b = { 1, 2, 3, 4, 5, 6 };
        int index2 = ArrayUtil.indexOf(b, 3);
        Assertions.assertEquals(2, index2);
    }

    @Test
    public void lastIndexOfTest() {
        Integer[] a = { 1, 2, 3, 4, 3, 6 };
        int index = ArrayUtil.lastIndexOf(a, 3);
        Assertions.assertEquals(4, index);

        long[] b = { 1, 2, 3, 4, 3, 6 };
        int index2 = ArrayUtil.lastIndexOf(b, 3);
        Assertions.assertEquals(4, index2);
    }

    @Test
    public void containsTest() {
        Integer[] a = { 1, 2, 3, 4, 3, 6 };
        boolean contains = ArrayUtil.contains(a, 3);
        Assertions.assertTrue(contains);

        long[] b = { 1, 2, 3, 4, 3, 6 };
        boolean contains2 = ArrayUtil.contains(b, 3);
        Assertions.assertTrue(contains2);
    }

    @Test
    public void mapTest() {
        String[] keys = { "a", "b", "c" };
        Integer[] values = { 1, 2, 3 };
        Map<String, Integer> map = ArrayUtil.zip(keys, values, true);
        Assertions.assertEquals(map.toString(), "{a=1, b=2, c=3}");
    }

    @Test
    public void castTest() {
        Object[] values = { "1", "2", "3" };
        String[] cast = (String[]) ArrayUtil.cast(String.class, values);
        Assertions.assertEquals(values[0], cast[0]);
        Assertions.assertEquals(values[1], cast[1]);
        Assertions.assertEquals(values[2], cast[2]);
    }

    @Test
    public void rangeTest() {
        int[] range = ArrayUtil.range(0, 10);
        Assertions.assertEquals(0, range[0]);
        Assertions.assertEquals(1, range[1]);
        Assertions.assertEquals(2, range[2]);
        Assertions.assertEquals(3, range[3]);
        Assertions.assertEquals(4, range[4]);
        Assertions.assertEquals(5, range[5]);
        Assertions.assertEquals(6, range[6]);
        Assertions.assertEquals(7, range[7]);
        Assertions.assertEquals(8, range[8]);
        Assertions.assertEquals(9, range[9]);
    }

    @Test
    public void maxTest() {
        int max = ArrayUtil.max(1, 2, 13, 4, 5);
        Assertions.assertEquals(13, max);

        long maxLong = ArrayUtil.max(1L, 2L, 13L, 4L, 5L);
        Assertions.assertEquals(13, maxLong);

        double maxDouble = ArrayUtil.max(1D, 2.4D, 13.0D, 4.55D, 5D);
        Assertions.assertEquals(13.0, maxDouble, 2);
    }

    @Test
    public void minTest() {
        int min = ArrayUtil.min(1, 2, 13, 4, 5);
        Assertions.assertEquals(1, min);

        long minLong = ArrayUtil.min(1L, 2L, 13L, 4L, 5L);
        Assertions.assertEquals(1, minLong);

        double minDouble = ArrayUtil.min(1D, 2.4D, 13.0D, 4.55D, 5D);
        Assertions.assertEquals(1.0, minDouble, 2);
    }

    @Test
    public void appendTest() {
        String[] a = { "1", "2", "3", "4" };
        String[] b = { "a", "b", "c" };

        String[] result = ArrayUtil.addAll(a, b);
        Assertions.assertArrayEquals(new String[] { "1", "2", "3", "4", "a", "b", "c" }, result);
    }

    @Test
    public void insertTest() {
        String[] a = { "1", "2", "3", "4" };
        String[] b = { "a", "b", "c" };

        // 在-1位置插入，相当于在3位置插入
        String[] result = ArrayUtil.insert(a, -1, b);
        Assertions.assertArrayEquals(new String[] { "1", "2", "3", "a", "b", "c", "4" }, result);

        // 在第0个位置插入，即在数组前追加
        result = ArrayUtil.insert(a, 0, b);
        Assertions.assertArrayEquals(new String[] { "a", "b", "c", "1", "2", "3", "4" }, result);

        // 在第2个位置插入，即"3"之前
        result = ArrayUtil.insert(a, 2, b);
        Assertions.assertArrayEquals(new String[] { "1", "2", "a", "b", "c", "3", "4" }, result);

        // 在第4个位置插入，即"4"之后，相当于追加
        result = ArrayUtil.insert(a, 4, b);
        Assertions.assertArrayEquals(new String[] { "1", "2", "3", "4", "a", "b", "c" }, result);

        // 在第5个位置插入，由于数组长度为4，因此补null
        result = ArrayUtil.insert(a, 5, b);
        Assertions.assertArrayEquals(new String[] { "1", "2", "3", "4", null, "a", "b", "c" }, result);
    }

    @Test
    public void joinTest() {
        String[] array = { "aa", "bb", "cc", "dd" };
        String join = ArrayUtil.join(",", "[", "]", array);
        Assertions.assertEquals("[aa],[bb],[cc],[dd]", join);
    }

    @Test
    public void getArrayTypeTest() {
        Class<?> arrayType = ArrayUtil.getArrayType(int.class);
        Assertions.assertEquals(int[].class, arrayType);

        arrayType = ArrayUtil.getArrayType(String.class);
        Assertions.assertEquals(String[].class, arrayType);
    }

    @Test
    public void distinctTest() {
        String[] array = { "aa", "bb", "cc", "dd", "bb", "dd" };
        String[] distinct = ArrayUtil.distinct(array);
        Assertions.assertArrayEquals(new String[] { "aa", "bb", "cc", "dd" }, distinct);
    }

    @Test
    @DisplayName("数组 toString")
    public void toStringTest() {
        Assertions.assertEquals("[true, true, false, false]", ArrayUtil.toString(boolArray));
        Assertions.assertEquals("[104, 101, 108, 108, 111]", ArrayUtil.toString(byteArray));
        Assertions.assertEquals("[a, b, c, d, e]", ArrayUtil.toString(charArray));
        Assertions.assertEquals("[1, 2, 3, 4, 5]", ArrayUtil.toString(shortArray));
        Assertions.assertEquals("[1, 2, 3, 4, 5]", ArrayUtil.toString(intArray));
        Assertions.assertEquals("[1, 2, 3, 4, 5]", ArrayUtil.toString(longArray));
        Assertions.assertEquals("[1.0, 2.0, 3.0, 4.0, 5.0]", ArrayUtil.toString(floatArray));
        Assertions.assertEquals("[1.0, 2.0, 3.0, 4.0, 5.0]", ArrayUtil.toString(doubleArray));
        Assertions.assertEquals("[A, B, C, D, E]", ArrayUtil.toString(stringArray));
    }

    @Test
    @DisplayName("装箱/拆箱")
    public void boxingAndUnboxing() {

        // boolean[] <-> Boolean[]
        Boolean[] bools = ArrayUtil.boxing(boolArray);
        Assertions.assertNotNull(bools);
        boolean[] bools2 = ArrayUtil.unboxing(bools);
        Assertions.assertNotNull(bools2);
        Assertions.assertArrayEquals(boolArray, bools2);

        // byte[] <-> Byte[]
        Byte[] bytes = ArrayUtil.boxing(byteArray);
        Assertions.assertNotNull(bytes);
        byte[] bytes2 = ArrayUtil.unboxing(bytes);
        Assertions.assertNotNull(bytes2);
        Assertions.assertArrayEquals(byteArray, bytes2);

        // char[] <-> Character[]
        Character[] chars = ArrayUtil.boxing(charArray);
        Assertions.assertNotNull(chars);
        char[] chars2 = ArrayUtil.unboxing(chars);
        Assertions.assertNotNull(chars2);
        Assertions.assertArrayEquals(charArray, chars2);

        // short[] <-> Short[]
        Short[] shorts = ArrayUtil.boxing(shortArray);
        Assertions.assertNotNull(shorts);
        short[] shorts2 = ArrayUtil.unboxing(shorts);
        Assertions.assertNotNull(shorts2);
        Assertions.assertArrayEquals(shortArray, shorts2);

        // int[] <-> Integer[]
        Integer[] ints = ArrayUtil.boxing(intArray);
        Assertions.assertNotNull(ints);
        int[] ints2 = ArrayUtil.unboxing(ints);
        Assertions.assertNotNull(ints2);
        Assertions.assertArrayEquals(intArray, ints2);

        // long[] <-> Long[]
        Long[] longs = ArrayUtil.boxing(longArray);
        Assertions.assertNotNull(longs);
        long[] longs2 = ArrayUtil.unboxing(longs);
        Assertions.assertNotNull(longs2);
        Assertions.assertArrayEquals(longArray, longs2);

        // float[] <-> Float[]
        Float[] floats = ArrayUtil.boxing(floatArray);
        Assertions.assertNotNull(floats);
        float[] floats2 = ArrayUtil.unboxing(floats);
        Assertions.assertNotNull(floats2);
        Assertions.assertArrayEquals(floatArray, floats2);

        // double[] <-> Double[]
        Double[] doubles = ArrayUtil.boxing(doubleArray);
        Assertions.assertNotNull(doubles);
        double[] doubles2 = ArrayUtil.unboxing(doubles);
        Assertions.assertNotNull(doubles2);
        Assertions.assertArrayEquals(doubleArray, doubles2);
    }

}
