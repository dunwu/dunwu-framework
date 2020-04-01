package io.github.dunwu.admin;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 启动类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-23
 */
@EnableCaching
@EnableRedisHttpSession
@EnableSwagger2Doc
@SpringBootApplication(scanBasePackages = "io.github.dunwu")
@MapperScan("io.github.dunwu.admin.*.mapper")
public class DunwuAdminApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DunwuAdminApplication.class).run(args);
    }

}
