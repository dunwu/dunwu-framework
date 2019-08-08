package io.github.dunwu.scheduler.config;

import io.github.dunwu.data.config.DunwuDataAutoConfiguration;
import io.github.dunwu.web.config.DunwuWebAutoConfiguration;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.spi.JobFactory;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

/**
 * 调度配置类
 * <p>
 * 注：务必要排除 Spring 默认的 Quartz 配置类 QuartzAutoConfiguration
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @see <a href="https://github.com/quartz-scheduler/quartz/issues/468">Quartz Issue468</a>
 * @since 2019-07-30
 */
@Configuration
@ConditionalOnClass({Scheduler.class, SchedulerFactoryBean.class, PlatformTransactionManager.class})
@ConditionalOnSingleCandidate(DataSource.class)
@ConditionalOnProperty(prefix = "dunwu.scheduler", value = "enabled", matchIfMissing = true)
@AutoConfigureAfter({DunwuDataAutoConfiguration.class, DunwuWebAutoConfiguration.class})
@EnableConfigurationProperties({DunwuSchedulerProperties.class})
public class DunwuSchedulerAutoConfiguration {

    private final DunwuSchedulerProperties properties;

    public DunwuSchedulerAutoConfiguration(DunwuSchedulerProperties properties) {
        this.properties = properties;
    }

    @Bean
    public JobFactory jobFactory(ApplicationContext applicationContext) {
        AutowireSpringBeanJobFactory jobFactory = new AutowireSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        return jobFactory;
    }

    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(DataSource dataSource, JobFactory jobFactory) throws Exception {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        schedulerFactoryBean.setOverwriteExistingJobs(true);
        schedulerFactoryBean.setDataSource(dataSource);
        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setQuartzProperties(quartzProperties());
        schedulerFactoryBean.afterPropertiesSet();
        return schedulerFactoryBean;
    }

    /**
     * 持久化调度器，只处理需要持久化存储的调度任务
     *
     * @param schedulerFactoryBean 调度器工厂类
     * @return 调度器实例
     * @throws SchedulerException 调度器异常
     */
    @Bean
    public Scheduler scheduler(SchedulerFactoryBean schedulerFactoryBean) throws SchedulerException {
        Scheduler scheduler = schedulerFactoryBean.getScheduler();
        scheduler.start();
        return scheduler;
    }

    /**
     * 加载 Quartz 调度器配置项
     *
     * @return Properties
     * @throws IOException IO 异常
     */
    @Bean
    public Properties quartzProperties() throws IOException {
        PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource(properties.getQuartzConfigLocation()));
        propertiesFactoryBean.afterPropertiesSet();
        return propertiesFactoryBean.getObject();
    }
}
