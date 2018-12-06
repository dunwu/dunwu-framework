package io.github.dunwu.utils.mock;

import io.github.dunwu.utils.time.DateUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.text.RandomStringGenerator;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 产生模拟数据工具类
 * @author Zhang Peng
 * @date 2018-12-04
 */
public class MockUtil {

    private static final String defaultDateFormat = "yyyy-MM-dd HH:mm:ss";

    public static String date(final Date min, final Date max) {
        return date(min, max, defaultDateFormat);
    }

    public static String date(final Date min, final Date max, String pattern) {
        return date(DateUtil.date2LocalDateTime(min), DateUtil.date2LocalDateTime(max), pattern);
    }

    public static String date(final LocalDateTime min, final LocalDateTime max, final String pattern) {
        long minSeconds = min.toEpochSecond(ZoneOffset.UTC);
        long maxSeconds = max.toEpochSecond(ZoneOffset.UTC);
        long random = ThreadLocalRandom.current().nextLong(minSeconds, maxSeconds);
        LocalDateTime localDate = LocalDateTime.ofEpochSecond(random, 1, ZoneOffset.UTC);
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static Date date(final LocalDateTime min, final LocalDateTime max) {
        long minSeconds = min.toEpochSecond(ZoneOffset.UTC);
        long maxSeconds = max.toEpochSecond(ZoneOffset.UTC);
        long random = ThreadLocalRandom.current().nextLong(minSeconds, maxSeconds);
        LocalDateTime localDate = LocalDateTime.ofEpochSecond(random, 1, ZoneOffset.UTC);
        return DateUtil.localDateTime2Date(localDate);
    }

    public static String ipv4() {
        int[][] range = {{607649792, 608174079}, // 36.56.0.0-36.63.255.255
            {1038614528, 1039007743}, // 61.232.0.0-61.237.255.255
            {1783627776, 1784676351}, // 106.80.0.0-106.95.255.255
            {2035023872, 2035154943}, // 121.76.0.0-121.77.255.255
            {2078801920, 2079064063}, // 123.232.0.0-123.235.255.255
            {-1950089216, -1948778497}, // 139.196.0.0-139.215.255.255
            {-1425539072, -1425014785}, // 171.8.0.0-171.15.255.255
            {-1236271104, -1235419137}, // 182.80.0.0-182.92.255.255
            {-770113536, -768606209}, // 210.25.0.0-210.47.255.255
            {-569376768, -564133889}, // 222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    public static String mac() {
        final String SEPARATOR_OF_MAC = ":";
        Random random = new Random();
        String[] mac = {String.format("%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff))};
        return String.join(SEPARATOR_OF_MAC, mac);
    }

    private static String num2ip(int ip) {
        int[] b = new int[4];
        String result = "";
        b[0] = (ip >> 24) & 0xff;
        b[1] = ((ip >> 16) & 0xff);
        b[2] = ((ip >> 8) & 0xff);
        b[3] = (ip & 0xff);
        result = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "." + Integer
            .toString(b[3]);
        return result;
    }

    public static String domain() {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        //使用字母a-z，生成20个code point(维基百科称之为'码位')的随机字符串
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
        String domain1 = generator.generate(RandomUtils.nextInt(2, 11));
        String domain2 = generator.generate(RandomUtils.nextInt(2, 4));
        return new StringBuilder().append(domain1.toLowerCase()).append(".").append(domain2.toLowerCase()).toString();
    }

    public static String email() {
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('a', 'z').build();
        String name = generator.generate(RandomUtils.nextInt(2, 11));
        return new StringBuilder().append(name.toLowerCase()).append("@").append(domain()).toString();
    }

    public static String mock(String[] list) {
        return mock(Arrays.asList(list));
    }

    public static String mock(List<String> list) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        int index = RandomUtils.nextInt(0, list.size());
        return list.get(index);
    }

    public static String anyString(int minLen, int maxLen) {
        RandomStringGenerator generator = new RandomStringGenerator.Builder().withinRange('A', 'z').build();
        return generator.generate(RandomUtils.nextInt(minLen, maxLen));
    }
}



