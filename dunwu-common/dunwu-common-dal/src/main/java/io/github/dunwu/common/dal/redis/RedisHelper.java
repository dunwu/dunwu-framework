package io.github.dunwu.common.dal.redis;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisConnectionUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Redis 操作辅助类
 * <p>
 * 基于 {@link RedisTemplate} 封装了常用的 Redis 数据结构操作，包括 String、Hash、Set、List 等。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-17
 */
public class RedisHelper {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * 构造 RedisHelper
     *
     * @param redisTemplate RedisTemplate 实例
     */
    public RedisHelper(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 获取指定 Key 的过期时间
     *
     * @param key Redis Key，不能为 null
     * @return 过期时间（单位：秒），返回 0 代表永久有效，返回 -1 或 -2 通常代表 Key 不存在或无过期时间
     */
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断 Key 是否存在
     *
     * @param key Redis Key
     * @return 如果存在返回 true，否则返回 false
     */
    public Boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除指定的 Key
     *
     * @param key 一个或多个 Redis Key
     */
    public void del(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete(Arrays.asList(key));
            }
        }
    }

    /**
     * 获取指定 Key 的缓存值
     *
     * @param key Redis Key
     * @return 缓存值，如果 Key 不存在则返回 null
     */
    public Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 设置指定 Key 的缓存值并设置过期时间
     *
     * @param key   Redis Key
     * @param value 缓存值
     * @param time  过期时间（单位：秒），如果小于等于 0 则设置为永久有效
     * @return 如果设置成功返回 true，否则返回 false
     */
    public Boolean set(String key, Object value, Long time) {
        return set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 设置指定 Key 的缓存值并设置过期时间
     *
     * @param key   Redis Key
     * @param value 缓存值
     * @param time  过期时间
     * @param unit  时间单位
     * @return 如果设置成功返回 true，否则返回 false
     */
    public Boolean set(String key, Object value, Long time, TimeUnit unit) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, unit);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置指定 Key 的缓存值（永久有效）
     *
     * @param key   Redis Key
     * @param value 缓存值
     * @return 如果设置成功返回 true，否则返回 false
     */
    public Boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 对指定 Key 的值进行递增操作
     *
     * @param key   Redis Key
     * @param delta 递增步长，必须大于 0
     * @return 递增后的值
     * @throws RuntimeException 如果 delta 小于 0
     */
    public Long incr(String key, Long delta) {
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * 对指定 Key 的值进行递减操作
     *
     * @param key   Redis Key
     * @param delta 递减步长，必须大于 0
     * @return 递减后的值
     * @throws RuntimeException 如果 delta 小于 0
     */
    public Long decr(String key, Long delta) {
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * 获取 Hash 结构中指定字段的值
     *
     * @param key  Redis Key，不能为 null
     * @param item 字段名，不能为 null
     * @return 字段对应的值
     */
    public Object hget(String key, String item) {
        return redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取 Hash 结构中所有字段及其对应的值
     *
     * @param key Redis Key
     * @return 包含所有键值对的 Map
     */
    public Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 批量设置 Hash 结构中的字段值
     *
     * @param key Redis Key
     * @param map 包含字段名和值的 Map
     * @return 如果设置成功返回 true，否则返回 false
     */
    public Boolean hmset(String key, Map<String, Object> map) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 批量设置 Hash 结构中的字段值并设置过期时间
     *
     * @param key  Redis Key
     * @param map  包含字段名和值的 Map
     * @param time 过期时间（单位：秒）
     * @return 如果设置成功返回 true，否则返回 false
     */
    public Boolean hmset(String key, Map<String, Object> map, Long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置指定 Key 的过期时间
     *
     * @param key  Redis Key
     * @param time 过期时间（单位：秒）
     * @return 如果设置成功返回 true，否则返回 false
     */
    public Boolean expire(String key, Long time) {
        return expire(key, time, TimeUnit.SECONDS);
    }

    /**
     * 设置指定 Key 的过期时间
     *
     * @param key      Redis Key
     * @param time     过期时间
     * @param timeUnit 时间单位
     * @return 如果设置成功返回 true，否则返回 false
     */
    public Boolean expire(String key, Long time, TimeUnit timeUnit) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, timeUnit);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置 Hash 结构中指定字段的值，如果字段不存在则创建
     *
     * @param key   Redis Key
     * @param item  字段名
     * @param value 字段值
     * @return 如果设置成功返回 true，否则返回 false
     */
    public Boolean hset(String key, String item, Object value) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 设置 Hash 结构中指定字段的值，如果字段不存在则创建，并设置 Key 的过期时间
     *
     * @param key   Redis Key
     * @param item  字段名
     * @param value 字段值
     * @param time  过期时间（单位：秒）。注意：如果 Key 已存在过期时间，这里将会替换原有时间
     * @return 如果设置成功返回 true，否则返回 false
     */
    public Boolean hset(String key, String item, Object value, Long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除 Hash 结构中的一个或多个字段
     *
     * @param key  Redis Key，不能为 null
     * @param item 一个或多个字段名，不能为 null
     */
    public void hdel(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断 Hash 结构中是否存在指定字段
     *
     * @param key  Redis Key，不能为 null
     * @param item 字段名，不能为 null
     * @return 如果存在返回 true，否则返回 false
     */
    public Boolean hHasKey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * 对 Hash 结构中指定字段的值进行递增操作。如果字段不存在，则创建并初始化为 0 后递增
     *
     * @param key  Redis Key
     * @param item 字段名
     * @param by   递增步长，必须大于 0
     * @return 递增后的值
     */
    public Double hincr(String key, String item, Double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * 对 Hash 结构中指定字段的值进行递减操作
     *
     * @param key  Redis Key
     * @param item 字段名
     * @param by   递减步长（正数），实际执行减法
     * @return 递减后的值
     */
    public Double hdecr(String key, String item, Double by) {
        return redisTemplate.opsForHash().increment(key, item, -by);
    }

    /**
     * 获取 Set 结构中的所有成员
     *
     * @param key Redis Key
     * @return Set 中的所有值，如果发生异常返回 null
     */
    public Set<Object> members(String key) {
        try {
            return redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 判断 Set 结构中是否存在指定值
     *
     * @param key   Redis Key
     * @param value 待检查的值
     * @return 如果存在返回 true，否则返回 false
     */
    public Boolean sHasKey(String key, Object value) {
        try {
            return redisTemplate.opsForSet().isMember(key, value);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向 Set 结构中添加一个或多个值
     *
     * @param key    Redis Key
     * @param values 一个或多个值
     * @return 成功添加的成员数量
     */
    public Long sSet(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().add(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 向 Set 结构中添加一个或多个值，并设置 Key 的过期时间
     *
     * @param key    Redis Key
     * @param time   过期时间（单位：秒）
     * @param values 一个或多个值
     * @return 成功添加的成员数量
     */
    public Long sSetAndTime(String key, Long time, Object... values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0) {
                expire(key, time);
            }
            return count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取 Set 结构中成员的数量
     *
     * @param key Redis Key
     * @return 成员数量
     */
    public Long sGetSetSize(String key) {
        try {
            return redisTemplate.opsForSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 从 Set 结构中移除一个或多个值
     *
     * @param key    Redis Key
     * @param values 一个或多个待移除的值
     * @return 成功移除的成员数量
     */
    public Long setRemove(String key, Object... values) {
        try {
            return redisTemplate.opsForSet().remove(key, values);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 获取 List 结构中指定范围内的元素
     *
     * @param key   Redis Key
     * @param start 开始索引
     * @param end   结束索引。0 到 -1 代表获取所有值
     * @return 指定范围内的元素列表，如果发生异常返回 null
     */
    public List<Object> lGet(String key, Long start, Long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取 List 结构的长度
     *
     * @param key Redis Key
     * @return List 的长度
     */
    public Long lGetListSize(String key) {
        try {
            return redisTemplate.opsForList().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 通过索引获取 List 中的值
     *
     * @param key   Redis Key
     * @param index 索引。index >= 0 时，0 表示表头，1 表示第二个元素，依次类推；index < 0 时，-1 表示表尾，-2 表示倒数第二个元素，依次类推
     * @return 索引对应的值，如果发生异常返回 null
     */
    public Object lGetIndex(String key, Long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将值添加到 List 的尾部
     *
     * @param key   Redis Key
     * @param value 待添加的值
     * @return 如果添加成功返回 true，否则返回 false
     */
    public Boolean lSet(String key, Object value) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将值添加到 List 的尾部，并设置 Key 的过期时间
     *
     * @param key   Redis Key
     * @param value 待添加的值
     * @param time  过期时间（单位：秒）
     * @return 如果添加成功返回 true，否则返回 false
     */
    public Boolean lSet(String key, Object value, Long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将集合中的所有值添加到 List 的尾部
     *
     * @param key   Redis Key
     * @param value 待添加的集合
     * @return 如果添加成功返回 true，否则返回 false
     */
    public Boolean lSet(String key, List<Object> value) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将集合中的所有值添加到 List 的尾部，并设置 Key 的过期时间
     *
     * @param key   Redis Key
     * @param value 待添加的集合
     * @param time  过期时间（单位：秒）
     * @return 如果添加成功返回 true，否则返回 false
     */
    public Boolean lSet(String key, List<Object> value, Long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改 List 中的某条数据
     *
     * @param key   Redis Key
     * @param index 索引
     * @param value 新值
     * @return 如果修改成功返回 true，否则返回 false
     */
    public Boolean lUpdateIndex(String key, Long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除 List 中值为 value 的元素
     *
     * @param key   Redis Key
     * @param count 移除的数量。count > 0 时，从表头开始移除；count < 0 时，从表尾开始移除；count = 0 时，移除所有
     * @param value 待移除的值
     * @return 成功移除的元素数量
     */
    public Long lRemove(String key, Long count, Object value) {
        try {
            return redisTemplate.opsForList().remove(key, count, value);
        } catch (Exception e) {
            e.printStackTrace();
            return 0L;
        }
    }

    /**
     * 使用 SCAN 命令查找匹配模式的 Key
     *
     * @param pattern Key 的匹配模式（支持通配符，如 "user:*"）
     * @return 匹配到的 Key 列表
     */
    public List<String> scan(String pattern) {
        ScanOptions options = ScanOptions.scanOptions().match(pattern).build();
        RedisConnectionFactory factory = redisTemplate.getConnectionFactory();
        RedisConnection rc = Objects.requireNonNull(factory).getConnection();
        Cursor<byte[]> cursor = rc.scan(options);
        List<String> result = new ArrayList<>();
        while (cursor.hasNext()) {
            result.add(new String(cursor.next()));
        }
        try {
            RedisConnectionUtils.releaseConnection(rc, factory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

}
