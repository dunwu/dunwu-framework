package io.github.dunwu.data.util;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.github.dunwu.core.Pagination;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-07
 */
public class PageUtil {

    public static <S, T> IPage<T> transToMybatisPlusPage(Pagination<S> page) {
        return new Page<T>(page.getCurrent(), page.getSize(), page.getTotal());
    }

    public static <T> Pagination<T> transToPagination(IPage<T> page) {
        return new Pagination<>(page.getCurrent(), page.getSize(), page.getTotal(),
            page.getRecords());
    }

}
