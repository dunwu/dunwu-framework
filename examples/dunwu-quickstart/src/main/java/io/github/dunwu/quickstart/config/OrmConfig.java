package io.github.dunwu.quickstart.config;

import io.github.dunwu.config.AbstractMybatisPlusConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * ORM 框架集成配置
 * <p>
 * 扫描本项目中的io.github.dunwu.*.dao*，向容器中注入 DAO
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-04-27
 */
@Configuration
@EnableTransactionManagement
@MapperScan("io.github.dunwu.*.dao*")
public class OrmConfig extends AbstractMybatisPlusConfig {}
