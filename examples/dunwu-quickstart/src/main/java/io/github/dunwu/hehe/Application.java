package io.github.dunwu.hehe;

import com.spring4all.swagger.EnableSwagger2Doc;
import io.github.dunwu.config.EnableDunwuWebConfiguration;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableSwagger2Doc
@SpringBootApplication(scanBasePackages = "io.github.dunwu")
@MapperScan("io.github.dunwu.quickstart.dao")
@EnableDunwuWebConfiguration
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
