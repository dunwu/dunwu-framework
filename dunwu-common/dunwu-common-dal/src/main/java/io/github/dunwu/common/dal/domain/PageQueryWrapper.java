package io.github.dunwu.common.dal.domain;

import io.github.dunwu.common.constant.ResultCode;
import io.github.dunwu.common.exception.DefaultException;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collections;
import java.util.List;

/**
 * 分页查询包裹实体
 * <p>
 * 继承自 {@link PageQuery}，用于封装分页查询时的额外信息，如查询实体、实体类等。
 *
 * @param <T> 实体类型
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-08-22
 */
@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
@SuppressWarnings({"unchecked"})
public class PageQueryWrapper<T> extends PageQuery {

    /**
     * 是否开启分页
     */
    private boolean doPage = true;

    /**
     * 查询实体
     */
    private T entity;

    /**
     * 实体类型（主要用于确定泛型）
     */
    private Class<T> entityClass;

    /**
     * 默认构造方法
     */
    public PageQueryWrapper() { }

    /**
     * 构建分页查询包裹
     *
     * @param entity 查询实体
     * @param <T>    实体类型
     * @return PageQueryWrapper 实例
     */
    public static <T> PageQueryWrapper<T> build(T entity) {
        return build(1, 10, Collections.singletonList("id,asc"), entity, false);
    }

    /**
     * 构建分页查询包裹
     *
     * @param page     当前页码
     * @param pageSize 每页大小
     * @param sort     排序规则列表
     * @param entity   查询实体
     * @param <T>      实体类型
     * @return PageQueryWrapper 实例
     */
    public static <T> PageQueryWrapper<T> build(int page, int pageSize, List<String> sort, T entity) {
        return build(page, pageSize, sort, entity, false);
    }

    /**
     * 构建分页查询包裹
     *
     * @param page     当前页码
     * @param pageSize 每页大小
     * @param sort     排序规则列表
     * @param entity   查询实体
     * @param doPage   是否分页
     * @param <T>      实体类型
     * @return PageQueryWrapper 实例
     */
    public static <T> PageQueryWrapper<T> build(int page, int pageSize, List<String> sort, T entity, boolean doPage) {
        if (entity == null) {
            throw new DefaultException(ResultCode.PARAM_ERROR, "entity 不能为 null");
        }
        return build(doPage, page, pageSize, sort, entity, (Class<T>)entity.getClass());
    }

    /**
     * 构建分页查询包裹
     *
     * @param page     当前页码
     * @param pageSize 每页大小
     * @param sort     排序规则列表
     * @param clazz    实体类型
     * @param <T>      实体类型
     * @return PageQueryWrapper 实例
     */
    public static <T> PageQueryWrapper<T> build(int page, int pageSize, List<String> sort, Class<T> clazz) {
        return build(page, pageSize, sort, clazz, true);
    }

    /**
     * 构建分页查询包裹
     *
     * @param page     当前页码
     * @param pageSize 每页大小
     * @param sort     排序规则列表
     * @param clazz    实体类型
     * @param doPage   是否分页
     * @param <T>      实体类型
     * @return PageQueryWrapper 实例
     */
    public static <T> PageQueryWrapper<T> build(int page, int pageSize, List<String> sort, Class<T> clazz,
        boolean doPage) {
        if (clazz == null) {
            throw new DefaultException(ResultCode.PARAM_ERROR, "clazz 不能为 null");
        }
        return build(doPage, page, pageSize, sort, null, clazz);
    }

    /**
     * 全参构建分页查询包裹
     *
     * @param doPage 是否分页
     * @param page   当前页码
     * @param size   每页大小
     * @param sort   排序规则列表
     * @param entity 查询实体
     * @param clazz  实体类型
     * @param <T>    实体类型
     * @return PageQueryWrapper 实例
     */
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
