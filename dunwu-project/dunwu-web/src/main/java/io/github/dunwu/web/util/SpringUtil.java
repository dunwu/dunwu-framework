package io.github.dunwu.web.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-24
 */
@Component
public class SpringUtil implements ApplicationContextAware {

	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
		throws BeansException {
		if (null == SpringUtil.applicationContext) {
			SpringUtil.applicationContext = applicationContext;
		}
	}

	public static Object getBean(String name) {
		return applicationContext.getBean(name);
	}

	public static Object getBean(String name, Object... args) {
		return applicationContext.getBean(name, args);
	}

	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	public static <T> T getBean(Class<T> requiredType, Object... args) {
		return applicationContext.getBean(requiredType, args);
	}

	public static <T> T getBean(String name, Class<T> requiredType) {
		return applicationContext.getBean(name, requiredType);
	}

	public static Object getObject(String id) {
		Object object = null;
		object = applicationContext.getBean(id);
		return object;
	}

}
