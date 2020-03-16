/*
 * Copyright 2012-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.dunwu.autoconfigure.mail;

import io.github.dunwu.tool.thread.ThreadPoolUtil;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.AnyNestedCondition;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.mail.MailSender;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import javax.activation.MimeType;
import javax.mail.internet.MimeMessage;

/**
 * {@link EnableAutoConfiguration Auto configuration} for email support.
 *
 * @author Oliver Gierke
 * @author Stephane Nicoll
 * @author Eddú Meléndez
 * @since 1.2.0
 */
@Configuration
@ConditionalOnClass({ MimeMessage.class, MimeType.class, MailSender.class })
@ConditionalOnMissingBean(MailSender.class)
@Conditional(MailSenderAutoConfiguration.MailSenderCondition.class)
@EnableConfigurationProperties(DunwuMailProperties.class)
@Import({ MailSenderJndiConfiguration.class, MailSenderPropertiesConfiguration.class })
public class MailSenderAutoConfiguration {

    private final DunwuMailProperties dunwuMailProperties;

    public MailSenderAutoConfiguration(DunwuMailProperties dunwuMailProperties) {
        this.dunwuMailProperties = dunwuMailProperties;
    }

    @Bean("mailExecutor")
    public ExecutorService mailExecutor() {
        DunwuMailProperties.Pool pool = dunwuMailProperties.getPool();

        ThreadPoolUtil.QueuableCachedThreadPoolBuilder queuableCachedPool = ThreadPoolUtil.queuableCachedPool();
        queuableCachedPool.setMinSize(pool.getMinSize()).setMinSize(pool.getMaxSize())
            .setKeepAliveSecs(pool.getKeepAliveSecs())
            .setQueueSize(pool.getQueueSize())
            .setThreadFactory(ThreadPoolUtil
                .buildThreadFactory(pool.getThreadNamePrefix(), pool.getDaemon()))
            .setRejectHanlder(new ThreadPoolExecutor.AbortPolicy());
        return queuableCachedPool.build();
    }

    /**
     * Condition to trigger the creation of a {@link MailSender}. This kicks in if either the host or jndi name property
     * is set.
     */
    static class MailSenderCondition extends AnyNestedCondition {

        MailSenderCondition() {
            super(ConfigurationPhase.PARSE_CONFIGURATION);
        }

        @ConditionalOnProperty(prefix = "spring.mail", name = "host")
        static class HostProperty {

        }

        @ConditionalOnProperty(prefix = "spring.mail", name = "jndi-name")
        static class JndiNameProperty {

        }

    }

}
