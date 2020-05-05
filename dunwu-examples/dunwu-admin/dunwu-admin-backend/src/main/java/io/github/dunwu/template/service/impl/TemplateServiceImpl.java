package io.github.dunwu.template.service.impl;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import io.github.dunwu.data.mybatis.BaseResultServiceImpl;
import io.github.dunwu.template.entity.Template;
import io.github.dunwu.template.mapper.TemplateMapper;
import io.github.dunwu.template.service.TemplateService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * <p>
 * 模板配置表 服务实现类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
@Service
public class TemplateServiceImpl extends BaseResultServiceImpl<TemplateMapper, Template>
    implements TemplateService {

    private final Configuration freemarkConfig;

    public TemplateServiceImpl(Configuration freemarkConfig) {
        this.freemarkConfig = freemarkConfig;
    }

    @Override
    public String mergeTemplate(String tmplFile, Map params)
        throws IOException, TemplateException {
        freemarker.template.Template template = freemarkConfig.getTemplate(tmplFile);
        StringWriter stringWriter = new StringWriter();
        template.process(params, stringWriter);
        return stringWriter.toString();
    }

}
