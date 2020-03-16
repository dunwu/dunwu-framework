package io.github.dunwu.app;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * 启动类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-23
 */
@SpringBootApplication(scanBasePackages = "io.github.dunwu")
public class DunwuApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DunwuApplication.class).run(args);
    }

}
