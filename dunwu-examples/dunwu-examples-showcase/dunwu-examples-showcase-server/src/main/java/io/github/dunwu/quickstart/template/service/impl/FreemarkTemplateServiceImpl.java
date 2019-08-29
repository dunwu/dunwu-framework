package io.github.dunwu.quickstart.template.service.impl;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import io.github.dunwu.quickstart.template.service.TemplateService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map;

/**
 * @author Zhang Peng
 * @date 2018-12-19
 */
@Service
public class FreemarkTemplateServiceImpl implements TemplateService {

    private final Configuration freemarkConfig;

    public FreemarkTemplateServiceImpl(Configuration freemarkConfig) {
        this.freemarkConfig = freemarkConfig;
    }

    @Override
    public String mergeTemplate(String tmplFile, Map params) throws IOException, TemplateException {
        Template template = freemarkConfig.getTemplate(tmplFile);
        StringWriter stringWriter = new StringWriter();
        template.process(params, stringWriter);
        return stringWriter.toString();
    }
}
