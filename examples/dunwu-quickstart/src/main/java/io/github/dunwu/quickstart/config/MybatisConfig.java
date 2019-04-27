package io.github.dunwu.quickstart.config;

import io.github.dunwu.config.AbstractMybatisConfig;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Zhang Peng
 * @since 2019-04-15
 */
@Configuration
@EnableTransactionManagement
@MapperScan("io.github.dunwu.*.dao*")
public class MybatisConfig extends AbstractMybatisConfig {}
