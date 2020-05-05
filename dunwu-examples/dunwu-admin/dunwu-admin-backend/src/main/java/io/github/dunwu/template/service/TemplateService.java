package io.github.dunwu.template.service;

import freemarker.template.TemplateException;
import io.github.dunwu.data.mybatis.IResultService;
import io.github.dunwu.template.entity.Template;

import java.io.IOException;
import java.util.Map;

/**
 * <p>
 * 模板配置表 服务类
 * </p>
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-09-12
 */
public interface TemplateService extends IResultService<Template> {

    String TMPL_MAIL_CHECKCODE = "templates/mail/checkcode.ftl";

    String mergeTemplate(final String tmplName, Map params)
        throws IOException, TemplateException;

}
