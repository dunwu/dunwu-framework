package io.github.dunwu.common.web.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;

import java.lang.reflect.Field;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

/**
 * {@link SpringUtil} 单元测试
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2026-04-13
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("SpringUtil 单元测试")
class SpringUtilTest {

    @Mock
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() throws Exception {
        // 通过反射注入 Mock 的 ApplicationContext
        Field field = SpringUtil.class.getDeclaredField("applicationContext");
        field.setAccessible(true);
        field.set(null, applicationContext);
    }

    @Test
    @DisplayName("getBean - 根据名称获取 Bean")
    void getBeanByName() {
        Object mockBean = new Object();
        when(applicationContext.getBean("myBean")).thenReturn(mockBean);

        Object result = SpringUtil.getBean("myBean");
        assertThat(result).isSameAs(mockBean);
    }

    @Test
    @DisplayName("getBean - 根据名称和参数获取 Bean")
    void getBeanByNameAndArgs() {
        Object mockBean = new Object();
        when(applicationContext.getBean("myBean", "arg1", 123)).thenReturn(mockBean);

        Object result = SpringUtil.getBean("myBean", "arg1", 123);
        assertThat(result).isSameAs(mockBean);
    }

    @Test
    @DisplayName("getBean - 根据类型获取 Bean")
    void getBeanByType() {
        String mockBean = "testString";
        when(applicationContext.getBean(String.class)).thenReturn(mockBean);

        String result = SpringUtil.getBean(String.class);
        assertThat(result).isEqualTo("testString");
    }

    @Test
    @DisplayName("getBean - 根据类型和参数获取 Bean")
    void getBeanByTypeAndArgs() {
        String mockBean = "testStringWithArgs";
        when(applicationContext.getBean(String.class, "arg1")).thenReturn(mockBean);

        String result = SpringUtil.getBean(String.class, "arg1");
        assertThat(result).isEqualTo("testStringWithArgs");
    }

    @Test
    @DisplayName("getBean - 根据名称和类型获取 Bean")
    void getBeanByNameAndType() {
        String mockBean = "namedStringBean";
        when(applicationContext.getBean("stringBean", String.class)).thenReturn(mockBean);

        String result = SpringUtil.getBean("stringBean", String.class);
        assertThat(result).isEqualTo("namedStringBean");
    }

    @Test
    @DisplayName("getObject - 根据 ID 获取对象")
    void getObjectById() {
        Object mockBean = new Object();
        when(applicationContext.getBean("beanId")).thenReturn(mockBean);

        Object result = SpringUtil.getObject("beanId");
        assertThat(result).isSameAs(mockBean);
    }
}
