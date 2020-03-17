package io.github.dunwu.autoconfigure.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * Kafka 扩展配置类
 *
 * @author <a href="mailto:forbreak@163.com">Zhang Peng</a>
 * @since 2020-02-27
 */
@Configuration
@ConditionalOnClass(KafkaTemplate.class)
@ConditionalOnProperty(value = "dunwu.kafka.enabled", havingValue = "true")
@EnableKafka
@AutoConfigureAfter({ KafkaAutoConfiguration.class })
public class DunwuKafkaExtConfiguration {

    @Bean
    @ConditionalOnMissingBean(AdminClient.class)
    public AdminClient kafkaAdminClient(KafkaAdmin kafkaAdmin) {
        return AdminClient.create(kafkaAdmin.getConfig());
    }

    @Bean
    public KafkaUtil kafkaUtil(KafkaTemplate<Object, Object> kafkaTemplate, AdminClient adminClient) {
        return new KafkaUtil(kafkaTemplate, adminClient);
    }

}
