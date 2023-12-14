package io.github.dunwu.tool.data.mybatis;

import io.github.dunwu.tool.data.request.PageQuery;
import org.springframework.data.domain.PageRequest;

public interface IService {

    PageRequest toPageRequest(PageQuery query);

}
