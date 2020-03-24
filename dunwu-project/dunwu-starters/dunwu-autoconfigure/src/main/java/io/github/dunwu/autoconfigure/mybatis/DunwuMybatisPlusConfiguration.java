package io.github.dunwu.autoconfigure.mybatis;

import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.extension.parsers.BlockAttackSqlParser;
import com.baomidou.mybatisplus.extension.plugins.OptimisticLockerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.PaginationInterceptor;
import com.baomidou.mybatisplus.extension.plugins.pagination.optimize.JsqlParserCountOptimize;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.ArrayList;
import java.util.List;

/**
 * Spring Boot 集成 MyBatis-Plus 配置
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://mybatis.plus/">MyBatis-Plus</a>
 * @since 2019-04-27
 */
@Configuration
@ConditionalOnClass({ SqlSessionFactory.class, SqlSessionFactoryBean.class })
@EnableTransactionManagement
@EnableConfigurationProperties(DunwuMybatisProperties.class)
public class DunwuMybatisPlusConfiguration {

    private final DunwuMybatisProperties properties;

    public DunwuMybatisPlusConfiguration(DunwuMybatisProperties properties) {
        this.properties = properties;
    }

    /**
     * 注入乐观锁插件
     *
     * @see <a href="https://mybatis.plus/guide/optimistic-locker-plugin.html">乐观锁插件</a>
     */
    @Bean
    @ConditionalOnProperty(value = "dunwu.mybatis.optimisticLockerEnabled", havingValue = "true", matchIfMissing = true)
    public OptimisticLockerInterceptor optimisticLockerInterceptor() {
        return new OptimisticLockerInterceptor();
    }

    /**
     * 注入分页插件
     *
     * @see <a href="https://mybatis.plus/guide/page.html">分页插件</a>
     * @see <a href="https://mybatis.plus/guide/block-attack-sql-parser.html">攻击 SQL
     * 阻断解析器</a>
     */
    @Bean
    public PaginationInterceptor paginationInterceptor() {
        PaginationInterceptor paginationInterceptor = new PaginationInterceptor();

        // 设置请求的页面大于最大页后操作， true调回到首页，false 继续请求  默认false
        paginationInterceptor.setOverflow(false);
        // 设置最大单页限制数量，默认 500 条，-1 不受限制
        paginationInterceptor.setLimit(500);
        // 开启 count 的 join 优化,只针对部分 left join
        paginationInterceptor.setCountSqlParser(new JsqlParserCountOptimize(true));

        // 攻击 SQL 阻断解析器、加入解析链
        if (properties.isBlockAttackEnabled()) {
            List<ISqlParser> sqlParserList = new ArrayList<>();
            sqlParserList.add(new BlockAttackSqlParser());
            paginationInterceptor.setSqlParserList(sqlParserList);
        }

        return paginationInterceptor;
    }

}
