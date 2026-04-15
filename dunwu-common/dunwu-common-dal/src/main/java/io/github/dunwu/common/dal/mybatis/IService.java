package io.github.dunwu.common.dal.mybatis;

import io.github.dunwu.common.dal.domain.PageQuery;
import org.springframework.data.domain.PageRequest;

/**
 * 服务层通用接口
 * <p>
 * 定义了服务层可能需要用到的一些通用辅助方法，例如分页参数转换。
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2022-02-13
 */
public interface IService {

    /**
     * 将自定义的分页查询参数转换为 Spring Data 的 {@link PageRequest} 对象
     *
     * @param query 自定义分页查询参数
     * @return Spring Data 分页请求对象
     */
    PageRequest toPageRequest(PageQuery query);

}
