package io.github.dunwu.tool.convert;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 类型转换工具单元测试
 *
 * @author Looly
 */
public class ConvertTest {

    @Test
    public void toObjectTest() {
        Object result = Convert.convert(Object.class, "aaaa");
        Assertions.assertEquals("aaaa", result);
    }

    @Test
    public void toStrTest() {
        int a = 1;
        long[] b = { 1, 2, 3, 4, 5 };

        Assertions.assertEquals("[1, 2, 3, 4, 5]", Convert.convert(String.class, b));

        String aStr = Convert.toStr(a);
        Assertions.assertEquals("1", aStr);
        String bStr = Convert.toStr(b);
        Assertions.assertEquals("[1, 2, 3, 4, 5]", Convert.toStr(bStr));
    }

    @Test
    public void toStrTest2() {
        String result = Convert.convert(String.class, "aaaa");
        Assertions.assertEquals("aaaa", result);
    }

    @Test
    public void toStrTest3() {
        char a = 'a';
        String result = Convert.convert(String.class, a);
        Assertions.assertEquals("a", result);
    }

    @Test
    public void toIntTest() {
        String a = " 34232";
        Integer aInteger = Convert.toInt(a);
        Assertions.assertEquals(Integer.valueOf(34232), aInteger);
        int aInt = ConverterRegistry.getInstance().convert(int.class, a);
        Assertions.assertEquals(34232, aInt);

        // 带小数测试
        String b = " 34232.00";
        Integer bInteger = Convert.toInt(b);
        Assertions.assertEquals(Integer.valueOf(34232), bInteger);
        int bInt = ConverterRegistry.getInstance().convert(int.class, b);
        Assertions.assertEquals(34232, bInt);

        // boolean测试
        boolean c = true;
        Integer cInteger = Convert.toInt(c);
        Assertions.assertEquals(Integer.valueOf(1), cInteger);
        int cInt = ConverterRegistry.getInstance().convert(int.class, c);
        Assertions.assertEquals(1, cInt);

        // boolean测试
        String d = "08";
        Integer dInteger = Convert.toInt(d);
        Assertions.assertEquals(Integer.valueOf(8), dInteger);
        int dInt = ConverterRegistry.getInstance().convert(int.class, d);
        Assertions.assertEquals(8, dInt);
    }

    @Test
    public void toIntTest2() {
        ArrayList<String> array = new ArrayList<>();
        Integer aInt = Convert.convertQuietly(Integer.class, array, -1);
        Assertions.assertEquals(Integer.valueOf(-1), aInt);
    }

    @Test
    public void toLongTest() {
        String a = " 342324545435435";
        Long aLong = Convert.toLong(a);
        Assertions.assertEquals(Long.valueOf(342324545435435L), aLong);
        long aLong2 = ConverterRegistry.getInstance().convert(long.class, a);
        Assertions.assertEquals(342324545435435L, aLong2);

        // 带小数测试
        String b = " 342324545435435.245435435";
        Long bLong = Convert.toLong(b);
        Assertions.assertEquals(Long.valueOf(342324545435435L), bLong);
        long bLong2 = ConverterRegistry.getInstance().convert(long.class, b);
        Assertions.assertEquals(342324545435435L, bLong2);

        // boolean测试
        boolean c = true;
        Long cLong = Convert.toLong(c);
        Assertions.assertEquals(Long.valueOf(1), cLong);
        long cLong2 = ConverterRegistry.getInstance().convert(long.class, c);
        Assertions.assertEquals(1, cLong2);

        // boolean测试
        String d = "08";
        Long dLong = Convert.toLong(d);
        Assertions.assertEquals(Long.valueOf(8), dLong);
        long dLong2 = ConverterRegistry.getInstance().convert(long.class, d);
        Assertions.assertEquals(8, dLong2);

        byte[] bytes = { 0x1, 0x1, 0x1, 0x1 };
        Long value = Convert.toLong(bytes);
        System.out.println(value);
        Assertions.assertEquals(16843009L, value.longValue());
    }

    @Test
    public void toCharTest() {
        String str = "aadfdsfs";
        Character c = Convert.toChar(str);
        Assertions.assertEquals(Character.valueOf('a'), c);

        // 转换失败
        Object str2 = "";
        Character c2 = Convert.toChar(str2);
        Assertions.assertNull(c2);
    }

    @Test
    public void toNumberTest() {
        Object a = "12.45";
        Number number = Convert.toNumber(a);
        Assertions.assertEquals(12.45D, number);
    }

    @Test
    public void emptyToNumberTest() {
        Object a = "";
        Number number = Convert.toNumber(a);
        Assertions.assertNull(number);
    }

    @Test
    public void intAndByteConvertTest() {
        // 测试 int 转 byte
        int int0 = 234;
        byte byte0 = Convert.intToByte(int0);
        Assertions.assertEquals(-22, byte0);

        int int1 = Convert.byteToUnsignedInt(byte0);
        Assertions.assertEquals(int0, int1);
    }

    @Test
    public void intAndBytesTest() {
        // 测试 int 转 byte 数组
        int int2 = 1417;
        byte[] bytesInt = Convert.intToBytes(int2);

        // 测试 byte 数组转 int
        int int3 = Convert.bytesToInt(bytesInt);
        Assertions.assertEquals(int2, int3);
    }

    @Test
    public void longAndBytesTest() {
        // 测试 long 转 byte 数组
        long long1 = 2223;

        byte[] bytesLong = Convert.longToBytes(long1);
        long long2 = Convert.bytesToLong(bytesLong);

        Assertions.assertEquals(long1, long2);
    }

    @Test
    public void shortAndBytesTest() {
        short short1 = 122;
        byte[] bytes = Convert.shortToBytes(short1);
        short short2 = Convert.bytesToShort(bytes);

        Assertions.assertEquals(short2, short1);
    }

    @Test
    public void toListTest() {
        List<String> list = Arrays.asList("1", "2");
        String str = Convert.toStr(list);
        List<String> list2 = Convert.toList(String.class, str);
        Assertions.assertEquals("1", list2.get(0));
        Assertions.assertEquals("2", list2.get(1));

        List<Integer> list3 = Convert.toList(Integer.class, str);
        Assertions.assertEquals(1, list3.get(0).intValue());
        Assertions.assertEquals(2, list3.get(1).intValue());
    }

    @Test
    public void toListTest2() {
        String str = "1,2";
        List<String> list2 = Convert.toList(String.class, str);
        Assertions.assertEquals("1", list2.get(0));
        Assertions.assertEquals("2", list2.get(1));

        List<Integer> list3 = Convert.toList(Integer.class, str);
        Assertions.assertEquals(1, list3.get(0).intValue());
        Assertions.assertEquals(2, list3.get(1).intValue());
    }

}
