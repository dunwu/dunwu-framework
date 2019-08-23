package io.github.dunwu.quickstart;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;

@EnableSwagger2Doc
// @see https://github.com/quartz-scheduler/quartz/issues/468
@SpringBootApplication(scanBasePackages = "io.github.dunwu", exclude = {QuartzAutoConfiguration.class})
//@MapperScan({"io.github.dunwu.hehe.*.mapper", "io.github.dunwu.*.mapper"})
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
