package io.github.dunwu.quickstart;

import com.spring4all.swagger.EnableSwagger2Doc;
import io.github.dunwu.data.hdfs.HdfsUtil;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import java.util.List;
import javax.annotation.PostConstruct;

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
@MapperScan("io.github.dunwu.quickstart.*.mapper")
public class Application {

    @Autowired
    private HdfsUtil hdfsUtil;

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class).run(args);
    }

    @PostConstruct
    public void method() throws Exception {
        List<LocatedFileStatus> locatedFileStatuses = hdfsUtil.listFiles("/user/dunwu");
        System.out.println(locatedFileStatuses);
    }

}
