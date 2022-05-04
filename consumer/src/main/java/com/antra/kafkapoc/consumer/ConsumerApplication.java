package com.antra.kafkapoc.consumer;

import com.antra.kafkapoc.consumer.pojo.UserActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;

@SpringBootApplication
@EnableKafka
public class ConsumerApplication{
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @KafkaListener(topics = "testTopic")
    public void listenGroupA(UserActionEvent message) {
        System.out.println("Received: " + message);
    }

    @KafkaListener(topics = "testTopic", groupId = "anotherJsonGroup")
    public void processMessage(UserActionEvent user) {
        System.out.println("Message received by consumer in second group: " + user.toString());
    }
}
