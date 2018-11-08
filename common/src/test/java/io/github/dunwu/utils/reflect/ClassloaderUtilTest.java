package io.github.dunwu.utils.reflect;

import org.junit.Test;

public class ClassloaderUtilTest {

    @Test
    public void test() {
        ClassLoader loader = ClassLoaderUtil.getDefaultClassLoader();
        ClassLoaderUtil.isPresent("io.github.dunwu.utils.reflect.ClassUtil", loader);
    }
}
