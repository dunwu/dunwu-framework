package io.github.dunwu.quickstart.template.mapper;

import io.github.dunwu.quickstart.template.entity.TemplateConfig;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 模板配置表 Mapper 接口
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-26
 */
@Mapper
public interface TemplateConfigMapper extends BaseMapper<TemplateConfig> {

}
