package io.github.dunwu.common.dal.domain;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 排序实体
 * <p>
 * 用于封装排序字段和排序方向，支持解析字符串表达式（如 "id,asc"） 以及与 Spring Data 的 {@link Sort.Order} 进行互转。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-08-22
 */
@Data
@Builder
public class Order implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    /**
     * 排序字段
     */
    private String field;

    /**
     * 是否为正序排序
     */
    private Direction direction;

    /**
     * 默认构造方法
     */
    public Order() { }

    /**
     * 构造方法
     *
     * @param field     排序字段
     * @param direction 排序方向
     */
    public Order(String field, Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    /**
     * 排序方向枚举
     */
    public enum Direction {
        /** 升序（ASCENDING） */
        asc,
        /** 降序（DESCENDING） */
        desc;
    }

    /**
     * 比较两个 Order 对象是否相等
     * <p>
     * 仅比较排序字段是否相同
     *
     * @param o 比较对象
     * @return 如果相等返回 true，否则返回 false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order)o;
        return Objects.equals(field, order.field);
    }

    /**
     * 计算哈希值
     * <p>
     * 基于排序字段计算
     *
     * @return 哈希值
     */
    @Override
    public int hashCode() {
        return Objects.hash(field);
    }

    /**
     * 返回排序表达式的字符串表示
     * <p>
     * 格式为 "字段,方向"，例如 "id,asc"
     *
     * @return 字符串表示
     */
    @Override
    public String toString() {
        return this.field + "," + this.direction.name();
    }

    /**
     * 获取排序子句
     * <p>
     * 格式为 "字段,方向"
     *
     * @return 排序子句字符串
     */
    public String getClause() {
        return this.getField() + "," + this.getDirection().name();
    }

    /**
     * 解析排序表达式字符串为 {@link Order} 对象
     *
     * @param expression 排序表达式，格式为 "字段,方向"，例如 "id,asc"
     * @return Order 对象，如果解析失败或格式不正确返回 null
     */
    public static Order parse(String expression) {
        if (StrUtil.isBlank(expression)) {
            return null;
        }

        List<String> list = StrUtil.split(expression, ',');
        if (CollectionUtil.isEmpty(list) || list.size() != 2) {
            return null;
        }

        String field = list.get(0);
        Direction direction = Direction.valueOf(list.get(1));
        return new Order(field, direction);
    }

    /**
     * 将 Spring Data 的 {@link Sort.Order} 转换为 {@link Order} 对象
     *
     * @param order Spring Data 的排序对象
     * @return Order 对象，如果输入为 null 返回 null
     */
    public static Order parse(Sort.Order order) {
        if (order == null) {
            return null;
        }

        Direction direction;
        if (order.getDirection().isAscending()) {
            direction = Direction.asc;
        } else {
            direction = Direction.desc;
        }
        return new Order(order.getProperty(), direction);
    }

}
