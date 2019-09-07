package io.github.dunwu.quickstart.template.service;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.util.Map;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-26
 */
public interface TemplateService {

	String TMPL_MAIL_CHECKCODE = "templates/mail/checkcode.ftl";

	String mergeTemplate(final String tmplName, Map params)
			throws IOException, TemplateException;

}
