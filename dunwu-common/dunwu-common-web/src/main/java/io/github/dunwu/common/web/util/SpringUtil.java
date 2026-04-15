package io.github.dunwu.common.web.util;

import lombok.Getter;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Spring 上下文工具类
 * <p>
 * 用于在非 Spring 管理的类中获取 ApplicationContext，从而访问 Spring Bean
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-07-24
 */
public class SpringUtil implements ApplicationContextAware, DisposableBean {

    /**
     * Spring 应用上下文
     */
    @Getter
    private static ApplicationContext applicationContext = null;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        if (null == SpringUtil.applicationContext) {
            SpringUtil.applicationContext = applicationContext;
        }
    }

    @Override
    public void destroy() throws Exception {
        applicationContext = null;
    }

    /**
     * 根据 Bean 名称获取 Bean 实例
     *
     * @param name Bean 名称
     * @return Bean 实例
     */
    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    /**
     * 根据 Bean 名称获取 Bean 实例（带构造参数）
     *
     * @param name Bean 名称
     * @param args 构造参数
     * @return Bean 实例
     */
    public static Object getBean(String name, Object... args) {
        return applicationContext.getBean(name, args);
    }

    /**
     * 根据类型获取 Bean 实例
     *
     * @param requiredType Bean 类型
     * @param <T>          泛型类型
     * @return Bean 实例
     */
    public static <T> T getBean(Class<T> requiredType) {
        return applicationContext.getBean(requiredType);
    }

    /**
     * 根据类型获取 Bean 实例（带构造参数）
     *
     * @param requiredType Bean 类型
     * @param args         构造参数
     * @param <T>          泛型类型
     * @return Bean 实例
     */
    public static <T> T getBean(Class<T> requiredType, Object... args) {
        return applicationContext.getBean(requiredType, args);
    }

    /**
     * 根据名称和类型获取 Bean 实例
     *
     * @param name         Bean 名称
     * @param requiredType Bean 类型
     * @param <T>          泛型类型
     * @return Bean 实例
     */
    public static <T> T getBean(String name, Class<T> requiredType) {
        return applicationContext.getBean(name, requiredType);
    }

    /**
     * 根据 ID 获取对象（与 getBean(String) 功能相同，保留用于兼容）
     *
     * @param id Bean ID
     * @return Bean 实例
     */
    public static Object getObject(String id) {
        return applicationContext.getBean(id);
    }

}
