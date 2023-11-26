package com.rpf.springmessagingservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class MessageService {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Value("${topic}")
    private String topic;

    public void sendMessage(String json) {
        ExecutorService executorService = Executors.newFixedThreadPool(20);
        executorService.submit(new AsyncMessagePublisher());
        //submitMessage(json);
    }

    public class AsyncMessagePublisher implements Callable {
        @Override
        public Object call() throws Exception {
            CompletableFuture<SendResult<String, Object>> future = kafkaTemplate.send(topic, json);
            future.whenComplete((result, exception) -> {
                if (exception == null) {
                    System.out.println(result.getRecordMetadata().offset() + " - " + result.getRecordMetadata().partition());
                } else {
                    System.out.println("Exception in processing!");
                }
            });

            return null;
        }
    }
}
