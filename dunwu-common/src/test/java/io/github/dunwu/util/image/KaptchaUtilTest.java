package io.github.dunwu.util.image;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-13
 */
class KaptchaUtilTest {

    static final String OUTPUT_FILE = "D:\\Temp\\kaptcha.png";

    @Test
    void test() {
        KaptchaUtil.Kaptcha kaptcha = KaptchaUtil.create();
        System.out.println("验证码的值为：" + kaptcha.getCode());
        try {
            KaptchaUtil.toFile(kaptcha, new File(OUTPUT_FILE));
        } catch (IOException e) {
            Assertions.fail("生成验证码失败", e);
        }
    }
}
