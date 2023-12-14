package io.github.dunwu.tool.data.mybatis;

import cn.hutool.core.collection.CollectionUtil;
import io.github.dunwu.tool.data.request.PageQuery;
import io.github.dunwu.tool.data.util.PageUtil;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

/**
 * {@link IService} 实现类（ 泛型：M 是 mapper 对象，T 是实体 ）
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-04-26
 */
public class ServiceImpl implements IService {

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
