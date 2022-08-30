package io.github.dunwu.tool.data;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Spring4 的 webmvc 无法直接数据绑定 {@link org.springframework.data.domain.Pageable}
 * <p>
 * 所以使用本类进行数据适配，流程为：
 * <ol>
 * <li>web 层使用 {@link PageQuery} 作为请求参数，由 Spring Web 进行数据绑定</li>
 * <li>自行将 {@link PageQuery} 转化为 {@link org.springframework.data.domain.Pageable}</li>
 * <li>最后由 Mybatis Plus 扩展工具类使用 {@link org.springframework.data.domain.Pageable} 进行分页</li>
 * </ol>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-03-24
 */
@Data
@ToString
public class PageQuery implements Serializable {

    private static final long serialVersionUID = 7704551086334591374L;

    /**
     * 当前查询页
     */
    private int page = 1;

    /**
     * 每页展示记录数
     */
    private int size = 10;

    /**
     * 排序
     */
    private List<String> sort;

    public PageQuery() { }

    public PageQuery(int page, int size, List<String> sort) {
        this.page = page;
        this.size = size;
        this.sort = sort;
    }

}
