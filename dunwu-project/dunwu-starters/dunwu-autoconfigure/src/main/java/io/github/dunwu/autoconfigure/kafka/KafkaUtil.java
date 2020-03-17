package io.github.dunwu.autoconfigure.kafka;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class KafkaUtil {

    private static final Logger log = LoggerFactory.getLogger(KafkaUtil.class);

    private final KafkaTemplate<Object, Object> kafkaTemplate;

    private final AdminClient adminClient;

    public KafkaUtil(KafkaTemplate<Object, Object> kafkaTemplate, AdminClient adminClient) {
        this.kafkaTemplate = kafkaTemplate;
        this.adminClient = adminClient;
    }

    public void send(String topic, Object data) {
        log.info("向kafka发送数据:[{}]", data);
        log.info("");
        try {
            kafkaTemplate.send(topic, data);
        } catch (Exception e) {
            log.error("发送数据出错！！！{}{}", topic, data);
            log.error("发送数据出错=====>", e);
        }

        //消息发送的监听器，用于回调返回信息
        kafkaTemplate.setProducerListener(new ProducerListener<Object, Object>() {
            public void onSuccess(String topic, Integer partition, String key, Object value,
                RecordMetadata recordMetadata) {
                log.info("发送 {} 数据完成", topic);
            }

            public void onError(String topic, Integer partition, String key, Object value, Exception exception) {
                log.error("发送数据出错！！！topic={}, value={}", topic, value);
                log.error("发送数据出错=====>", exception);
            }
        });
    }

    public void createTopics(String kafkaTopics) {
        String[] topics = kafkaTopics.split(",");
        List<NewTopic> topicList = new ArrayList<>();
        for (String item : topics) {
            adminClient.deleteTopics(Collections.singletonList(item));
            NewTopic topic = new NewTopic(item, 1, (short) 1);
            topicList.add(topic);
        }
        log.info("[Kafka] create kafka topics: {}", topicList);
        adminClient.createTopics(topicList);
    }

    /**
     * 查看所有Topic
     *
     * @return Set<String>
     */
    public Set<String> showTopics() {
        ListTopicsResult listTopics = adminClient.listTopics();
        Set<String> topics = null;
        try {
            topics = listTopics.names().get();
        } catch (InterruptedException | ExecutionException e) {
            log.error("show topics failed", e);
        }
        return topics;
    }

    /**
     * 检查Topic是否存在
     *
     * @param topic Kafka Topic
     * @return boolean
     */
    public boolean isExistTopic(String topic) {
        Set<String> topics = showTopics();
        if (topics.contains(topic)) {
            return true;
        }
        return false;
    }

}
