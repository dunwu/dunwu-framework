package io.github.dunwu.tool.lang;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class ClassScanerTest {

    @Test
    @Disabled
    public void scanTest() {
        ClassScanner scaner = new ClassScanner("cn.hutool.core.util", null);
        Set<Class<?>> set = scaner.scan();
        for (Class<?> clazz : set) {
            Console.log(clazz.getName());
        }
    }

}
