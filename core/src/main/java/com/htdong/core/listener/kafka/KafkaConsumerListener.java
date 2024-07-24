package com.htdong.core.listener.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @author dht31261
 * @date 2024年7月23日 16:33:03
 */
@Slf4j
@Component
public class KafkaConsumerListener {

    @KafkaListener(topics = {"test000"})
    public void listenerMessage(ConsumerRecord<String, String> message) {
        System.out.printf("kafka = %s %s\n", message.key(), message.value());
    }
}