package io.github.dunwu.tool.data;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.domain.Sort;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * 排序实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-08-22
 */
@Data
@Builder
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 排序字段
     */
    private String field;

    /**
     * 是否为正序排序
     */
    private Direction direction;

    public Order() { }

    public Order(String field, Direction direction) {
        this.field = field;
        this.direction = direction;
    }

    public enum Direction {
        asc,
        desc;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Order order = (Order) o;
        return Objects.equals(field, order.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(field);
    }

    @Override
    public String toString() {
        return this.field + "," + this.direction.name();
    }

    public String getClause() {
        return this.getField() + "," + this.getDirection().name();
    }

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
