package io.github.dunwu.data;

import lombok.Data;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.data.domain.Pageable;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 基础查询实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-24
 */
@Data
@ToString
@Accessors(chain = true)
public class QueryRequest<T> implements Serializable {

    private static final long serialVersionUID = 7704551086334591374L;

    /**
     * 分页查询条件
     */
    private Pageable page;

    /**
     * 排序字段
     */
    private List<DataOrderItem> orders;

    /**
     * 数据实体
     */
    private T entity;

    public static <T> QueryRequest<T> build(T entity, Pageable page) {
        QueryRequest<T> request = new QueryRequest<T>();
        request.setEntity(entity);
        request.setPage(page);
        request.setOrders(Collections.emptyList());
        return request;
    }

    public static <T> QueryRequest<T> build(T entity, Pageable page, List<DataOrderItem> orders) {
        QueryRequest<T> request = new QueryRequest<T>();
        request.setEntity(entity);
        request.setPage(page);
        request.setOrders(orders);
        return request;
    }

}
