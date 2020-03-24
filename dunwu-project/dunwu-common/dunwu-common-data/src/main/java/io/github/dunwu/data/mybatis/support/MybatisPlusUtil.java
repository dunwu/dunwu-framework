package io.github.dunwu.data.mybatis.support;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.dunwu.common.Pagination;
import io.github.dunwu.data.DataOrderItem;
import io.github.dunwu.data.OrderType;
import io.github.dunwu.data.QueryRequest;
import io.github.dunwu.tool.collection.CollectionUtil;

import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-07
 */
public class MybatisPlusUtil {

    public static <T> Page<T> transToMybatisPlusPage(QueryRequest<T> query) {
        Page<T> page = new Page<>(query.getPage().getCurrent(), query.getPage().getSize(), true);
        if (CollectionUtil.isNotEmpty(query.getOrders())) {
            for (DataOrderItem item : query.getOrders()) {
                if (OrderType.ASC.equals(item.getType())) {
                    page.addOrder(OrderItem.asc(item.getColumn()));
                } else {
                    page.addOrder(OrderItem.desc(item.getColumn()));
                }
            }
        }
        return page;
    }

    public static Page<Map<String, Object>> transToMybatisPlusMapPage(QueryRequest<?> query) {
        Page<Map<String, Object>> page = new Page<>(query.getPage().getCurrent(), query.getPage().getSize(), true);
        if (CollectionUtil.isNotEmpty(query.getOrders())) {
            for (DataOrderItem item : query.getOrders()) {
                if (OrderType.ASC.equals(item.getType())) {
                    page.addOrder(OrderItem.asc(item.getColumn()));
                } else {
                    page.addOrder(OrderItem.desc(item.getColumn()));
                }
            }
        }
        return page;
    }

    public static <T> Pagination<T> transToPagination(IPage<T> page) {
        return new Pagination<>(page.getCurrent(), page.getSize(), page.getTotal(), page.getRecords());
    }

}
