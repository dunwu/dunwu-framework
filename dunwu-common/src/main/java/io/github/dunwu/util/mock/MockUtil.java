package io.github.dunwu.util.mock;

import io.github.dunwu.util.number.RandomUtil;
import io.github.dunwu.util.time.DateUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
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
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2018-12-04
 */
public class MockUtil {

    private static final String defaultDateFormat = "yyyy-MM-dd HH:mm:ss";

    public static String anyDate(final Date min, final Date max) {
        return anyDate(min, max, defaultDateFormat);
    }

    public static String anyDate(final Date min, final Date max, String pattern) {
        return anyDate(DateUtil.date2LocalDateTime(min), DateUtil.date2LocalDateTime(max), pattern);
    }

    public static String anyDate(final LocalDateTime min, final LocalDateTime max, final String pattern) {
        long minSeconds = min.toEpochSecond(ZoneOffset.UTC);
        long maxSeconds = max.toEpochSecond(ZoneOffset.UTC);
        long random = ThreadLocalRandom.current()
                                       .nextLong(minSeconds, maxSeconds);
        LocalDateTime localDate = LocalDateTime.ofEpochSecond(random, 1, ZoneOffset.UTC);
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static Date anyDate(final LocalDateTime min, final LocalDateTime max) {
        long minSeconds = min.toEpochSecond(ZoneOffset.UTC);
        long maxSeconds = max.toEpochSecond(ZoneOffset.UTC);
        long random = ThreadLocalRandom.current()
                                       .nextLong(minSeconds, maxSeconds);
        LocalDateTime localDate = LocalDateTime.ofEpochSecond(random, 1, ZoneOffset.UTC);
        return DateUtil.localDateTime2Date(localDate);
    }

    public static String anyIPv4() {
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

    public static String anyMac() {
        Random random = new Random();
        String[] mac = {String.format("%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff)), String.format(
            "%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff))};
        return StringUtils.join(mac);
    }

    public static String anyMac(String separator) {
        Random random = new Random();
        String[] mac = {String.format("%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff)), String.format(
            "%02x", random.nextInt(0xff)), String.format("%02x", random.nextInt(0xff))};
        return String.join(separator, mac);
    }

    private static String num2ip(int ip) {
        int[] b = new int[4];
        String result = "";
        b[0] = (ip >> 24) & 0xff;
        b[1] = ((ip >> 16) & 0xff);
        b[2] = ((ip >> 8) & 0xff);
        b[3] = (ip & 0xff);
        result = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "." + Integer.toString(b[2]) + "."
            + Integer.toString(b[3]);
        return result;
    }

    public static String anyDomain() {
        String domain1 = RandomStringUtils.randomAlphabetic(2, 11);
        String domain2 = RandomStringUtils.randomAlphabetic(2, 4);
        return new StringBuilder().append(domain1.toLowerCase())
                                  .append(".")
                                  .append(domain2.toLowerCase())
                                  .toString();
    }

    public static String anyEmail() {
        String name = RandomStringUtils.randomAlphabetic(2, 11);
        return new StringBuilder().append(name.toLowerCase())
                                  .append("@")
                                  .append(anyDomain())
                                  .toString();
    }

    public static String anyFirstName() {
        boolean isGirl = RandomUtils.nextBoolean();
        return anyFirstName(isGirl);
    }

    public static String anyFirstName(boolean isGirl) {
        if (isGirl) {
            return anyGirlFirstName();
        } else {
            return anyBoyFirstName();
        }
    }

    public static String anyBoyFirstName() {
        String dictionary =
            "Dylan,Jacob,Ethan,Gabriel,Nicholas,Jack,Joshua,Caleb,Ryan,Andrew,Caden,Tyler,Michael,Jaden,Zachary,"
                + "Connor,Logan,Aiden,Noah,Alexande,Jackson,Brayden,Lucas,William,Nathan,Joseph,Justin,Daniel,"
                + "Benjamin,Christopher,James,Gavin,Evan,Austin,Cameron,Brandon,Mason,Luke,Anthony,Christian,"
                + "Jonathan,Owen,David,John,Matthew,Samuel,Sean,Hunter,Elijah,Thomas";
        String[] names = StringUtils.splitByWholeSeparator(dictionary, ",");
        return mock(names);
    }

    public static String anyGirlFirstName() {
        String dictionary =
            "Lily,Emily,Sophia,Isabella,Ava,Emma,Kaitlyn,Hannah,Hailey,Olivia,Sarah,Abigail,Madeline,Madison,Kaylee,"
                + "Ella,Riley,Alexis,Alyssa,Samantha,Lauren,Mia,Grace,Chloe,Ashley,Brianna,Jessica,Elizabeth,Taylor,"
                + "Makayla,Makenzie,Anna,Zoe,Kayla,Sydney,Megan,Natalie,Kylie,ulia,Avery,Katherine,Isabel,Victoria,"
                + "Morgan,Kyra,Jasmine,Allison,Savannah,JRachel,Jordan";
        String[] names = StringUtils.splitByWholeSeparator(dictionary, ",");
        return mock(names);
    }

    public static String anyLastName() {
        String dictionary =
            "Smith,Jones,Williams,Taylor,Brown,Davies,Evans,Wilson,Thomas,Johnson,Roberts,Robinson,Thompson,Wright,"
                + "Walker,White,Edwards,Hughes,Green,Hall,Lewis,Harris,Clarke,Patel,Jackson";
        String[] names = StringUtils.splitByWholeSeparator(dictionary, ",");
        return mock(names);
    }

    public static String anyCLastName() {
        String dictionary =
            "李,王,张,刘,陈,杨,黄,赵,周,吴,徐,孙,朱,马,胡,郭,林,何,高,梁,郑,罗,宋,谢,唐,韩,曹,许,邓,萧,冯,曾,程,蔡,彭,潘,袁,于,董,余,苏,叶,吕,魏,蒋,田,杜,丁,沈,姜,范,江,"
                + "傅,钟,卢,汪,戴,崔,任,陆,廖,姚,方,金,邱,夏,谭,韦,贾,邹,石,熊,孟,秦,阎,薛,侯,雷,白,龙,段,郝,孔,邵,史,毛,常,万,顾,赖,武,康,贺,严,尹,钱,施,牛,洪,龚,汤,"
                + "陶,黎,温,莫,易,樊,乔,文,安,殷,颜,庄,章,鲁,倪,庞,邢,俞,翟,蓝,聂,齐,向,申,葛,岳";
        String[] names = StringUtils.splitByWholeSeparator(dictionary, ",");
        return mock(names);
    }

    public static String anyCBoyFirstName() {
        int count = RandomUtils.nextInt(2, 4);

        if (2 == count) {
            String secondDictionary = "睿,浩,博,瑞,昊,杰,刚,伟,勇,林,驰,俊,明,清,正,云,鹏,晨,强";
            String[] names = StringUtils.splitByWholeSeparator(secondDictionary, ",");
            return mock(names);
        } else if (3 == count) {
            String secondDictionary = "子,梓,浩,宇,俊,文,志,晓,小,大";
            String thirdDictionary = "轩,宇,泽,杰,豪,刚,伟,勇,明,然,涵,蔼,仁,容,德,轩,贤,良,伦,正,清,义,诚,直,道,达,耀,兴,荣,华,丰,余,昌,盛,涛";
            String[] seconds = StringUtils.splitByWholeSeparator(secondDictionary, ",");
            String[] thirds = StringUtils.splitByWholeSeparator(thirdDictionary, ",");
            return mock(seconds) + mock(thirds);
        } else {
            return "";
        }
    }

    public static String anyCGirlFirstName() {
        int count = RandomUtils.nextInt(2, 4);

        if (2 == count) {
            String secondDictionary = "悦,妍,玥,蕊,欣,洁,雪,静,慧,晴,娜,玟,倩,柔,雅,丽,萍,娟";
            String[] names = StringUtils.splitByWholeSeparator(secondDictionary, ",");
            return mock(names);
        } else if (3 == count) {
            String secondDictionary = "雨,梓,欣,子,语,馨,思,婉,涵,婷,文,梦,玉,安";
            String thirdDictionary = "涵,萱,怡,彤,琪,文,宁,雪,彤,柔,雅,丽,曼,云,晴,琴,娜";
            String[] seconds = StringUtils.splitByWholeSeparator(secondDictionary, ",");
            String[] thirds = StringUtils.splitByWholeSeparator(thirdDictionary, ",");
            return mock(seconds) + mock(thirds);
        } else {
            return "";
        }
    }

    public static String anyCBoyName() {
        return anyCLastName() + anyCBoyFirstName();
    }

    public static String anyCGirlName() {
        return anyCLastName() + anyCGirlFirstName();
    }

    public static String anyCName() {
        boolean isGirl = RandomUtils.nextBoolean();
        return anyCName(isGirl);
    }

    public static String anyCName(boolean isGirl) {
        if (isGirl) {
            return anyCGirlName();
        } else {
            return anyCBoyName();
        }
    }

    public static String anyLetterString(int minLen, int maxLen) {
        return RandomStringUtils.randomAlphabetic(minLen, maxLen);
    }

    /**
     * 随机获取任意简体字符
     *
     * @see <a href="https://baike.baidu.com/item/信息交换用汉字编码字符集?fromtitle=GB2312&fromid=483170">百度词条-GB2312</a>
     */
    public static String anySimpleCLetter() {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) RandomUtil.nextInt(0xB0, 0xF7);
        bytes[1] = (byte) RandomUtil.nextInt(0xA1, 0xFF);

        try {
            return new String(bytes, "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String anySimpleCLetterString(int minLen, int maxLen) {
        int count = RandomUtils.nextInt(minLen, maxLen);
        StringBuilder sb = new StringBuilder();
        for (char index = 0; index < count; index++) {
            sb.append(anySimpleCLetter());
        }
        return sb.toString();
    }

    public static String anyCLetterString(int minLen, int maxLen) {
        char begin = '\u4E00';
        char end = '\u9FA5';
        StringBuilder sb = new StringBuilder();
        for (int index = begin; index <= end; index++) {
            sb.append(index);
        }

        int count = RandomUtils.nextInt(minLen, maxLen);
        return RandomStringUtils.random(count, sb.toString());
    }

    public static String anyNumString(int minLen, int maxLen) {
        return RandomStringUtils.randomAlphanumeric(minLen, maxLen);
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

}



