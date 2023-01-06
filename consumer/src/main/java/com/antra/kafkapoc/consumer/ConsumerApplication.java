package com.antra.kafkapoc.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.PartitionOffset;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.core.KafkaTemplate;

import java.time.Duration;

@SpringBootApplication
@EnableKafka
public class ConsumerApplication{
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    public static void main(String[] args) {
        SpringApplication.run(ConsumerApplication.class, args);
    }

    @KafkaListener(topics = "testTopic", groupId = "UserGroupA")
    public void listenGroupA(String message) {
        System.out.println("Received in consumer 1: " + message);
    }
//    @KafkaListener(topics = "testTopic", groupId = "UserGroupB")
    @KafkaListener(groupId = "UserGroupB",
            topicPartitions = @TopicPartition(topic = "testTopic",
                    partitionOffsets = {
                            @PartitionOffset(partition = "0", initialOffset = "0"),
                            @PartitionOffset(partition = "1", initialOffset = "0"),
                            @PartitionOffset(partition = "2", initialOffset = "0")}))
    public void listenGroupAUser2(String message) {
        System.out.println("Received in consumer 2: " + message);
    }
}
