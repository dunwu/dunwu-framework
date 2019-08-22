package io.github.dunwu.web.config;

import io.github.dunwu.util.concurrent.ThreadPoolUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.MailSender;

import javax.activation.MimeType;
import javax.mail.internet.MimeMessage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2019-08-14
 */
@Configuration
@ConditionalOnClass({MimeMessage.class, MimeType.class, MailSender.class})
@ConditionalOnMissingBean(MailSender.class)
@EnableConfigurationProperties({DunwuMailExtProperties.class})
public class DunwuMailExtAutoConfiguration {

    private final DunwuMailExtProperties mailExtProperties;

    public DunwuMailExtAutoConfiguration(DunwuMailExtProperties mailExtProperties) {
        this.mailExtProperties = mailExtProperties;
    }

    @Bean("mailExecutorService")
    public ExecutorService mailExecutorService() {
        DunwuMailExtProperties.Pool pool = mailExtProperties.getPool();
        ThreadPoolUtil.QueuableCachedThreadPoolBuilder builder = ThreadPoolUtil.queuableCachedPool();
        builder.setMinSize(pool.getMinSize())
               .setMinSize(pool.getMaxSize())
               .setKeepAliveSecs(pool.getKeepAliveSecs())
               .setQueueSize(pool.getQueueSize())
               .setThreadFactory(ThreadPoolUtil.buildThreadFactory(pool.getThreadNamePrefix(), pool.getDaemon()))
               .setRejectHanlder(new ThreadPoolExecutor.AbortPolicy());
        return builder.build();
    }
}
