package io.github.dunwu.tool.data;

import io.github.dunwu.tool.core.constant.enums.ResultStatus;
import io.github.dunwu.tool.core.exception.DefaultException;
import lombok.Data;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * 分页查询包裹实体
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @date 2022-08-22
 */
@Data
@ToString
@SuppressWarnings({ "serial", "unchecked" })
public class PageQueryWrapper<T> extends PageQuery {

    /**
     * 是否分页
     */
    private boolean doPage = true;

    /**
     * 实体
     */
    private T entity;

    /**
     * 实体类型(主要用于确定泛型)
     */
    private Class<T> entityClass;

    public PageQueryWrapper() { }

    public static <T> PageQueryWrapper<T> build(T entity) {
        return build(1, 10, Collections.singletonList("id,asc"), entity, false);
    }

    public static <T> PageQueryWrapper<T> build(int page, int pageSize, List<String> sort, T entity) {
        return build(page, pageSize, sort, entity, false);
    }

    public static <T> PageQueryWrapper<T> build(int page, int pageSize, List<String> sort, T entity, boolean doPage) {
        if (entity == null) {
            throw new DefaultException(ResultStatus.PARAMS_ERROR, "entity 不能为 null");
        }
        return build(doPage, page, pageSize, sort, entity, (Class<T>) entity.getClass());
    }

    public static <T> PageQueryWrapper<T> build(int page, int pageSize, List<String> sort, Class<T> clazz) {
        return build(page, pageSize, sort, clazz, true);
    }

    public static <T> PageQueryWrapper<T> build(int page, int pageSize, List<String> sort, Class<T> clazz,
        boolean doPage) {
        if (clazz == null) {
            throw new DefaultException(ResultStatus.PARAMS_ERROR, "clazz 不能为 null");
        }
        return build(doPage, page, pageSize, sort, null, clazz);
    }

    public static <T> PageQueryWrapper<T> build(boolean doPage, int page, int size, List<String> sort, T entity,
        Class<T> clazz) {
        PageQueryWrapper<T> pageQueryWrapper = new PageQueryWrapper<>();
        pageQueryWrapper.setDoPage(doPage);
        pageQueryWrapper.setPage(page);
        pageQueryWrapper.setSize(size);
        pageQueryWrapper.setSort(sort);
        pageQueryWrapper.setEntity(entity);
        pageQueryWrapper.setEntityClass(clazz);
        return pageQueryWrapper;
    }

}
