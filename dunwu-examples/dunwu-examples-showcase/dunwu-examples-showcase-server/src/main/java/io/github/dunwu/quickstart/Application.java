package io.github.dunwu.quickstart;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 启动类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-23
 */
@EnableSwagger2Doc
@SpringBootApplication(scanBasePackages = "io.github.dunwu")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
