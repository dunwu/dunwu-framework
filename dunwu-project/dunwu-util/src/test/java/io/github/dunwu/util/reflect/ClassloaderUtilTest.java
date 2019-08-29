package io.github.dunwu.util.reflect;

import org.junit.jupiter.api.Test;

public class ClassloaderUtilTest {

    @Test
    public void test() {
        ClassLoader loader = ClassLoaderUtil.getDefaultClassLoader();
        ClassLoaderUtil.isPresent("io.github.dunwu.utils.reflect.ClassUtil", loader);
    }
}
