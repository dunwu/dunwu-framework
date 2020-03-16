package io.github.dunwu.tool.util;

import io.github.dunwu.tool.lang.Filter;
import io.github.dunwu.tool.lang.test.bean.ExamInfoDict;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * 反射工具类单元测试
 *
 * @author Looly
 */
public class ReflectUtilTest {

    @Test
    public void getMethodsTest() {
        Method[] methods = ReflectUtil.getMethods(ExamInfoDict.class);
        Assertions.assertEquals(22, methods.length);

        //过滤器测试
        methods = ReflectUtil.getMethods(ExamInfoDict.class, new Filter<Method>() {

            @Override
            public boolean accept(Method t) {
                return Integer.class.equals(t.getReturnType());
            }
        });

        Assertions.assertEquals(4, methods.length);
        final Method method = methods[0];
        Assertions.assertNotNull(method);

        //null过滤器测试
        methods = ReflectUtil.getMethods(ExamInfoDict.class, null);

        Assertions.assertEquals(22, methods.length);
        final Method method2 = methods[0];
        Assertions.assertNotNull(method2);
    }

    @Test
    public void getMethodTest() {
        Method method = ReflectUtil.getMethod(ExamInfoDict.class, "getId");
        Assertions.assertEquals("getId", method.getName());
        Assertions.assertEquals(0, method.getParameterTypes().length);

        method = ReflectUtil.getMethod(ExamInfoDict.class, "getId", Integer.class);
        Assertions.assertEquals("getId", method.getName());
        Assertions.assertEquals(1, method.getParameterTypes().length);
    }

    @Test
    public void getMethodIgnoreCaseTest() {
        Method method = ReflectUtil.getMethodIgnoreCase(ExamInfoDict.class, "getId");
        Assertions.assertEquals("getId", method.getName());
        Assertions.assertEquals(0, method.getParameterTypes().length);

        method = ReflectUtil.getMethodIgnoreCase(ExamInfoDict.class, "GetId");
        Assertions.assertEquals("getId", method.getName());
        Assertions.assertEquals(0, method.getParameterTypes().length);

        method = ReflectUtil.getMethodIgnoreCase(ExamInfoDict.class, "setanswerIs", Integer.class);
        Assertions.assertEquals("setAnswerIs", method.getName());
        Assertions.assertEquals(1, method.getParameterTypes().length);
    }

    @Test
    public void getFieldTest() {
        // 能够获取到父类字段
        Field privateField = ReflectUtil.getField(ClassUtilTest.TestSubClass.class, "privateField");
        Assertions.assertNotNull(privateField);
    }

    @Test
    public void setFieldTest() {
        TestClass testClass = new TestClass();
        ReflectUtil.setFieldValue(testClass, "a", "111");
        Assertions.assertEquals(111, testClass.getA());
    }

    @Test
    public void invokeTest() {
        TestClass testClass = new TestClass();
        ReflectUtil.invoke(testClass, "setA", 10);
        Assertions.assertEquals(10, testClass.getA());
    }

    static class TestClass {

        private int a;

        public int getA() {
            return a;
        }

        public void setA(int a) {
            this.a = a;
        }

    }

}
