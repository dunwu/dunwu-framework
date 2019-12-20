package io.github.dunwu.tool.util;

import io.github.dunwu.tool.collection.CollectionUtil;
import io.github.dunwu.tool.date.DateField;
import io.github.dunwu.tool.date.DatePattern;
import io.github.dunwu.tool.date.DateTime;
import io.github.dunwu.tool.date.DateUtil;
import io.github.dunwu.tool.exceptions.UtilException;
import io.github.dunwu.tool.lang.WeightRandom;

import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 随机工具类
 *
 * @author xiaoleilu
 */
public class RandomUtil {

    /**
     * 数字全集
     */
    public static final String NUMBER_SAMPLE = "0123456789";
    /**
     * 小写英文字母全集
     */
    public static final String EN_LOWER_CHAR_SAMPLE = "abcdefghijklmnopqrstuvwxyz";
    /**
     * 大写英文字母全集
     */
    public static final String EN_UPPER_CHAR_SAMPLE = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    /**
     * 英文字母全集
     */
    public static final String EN_CHAR_SAMPLE = StringUtil.join("", EN_LOWER_CHAR_SAMPLE, EN_UPPER_CHAR_SAMPLE);
    /**
     * 用于随机选的字符和数字
     */
    public static final String WORD_SAMPLE = StringUtil.join("", EN_CHAR_SAMPLE, NUMBER_SAMPLE);

    /**
     * 创建{@link SecureRandom}，类提供加密的强随机数生成器 (RNG)<br>
     *
     * @param seed 自定义随机种子
     * @return {@link SecureRandom}
     * @since 4.6.5
     */
    public static SecureRandom createSecureRandom(byte[] seed) {
        return (null == seed) ? new SecureRandom() : new SecureRandom(seed);
    }

    /**
     * 获取随机数产生器
     *
     * @param isSecure 是否为强随机数生成器 (RNG)
     * @return {@link Random}
     * @see #getSecureRandom()
     * @see #getRandom()
     * @since 4.1.15
     */
    public static Random getRandom(boolean isSecure) {
        return isSecure ? getSecureRandom() : getRandom();
    }

    /**
     * 获取随机数生成器对象<br> ThreadLocalRandom是JDK 7之后提供并发产生随机数，能够解决多个线程发生的竞争争夺。
     *
     * @return {@link ThreadLocalRandom}
     * @since 3.1.2
     */
    public static ThreadLocalRandom getRandom() {
        return ThreadLocalRandom.current();
    }

    /**
     * 获取{@link SecureRandom}，类提供加密的强随机数生成器 (RNG)<br> 注意：此方法获取的是伪随机序列发生器PRNG（pseudo-random number generator）
     *
     * <p>
     * 相关说明见：https://stackoverflow.com/questions/137212/how-to-solve-slow-java-securerandom
     *
     * @return {@link SecureRandom}
     * @since 3.1.2
     */
    public static SecureRandom getSecureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            throw new UtilException(e);
        }
    }

    /**
     * 获得指定范围内的随机数[0, 1)
     *
     * @return 随机数
     * @since 4.0.9
     */
    public static BigDecimal randomBigDecimal() {
        return NumberUtil.toBigDecimal(getRandom().nextDouble());
    }

    /**
     * 获得指定范围内的随机数 [0,limit)
     *
     * @param limit 最大数（不包含）
     * @return 随机数
     * @since 4.0.9
     */
    public static BigDecimal randomBigDecimal(BigDecimal limit) {
        return NumberUtil.toBigDecimal(getRandom().nextDouble(limit.doubleValue()));
    }

    /**
     * 获得指定范围内的随机数
     *
     * @param min 最小数（包含）
     * @param max 最大数（不包含）
     * @return 随机数
     * @since 4.0.9
     */
    public static BigDecimal randomBigDecimal(BigDecimal min, BigDecimal max) {
        return NumberUtil.toBigDecimal(getRandom().nextDouble(min.doubleValue(), max.doubleValue()));
    }

    /**
     * 获得随机Boolean值
     *
     * @return true or false
     * @since 4.5.9
     */
    public static boolean randomBoolean() {
        return 0 == randomInt(2);
    }

    /**
     * 获得指定范围内的随机数 [0,limit)
     *
     * @param limit 限制随机数的范围，不包括这个数
     * @return 随机数
     */
    public static int randomInt(int limit) {
        return getRandom().nextInt(limit);
    }

    /**
     * 随机bytes
     *
     * @param length 长度
     * @return bytes
     */
    public static byte[] randomBytes(int length) {
        byte[] bytes = new byte[length];
        getRandom().nextBytes(bytes);
        return bytes;
    }

    /**
     * 随机字母或数字，小写
     *
     * @return 随机字符
     * @since 3.1.2
     */
    public static char randomChar() {
        return randomChar(WORD_SAMPLE);
    }

    /**
     * 生成随机颜色
     *
     * @return 随机颜色
     * @since 4.1.5
     */
    public static Color randomColor() {
        final Random random = getRandom();
        return new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255));
    }

    /**
     * 以当天为基准，随机产生一个日期
     *
     * @param min 偏移最小天，可以为负数表示过去的时间（包含）
     * @param max 偏移最大天，可以为负数表示过去的时间（不包含）
     * @return 随机日期（随机天，其它时间不变）
     * @since 4.0.8
     */
    public static DateTime randomDay(int min, int max) {
        return randomDate(DateUtil.toDateTime(), DateField.DAY_OF_YEAR, min, max);
    }

    /**
     * 以给定日期为基准，随机产生一个日期
     *
     * @param baseDate  基准日期
     * @param dateField 偏移的时间字段，例如时、分、秒等
     * @param min       偏移最小量，可以为负数表示过去的时间（包含）
     * @param max       偏移最大量，可以为负数表示过去的时间（不包含）
     * @return 随机日期
     * @since 4.5.8
     */
    public static DateTime randomDate(Date baseDate, DateField dateField, int min, int max) {
        if (null == baseDate) {
            baseDate = DateUtil.toDateTime();
        }

        return DateUtil.offset(baseDate, dateField, randomInt(min, max));
    }

    /**
     * 获得指定范围内的随机数
     *
     * @param min 最小数（包含）
     * @param max 最大数（不包含）
     * @return 随机数
     */
    public static int randomInt(int min, int max) {
        return getRandom().nextInt(min, max);
    }

    /**
     * 获得指定范围内的随机数
     *
     * @param min          最小数（包含）
     * @param max          最大数（不包含）
     * @param scale        保留小数位数
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 随机数
     * @since 4.0.8
     */
    public static double randomDouble(double min, double max, int scale, RoundingMode roundingMode) {
        return NumberUtil.round(randomDouble(min, max), scale, roundingMode).doubleValue();
    }

    /**
     * 获得指定范围内的随机数
     *
     * @param min 最小数（包含）
     * @param max 最大数（不包含）
     * @return 随机数
     * @since 3.3.0
     */
    public static double randomDouble(double min, double max) {
        return getRandom().nextDouble(min, max);
    }

    /**
     * 获得指定范围内的随机数
     *
     * @param scale        保留小数位数
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 随机数
     * @since 4.0.8
     */
    public static double randomDouble(int scale, RoundingMode roundingMode) {
        return NumberUtil.round(randomDouble(), scale, roundingMode).doubleValue();
    }

    /**
     * 获得随机数[0, 1)
     *
     * @return 随机数
     * @since 3.3.0
     */
    public static double randomDouble() {
        return getRandom().nextDouble();
    }

    /**
     * 获得指定范围内的随机数
     *
     * @param limit        限制随机数的范围，不包括这个数
     * @param scale        保留小数位数
     * @param roundingMode 保留小数的模式 {@link RoundingMode}
     * @return 随机数
     * @since 4.0.8
     */
    public static double randomDouble(double limit, int scale, RoundingMode roundingMode) {
        return NumberUtil.round(randomDouble(limit), scale, roundingMode).doubleValue();
    }

    /**
     * 获得指定范围内的随机数 [0,limit)
     *
     * @param limit 限制随机数的范围，不包括这个数
     * @return 随机数
     * @since 3.3.0
     */
    public static double randomDouble(double limit) {
        return getRandom().nextDouble(limit);
    }

    /**
     * 随机获得列表中的元素
     *
     * @param <T>  元素类型
     * @param list 列表
     * @return 随机元素
     */
    public static <T> T randomEle(List<T> list) {
        return randomEle(list, list.size());
    }

    /**
     * 随机获得列表中的元素
     *
     * @param <T>   元素类型
     * @param list  列表
     * @param limit 限制列表的前N项
     * @return 随机元素
     */
    public static <T> T randomEle(List<T> list, int limit) {
        return list.get(randomInt(limit));
    }

    /**
     * 随机获得数组中的元素
     *
     * @param <T>   元素类型
     * @param array 列表
     * @return 随机元素
     * @since 3.3.0
     */
    public static <T> T randomEle(T[] array) {
        return randomEle(array, array.length);
    }

    /**
     * 随机获得数组中的元素
     *
     * @param <T>   元素类型
     * @param array 列表
     * @param limit 限制列表的前N项
     * @return 随机元素
     * @since 3.3.0
     */
    public static <T> T randomEle(T[] array, int limit) {
        return array[randomInt(limit)];
    }

    /**
     * 随机获得列表中的一定量的不重复元素，返回Set
     *
     * @param <T>        元素类型
     * @param collection 列表
     * @param count      随机取出的个数
     * @return 随机元素
     * @throws IllegalArgumentException 需要的长度大于给定集合非重复总数
     */
    public static <T> Set<T> randomEleSet(Collection<T> collection, int count) {
        final ArrayList<T> source = CollectionUtil.distinct(collection);
        if (count > source.size()) {
            throw new IllegalArgumentException("Count is larger than collection distinct size !");
        }

        final HashSet<T> result = new HashSet<>(count);
        int limit = source.size();
        while (result.size() < count) {
            result.add(randomEle(source, limit));
        }

        return result;
    }

    /**
     * 随机获得列表中的一定量元素
     *
     * @param <T>   元素类型
     * @param list  列表
     * @param count 随机取出的个数
     * @return 随机元素
     */
    public static <T> List<T> randomEles(List<T> list, int count) {
        final List<T> result = new ArrayList<>(count);
        int limit = list.size();
        while (result.size() < count) {
            result.add(randomEle(list, limit));
        }

        return result;
    }

    /**
     * 获得随机数[0, 2^32)
     *
     * @return 随机数
     */
    public static int randomInt() {
        return getRandom().nextInt(0, Integer.MAX_VALUE);
    }

    /**
     * 获得指定范围内的随机数[min, max)
     *
     * @param min 最小数（包含）
     * @param max 最大数（不包含）
     * @return 随机数
     * @since 3.3.0
     */
    public static long randomLong(long min, long max) {
        return getRandom().nextLong(min, max);
    }

    /**
     * 获得随机数
     *
     * @return 随机数
     * @since 3.3.0
     */
    public static long randomLong() {
        return getRandom().nextLong(0, Long.MAX_VALUE);
    }

    /**
     * 获得指定范围内的随机数 [0,limit)
     *
     * @param limit 限制随机数的范围，不包括这个数
     * @return 随机数
     */
    public static long randomLong(long limit) {
        return getRandom().nextLong(limit);
    }

    /**
     * 随机数字，数字为0~9单个数字
     *
     * @return 随机数字字符
     * @since 3.1.2
     */
    public static int randomNumber() {
        return randomChar(NUMBER_SAMPLE);
    }

    /**
     * 随机字符
     *
     * @param baseString 随机字符选取的样本
     * @return 随机字符
     * @since 3.1.2
     */
    public static char randomChar(String baseString) {
        return baseString.charAt(randomInt(baseString.length()));
    }

    /**
     * 获得一个只包含数字的字符串
     *
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String randomNumbers(int length) {
        return randomString(NUMBER_SAMPLE, length);
    }

    public static String randomString(final String sample, final int min, final int max) {
        if (StringUtil.isEmpty(sample)) {
            return StringUtil.EMPTY;
        }

        if (min < 0 || max < 0 || max < min) {
            return StringUtil.EMPTY;
        }

        int length = getRandom().nextInt(min, max);
        final StringBuilder sb = new StringBuilder(length);
        int sampleLength = sample.length();
        for (int i = 0; i < length; i++) {
            int number = randomInt(sampleLength);
            sb.append(sample.charAt(number));
        }
        return sb.toString();
    }

    /**
     * 获得一个随机的字符串
     *
     * @param sample 随机字符选取的样本
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String randomString(final String sample, int length) {
        return randomString(sample, 0, length);
    }

    public static String randomString(final int min, final int max) {
        return randomString(WORD_SAMPLE, min, max);
    }

    /**
     * 获得一个随机的字符串（只包含数字和字符）
     *
     * @param length 字符串的长度
     * @return 随机字符串
     */
    public static String randomString(int length) {
        return randomString(WORD_SAMPLE, 0, length);
    }

    public static String randomNumString(final int min, final int max) {
        return randomString(NUMBER_SAMPLE, min, max);
    }

    /**
     * 获得一个随机的字符串（只包含数字和大写字符）
     *
     * @param length 字符串的长度
     * @return 随机字符串
     * @since 4.0.13
     */
    public static String randomStringUpper(int length) {
        return randomString(WORD_SAMPLE, length).toUpperCase();
    }

    /**
     * 获得一个随机的字符串（只包含数字和字符） 并排除指定字符串
     *
     * @param length   字符串的长度
     * @param elemData 要排除的字符串
     * @return 随机字符串
     */
    public static String randomStringWithoutStr(int length, String elemData) {
        String baseStr = WORD_SAMPLE;
        baseStr = StringUtil.removeAll(baseStr, elemData.toCharArray());
        return randomString(baseStr, length);
    }

    /**
     * 带有权重的随机生成器
     *
     * @param <T>        随机对象类型
     * @param weightObjs 带有权重的对象列表
     * @return {@link WeightRandom}
     * @since 4.0.3
     */
    public static <T> WeightRandom<T> weightRandom(WeightRandom.WeightObj<T>[] weightObjs) {
        return new WeightRandom<>(weightObjs);
    }

    /**
     * 带有权重的随机生成器
     *
     * @param <T>        随机对象类型
     * @param weightObjs 带有权重的对象列表
     * @return {@link WeightRandom}
     * @since 4.0.3
     */
    public static <T> WeightRandom<T> weightRandom(Iterable<WeightRandom.WeightObj<T>> weightObjs) {
        return new WeightRandom<>(weightObjs);
    }

    /**
     * 返回无锁的 ThreadLocalRandom
     */
    public static ThreadLocalRandom threadLocalRandom() {
        return ThreadLocalRandom.current();
    }

    public static String randomBoyFirstName() {
        String dictionary =
            "Dylan,Jacob,Ethan,Gabriel,Nicholas,Jack,Joshua,Caleb,Ryan,Andrew,Caden,Tyler,Michael,Jaden,Zachary,"
                + "Connor,Logan,Aiden,Noah,Alexande,Jackson,Brayden,Lucas,William,Nathan,Joseph,Justin,Daniel,"
                + "Benjamin,Christopher,James,Gavin,Evan,Austin,Cameron,Brandon,Mason,Luke,Anthony,Christian,"
                + "Jonathan,Owen,David,John,Matthew,Samuel,Sean,Hunter,Elijah,Thomas";
        String[] names = StringUtil.split(dictionary, ",");
        return randomInArray(names);
    }

    public static String randomChineseBoyFirstName() {
        int count = randomInt(2, 4);

        if (2 == count) {
            String secondDictionary = "睿,浩,博,瑞,昊,杰,刚,伟,勇,林,驰,俊,明,清,正,云,鹏,晨,强";
            String[] names = StringUtil.split(secondDictionary, ",");
            return randomInArray(names);
        } else if (3 == count) {
            String secondDictionary = "子,梓,浩,宇,俊,文,志,晓,小,大";
            String thirdDictionary = "轩,宇,泽,杰,豪,刚,伟,勇,明,然,涵,蔼,仁,容,德,轩,贤,良,伦,正,清,义,诚,直,道,达,耀,兴,荣,华,丰,余,昌,盛,涛";
            String[] seconds = StringUtil.split(secondDictionary, ",");
            String[] thirds = StringUtil.split(thirdDictionary, ",");
            return randomInArray(seconds) + randomInArray(thirds);
        } else {
            return "";
        }
    }

    public static String randomChineseBoyName() {
        return randomChineseLastName() + randomChineseBoyFirstName();
    }

    public static String randomChineseGirlFirstName() {
        int count = randomInt(2, 4);

        if (2 == count) {
            String secondDictionary = "悦,妍,玥,蕊,欣,洁,雪,静,慧,晴,娜,玟,倩,柔,雅,丽,萍,娟";
            String[] names = StringUtil.split(secondDictionary, ",");
            return randomInArray(names);
        } else if (3 == count) {
            String secondDictionary = "雨,梓,欣,子,语,馨,思,婉,涵,婷,文,梦,玉,安";
            String thirdDictionary = "涵,萱,怡,彤,琪,文,宁,雪,彤,柔,雅,丽,曼,云,晴,琴,娜";
            String[] seconds = StringUtil.split(secondDictionary, ",");
            String[] thirds = StringUtil.split(thirdDictionary, ",");
            return randomInArray(seconds) + randomInArray(thirds);
        } else {
            return "";
        }
    }

    public static String randomChineseGirlName() {
        return randomChineseLastName() + randomChineseGirlFirstName();
    }

    public static String randomChineseLastName() {
        String dictionary =
            "李,王,张,刘,陈,杨,黄,赵,周,吴,徐,孙,朱,马,胡,郭,林,何,高,梁,郑,罗,宋,谢,唐,韩,曹,许,邓,萧,冯,曾,程,蔡,彭,潘,袁,于,董,余,苏,叶,吕,魏,蒋,田,杜,丁,沈,姜,范,江,"
                + "傅,钟,卢,汪,戴,崔,任,陆,廖,姚,方,金,邱,夏,谭,韦,贾,邹,石,熊,孟,秦,阎,薛,侯,雷,白,龙,段,郝,孔,邵,史,毛,常,万,顾,赖,武,康,贺,严,尹,钱,施,牛,洪,龚,汤,"
                + "陶,黎,温,莫,易,樊,乔,文,安,殷,颜,庄,章,鲁,倪,庞,邢,俞,翟,蓝,聂,齐,向,申,葛,岳";
        String[] names = StringUtil.split(dictionary, ",");
        return randomInArray(names);
    }

    public static String randomChineseLetterString(final int min, final int max) {
        char begin = '\u4E00';
        char end = '\u9FA5';
        StringBuilder sb = new StringBuilder();
        for (int index = begin; index <= end; index++) {
            sb.append(index);
        }

        int count = randomInt(min, max);
        return randomString(sb.toString(), count);
    }

    public static String randomChineseName() {
        boolean isGirl = randomBoolean();
        return randomChineseName(isGirl);
    }

    public static String randomChineseName(final boolean isGirl) {
        if (isGirl) {
            return randomChineseGirlName();
        } else {
            return randomChineseBoyName();
        }
    }

    public static String randomChineseSimpleLetterString(final int min, final int max) {
        int count = randomInt(min, max);
        StringBuilder sb = new StringBuilder();
        for (char index = 0; index < count; index++) {
            sb.append(randomChineseSimpleLetter());
        }
        return sb.toString();
    }

    /**
     * 随机获取任意简体字符
     *
     * @see <a href= "https://baike.baidu.com/item/信息交换用汉字编码字符集?fromtitle=GB2312&fromid=483170">百度词条-GB2312</a>
     */
    public static String randomChineseSimpleLetter() {
        byte[] bytes = new byte[2];
        bytes[0] = (byte) randomInt(0xB0, 0xF7);
        bytes[1] = (byte) randomInt(0xA1, 0xFF);

        try {
            return new String(bytes, "GB2312");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String randomDate(final Date min, final Date max) {
        return randomDate(min, max, DatePattern.NORM_DATETIME_PATTERN);
    }

    public static String randomDate(final Date min, final Date max,
        final String pattern) {
        return randomDate(DateUtil.toLocalDateTime(min),
            DateUtil.toLocalDateTime(max), pattern);
    }

    public static String randomDate(final LocalDateTime min, final LocalDateTime max,
        final String pattern) {
        long minSeconds = min.toEpochSecond(ZoneOffset.UTC);
        long maxSeconds = max.toEpochSecond(ZoneOffset.UTC);
        long random = ThreadLocalRandom.current().nextLong(minSeconds, maxSeconds);
        LocalDateTime localDate = LocalDateTime.ofEpochSecond(random, 1, ZoneOffset.UTC);
        return localDate.format(DateTimeFormatter.ofPattern(pattern));
    }

    public static Date randomDate(final LocalDateTime min, final LocalDateTime max) {
        long minSeconds = min.toEpochSecond(ZoneOffset.UTC);
        long maxSeconds = max.toEpochSecond(ZoneOffset.UTC);
        long random = ThreadLocalRandom.current().nextLong(minSeconds, maxSeconds);
        LocalDateTime localDate = LocalDateTime.ofEpochSecond(random, 1, ZoneOffset.UTC);
        return DateUtil.toDate(localDate);
    }

    public static String randomEmail() {
        String name = randomString(2, 11);
        return new StringBuilder().append(name.toLowerCase()).append("@")
            .append(randomDomain()).toString();
    }

    public static String randomDomain() {
        String domain1 = randomString(2, 11);
        String domain2 = randomString(2, 4);
        return new StringBuilder().append(domain1.toLowerCase()).append(".")
            .append(domain2.toLowerCase()).toString();
    }

    public static <E extends Enum<E>> E randomEnum(final Class<E> enumClass) {
        List<E> enumList = EnumUtil.getEnumList(enumClass);
        int index = randomInt(0, enumList.size());
        return enumList.get(index);
    }

    public static String randomFirstName() {
        return randomFirstName(randomBoolean());
    }

    public static String randomFirstName(final boolean isGirl) {
        if (isGirl) {
            return randomGirlFirstName();
        } else {
            return randomBoyFirstName();
        }
    }

    public static String randomGirlFirstName() {
        String dictionary =
            "Lily,Emily,Sophia,Isabella,Ava,Emma,Kaitlyn,Hannah,Hailey,Olivia,Sarah,Abigail,Madeline,Madison,Kaylee,"
                + "Ella,Riley,Alexis,Alyssa,Samantha,Lauren,Mia,Grace,Chloe,Ashley,Brianna,Jessica,Elizabeth,Taylor,"
                + "Makayla,Makenzie,Anna,Zoe,Kayla,Sydney,Megan,Natalie,Kylie,ulia,Avery,Katherine,Isabel,Victoria,"
                + "Morgan,Kyra,Jasmine,Allison,Savannah,JRachel,Jordan";
        String[] names = StringUtil.split(dictionary, ",");
        return randomInArray(names);
    }

    public static String randomIpv4() {
        int[][] range = { { 607649792, 608174079 }, // 36.56.0.0-36.63.255.255
            { 1038614528, 1039007743 }, // 61.232.0.0-61.237.255.255
            { 1783627776, 1784676351 }, // 106.80.0.0-106.95.255.255
            { 2035023872, 2035154943 }, // 121.76.0.0-121.77.255.255
            { 2078801920, 2079064063 }, // 123.232.0.0-123.235.255.255
            { -1950089216, -1948778497 }, // 139.196.0.0-139.215.255.255
            { -1425539072, -1425014785 }, // 171.8.0.0-171.15.255.255
            { -1236271104, -1235419137 }, // 182.80.0.0-182.92.255.255
            { -770113536, -768606209 }, // 210.25.0.0-210.47.255.255
            { -569376768, -564133889 }, // 222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = num2ip(range[index][0]
            + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    private static String num2ip(final int ip) {
        int[] b = new int[4];
        String result = "";
        b[0] = (ip >> 24) & 0xff;
        b[1] = ((ip >> 16) & 0xff);
        b[2] = ((ip >> 8) & 0xff);
        b[3] = (ip & 0xff);
        result = Integer.toString(b[0]) + "." + Integer.toString(b[1]) + "."
            + Integer.toString(b[2]) + "." + Integer.toString(b[3]);
        return result;
    }

    public static String randomLastName() {
        String dictionary =
            "Smith,Jones,Williams,Taylor,Brown,Davies,Evans,Wilson,Thomas,Johnson,Roberts,Robinson,Thompson,Wright,"
                + "Walker,White,Edwards,Hughes,Green,Hall,Lewis,Harris,Clarke,Patel,Jackson";
        String[] names = StringUtil.split(dictionary, ",");
        return randomInArray(names);
    }

    public static <T> T randomInArray(final T[] list) {
        return randomInList(Arrays.asList(list));
    }

    public static <T> T randomInList(final List<T> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        int index = randomInt(0, list.size());
        return list.get(index);
    }

    public static String randomMac() {
        Random random = new Random();
        String[] mac = { String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)) };
        return String.join("", mac);
    }

    public static String randomMac(final String separator) {
        Random random = new Random();
        String[] mac = { String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)),
            String.format("%02x", random.nextInt(0xff)) };
        return String.join(separator, mac);
    }

    /**
     * 使用性能更好的SHA1PRNG, Tomcat的sessionId生成也用此算法. 但JDK7中，需要在启动参数加入 -Djava.security=file:/dev/./urandom （中间那个点很重要）
     * 详见：《SecureRandom的江湖偏方与真实效果》http://calvin1978.blogcn.com/articles/securerandom.html
     */
    public static SecureRandom secureRandom() {
        try {
            return SecureRandom.getInstance("SHA1PRNG");
        } catch (NoSuchAlgorithmException e) {
            return new SecureRandom();
        }
    }

}
