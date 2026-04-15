package io.github.dunwu.common.dal.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.common.dal.domain.PageQuery;
import io.github.dunwu.common.dal.util.PageUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * 服务层通用实现类
 * <p>
 * 实现了 {@link IService} 接口，主要提供分页参数转换等通用功能。
 * 将自定义的 {@link PageQuery} 对象转换为 Spring Data 的 {@link PageRequest}。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-26
 */
public class ServiceImpl implements IService {

    /**
     * 将自定义的分页查询参数转换为 Spring Data 的 {@link PageRequest} 对象
     * <p>
     * 处理逻辑包括：
     * <ul>
     * <li>页码转换：{@link PageQuery} 的页码通常从 1 开始，转换为 Spring Data 的从 0 开始。</li>
     * <li>排序解析：支持多种排序格式的解析。</li>
     * </ul>
     *
     * @param query 自定义分页查询参数
     * @return Spring Data 分页请求对象，如果 query 为 null 则返回 null
     */
    @Override
    public PageRequest toPageRequest(PageQuery query) {

        if (query == null) {
            return null;
        }

        int page = 0;
        if (query.getPage() > 1) {
            page = query.getPage() - 1;
        }

        Sort sort = null;
        if (CollectionUtil.isNotEmpty(query.getSort())) {
            List<String> list = query.getSort();
            if (list.get(0).contains(",")) {
                sort = PageUtil.getSort(list);
            } else {
                sort = getOrdersForOneSort(query.getSort());
            }
        }

        if (sort == null) {
            return PageRequest.of(page, query.getSize());
        }
        return PageRequest.of(page, query.getSize(), sort);
    }

    /**
     * 解析简单的排序参数列表
     * <p>
     * 假设列表格式为 [字段1, 方向1, 字段2, 方向2, ...]。
     *
     * @param list 排序参数字符串列表
     * @return 排序对象 {@link Sort}
     */
    private Sort getOrdersForOneSort(List<String> list) {
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }

        int i = 0;
        List<Sort.Order> orders = new ArrayList<>();
        while (i + 1 < list.size()) {
            Sort.Direction direction = Sort.Direction.fromString(list.get(i + 1));
            Sort.Order order = new Sort.Order(direction, list.get(i));
            orders.add(order);
            i = i + 2;
        }
        return Sort.by(orders);
    }

}
