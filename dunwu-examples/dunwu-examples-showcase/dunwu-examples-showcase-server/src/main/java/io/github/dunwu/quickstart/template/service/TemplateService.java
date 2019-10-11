package io.github.dunwu.quickstart.template.service;

import freemarker.template.TemplateException;
import io.github.dunwu.data.service.IService;
import io.github.dunwu.quickstart.template.entity.Template;

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
public interface TemplateService extends IService<Template> {

	String TMPL_MAIL_CHECKCODE = "templates/mail/checkcode.ftl";

	String mergeTemplate(final String tmplName, Map params) throws IOException, TemplateException;

}
