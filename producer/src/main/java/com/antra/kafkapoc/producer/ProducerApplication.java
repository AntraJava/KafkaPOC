package com.antra.kafkapoc.producer;

import com.antra.kafkapoc.producer.pojo.UserActionEvent;
import com.antra.kafkapoc.producer.utils.RandomDataGenerator;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@SpringBootApplication
@EnableScheduling
public class ProducerApplication{
//    @Autowired
//    private KafkaTemplate<String, UserActionEvent> kafkaTemplate;

    @Autowired
    private ReplyingKafkaTemplate<String, String, String> replyingTemplate;


    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Scheduled(fixedDelay = 1000)
    public void scheduledSend() {
//        sendMessage(RandomDataGenerator.randomUserEvent());
//
//        replyingTemplate.send
    }
}
