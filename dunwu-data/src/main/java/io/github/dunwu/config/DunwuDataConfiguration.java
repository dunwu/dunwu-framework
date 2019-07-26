package io.github.dunwu.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.injector.LogicSqlInjector;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PerformanceInterceptor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * MyBatis-Plus 集成配置抽象类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://mybatis.plus/">MyBatis-Plus</a>
 * @since 2019-04-27
 */
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties({DunwuDataProperties.class})
@ConditionalOnProperty(prefix = "dunwu.data", value = "enabled", matchIfMissing = true)
public class DunwuDataConfiguration {

    @Autowired
    private DunwuDataProperties dunwuDataProperties;

    /**
     * 注入分页插件
     *
     * @see <a href="https://mybatis.plus/guide/page.html">分页插件</a>
     * @see <a href="https://mybatis.plus/guide/block-attack-sql-parser.html">攻击 SQL 阻断解析器</a>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        List<ISqlParser> sqlParserList = new ArrayList<>();
        // 攻击 SQL 阻断解析器、加入解析链
        sqlParserList.add(new BlockAttackSqlParser());
        paginationInterceptor.setSqlParserList(sqlParserList);

        return paginationInterceptor;
    }

    /**
     * 注入逻辑删除插件
     *
     * @see <a href="https://mybatis.plus/guide/logic-delete.html">逻辑删除插件</a>
     */
    @Bean
    public ISqlInjector sqlInjector() {
        return new LogicSqlInjector();
    }

    /**
     * 注入乐观锁插件
     *
     * @see <a href="https://mybatis.plus/guide/optimistic-locker-plugin.html">乐观锁插件</a>
     */
    @Bean
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 注入SQL执行效率插件
     * <p>
     * 设置 dev test 环境开启，不要在生产环境使用
     *
     * @see <a href="https://mybatis.plus/guide/performance-analysis-plugin.html">性能分析插件</a>
     */
    @Bean
    @Profile({"dev", "test"})
    public PerformanceInterceptor performanceInterceptor() {
        PerformanceInterceptor performanceInterceptor = new PerformanceInterceptor();
        performanceInterceptor.setMaxTime(100);
        performanceInterceptor.setFormat(true);
        return performanceInterceptor;
    }

    /**
     * Json 格式化
     * <p>
     * 引入 spring-boot-starter-json ，主要目的在于针对 Java8 的一些新类型进行 Json 格式化，这里需要注册进容器
     *
     * @return ObjectMapper
     */
    @Bean
    public ObjectMapper serializingObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules();
        return objectMapper;
    }

    //    @Bean
    //    public MapperScannerConfigurer getMapperScannerConfigurer() {
    //        MapperScannerConfigurer mapperScannerConfigurer = new MapperScannerConfigurer();
    //        mapperScannerConfigurer.setAnnotationClass(org.apache.ibatis.annotations.Mapper.class);
    //        mapperScannerConfigurer.setBasePackage(Optional.ofNullable(dunwuDataProperties.getMapperPackage())
    //                                                       .orElse("io.github.dunwu.*.mapper"));
    //        return mapperScannerConfigurer;
    //    }
}
