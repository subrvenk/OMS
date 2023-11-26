package com.rpf.springmessagingservice.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessageProducerConfig {

    @Value("${topic}")
    private String topic;
    @Value("${numOfParitions}")
    private Integer numOfParitions;

    @Bean
    public NewTopic createTopic() {
        return new NewTopic(topic, numOfParitions, (short)1);
    }
}
