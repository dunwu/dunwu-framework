package io.github.dunwu.util.code;

import io.github.dunwu.util.number.RandomUtil;

import java.security.SecureRandom;
import java.util.UUID;

/**
 * 生成唯一性 ID 算法的工具类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-12
 */
public class IdUtil {

    /**
     * 生成 UUID，含 - 字符
     *
     * @return String
     */
    public static String randomUuid() {
        return UUID.randomUUID().toString();
    }

    /**
     * 生成 UUID，不含 - 字符
     *
     * @return String
     */
    public static String uuid2() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * 使用 ThreadLocalRandom 随机生成 Long.
     */
    public static long randomLong() {
        return RandomUtil.nextLong();
    }

    public static String randomId8() {
        int hashCode = UUID.randomUUID().toString().hashCode();
        if (hashCode < 0) {
            hashCode = -hashCode;
        }
        return String.format("%x", hashCode);
    }

    /**
     * 基于 encodeBase64UrlSafe 随机生成bytes.
     */
    public static String randomBase64(int length) {
        byte[] randomBytes = new byte[length];
        new SecureRandom().nextBytes(randomBytes);
        return EncodeUtil.encodeBase64UrlSafe(randomBytes);
    }

    /**
     * 分布式 ID - snowflake算法
     *
     * @link http://www.imooc.com/article/285273?block_id=tuijian_wz
     */
    public static class DistributedId {
        private long workerId; // 这个就是代表了机器id
        private long datacenterId; // 这个就是代表了机房id
        private long sequence; // 这个就是代表了一毫秒内生成的多个id的最新序号

        public DistributedId(long workerId, long datacenterId, long sequence) {

            // sanity check for workerId
            // 这儿就不检查了，要求就是你传递进来的机房id和机器id不能超过32，不能小于0
            if (workerId > maxWorkerId || workerId < 0) {
                throw new IllegalArgumentException(
                    String.format("worker Id can't be greater than %d or less than 0", maxWorkerId));
            }

            if (datacenterId > maxDatacenterId || datacenterId < 0) {

                throw new IllegalArgumentException(
                    String.format("datacenter Id can't be greater than %d or less than 0", maxDatacenterId));
            }

            this.workerId = workerId;
            this.datacenterId = datacenterId;
            this.sequence = sequence;
        }

        private long twepoch = 1288834974657L;
        private long workerIdBits = 5L;
        private long datacenterIdBits = 5L;

        // 这个是二进制运算，就是5 bit最多只能有31个数字，也就是说机器id最多只能是32以内
        private long maxWorkerId = -1L ^ (-1L << workerIdBits);

        // 这个是一个意思，就是5 bit最多只能有31个数字，机房id最多只能是32以内
        private long maxDatacenterId = -1L ^ (-1L << datacenterIdBits);
        private long sequenceBits = 12L;

        private long workerIdShift = sequenceBits;
        private long datacenterIdShift = sequenceBits + workerIdBits;
        private long timestampLeftShift = sequenceBits + workerIdBits + datacenterIdBits;
        private long sequenceMask = -1L ^ (-1L << sequenceBits);

        private long lastTimestamp = -1L;

        public long getWorkerId() {
            return workerId;
        }

        public long getDatacenterId() {
            return datacenterId;
        }

        public long getTimestamp() {
            return System.currentTimeMillis();
        }

        // 这个是核心方法，通过调用nextId()方法，让当前这台机器上的snowflake算法程序生成一个全局唯一的id
        public synchronized long nextId() {

            // 这儿就是获取当前时间戳，单位是毫秒
            long timestamp = timeGen();

            if (timestamp < lastTimestamp) {
                System.err.printf("clock is moving backwards. Rejecting requests until %d.", lastTimestamp);
                throw new RuntimeException(
                    String.format("Clock moved backwards. Refusing to generate id for %d milliseconds",
                                  lastTimestamp - timestamp));
            }


            // 下面是说假设在同一个毫秒内，又发送了一个请求生成一个id
            // 这个时候就得把seqence序号给递增1，最多就是4096
            if (lastTimestamp == timestamp) {

                // 这个意思是说一个毫秒内最多只能有4096个数字，无论你传递多少进来，
                //这个位运算保证始终就是在4096这个范围内，避免你自己传递个sequence超过了4096这个范围
                sequence = (sequence + 1) & sequenceMask;

                if (sequence == 0) {
                    timestamp = tilNextMillis(lastTimestamp);
                }

            } else {
                sequence = 0;
            }

            // 这儿记录一下最近一次生成id的时间戳，单位是毫秒
            lastTimestamp = timestamp;

            // 这儿就是最核心的二进制位运算操作，生成一个64bit的id
            // 先将当前时间戳左移，放到41 bit那儿；将机房id左移放到5 bit那儿；将机器id左移放到5 bit那儿；将序号放最后12 bit
            // 最后拼接起来成一个64 bit的二进制数字，转换成10进制就是个long型
            return ((timestamp - twepoch) << timestampLeftShift) | (datacenterId << datacenterIdShift) | (workerId
                << workerIdShift) | sequence;
        }

        private long tilNextMillis(long lastTimestamp) {

            long timestamp = timeGen();

            while (timestamp <= lastTimestamp) {
                timestamp = timeGen();
            }
            return timestamp;
        }

        private long timeGen() {
            return System.currentTimeMillis();
        }
    }
}
