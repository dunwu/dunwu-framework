package io.github.dunwu.tool.data;

import io.github.dunwu.tool.data.constant.enums.OrderType;
import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
@ToString
@Accessors(chain = true)
public class DataOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 需要进行排序的字段
     */
    private String column;

    /**
     * 是否正序排列，默认 ASC
     */
    private OrderType type = OrderType.ASC;

    public static List<DataOrderItem> ascs(String... columns) {
        return Arrays.stream(columns)
                     .map(DataOrderItem::asc)
                     .collect(Collectors.toList());
    }

    public static DataOrderItem asc(String column) {
        return build(column, OrderType.ASC);
    }

    private static DataOrderItem build(String column, OrderType type) {
        DataOrderItem item = new DataOrderItem();
        item.setColumn(column);
        item.setType(type);
        return item;
    }

    public static List<DataOrderItem> descs(String... columns) {
        return Arrays.stream(columns)
                     .map(DataOrderItem::desc)
                     .collect(Collectors.toList());
    }

    public static DataOrderItem desc(String column) {
        return build(column, OrderType.DESC);
    }

}
