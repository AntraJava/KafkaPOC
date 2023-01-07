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
import org.springframework.kafka.support.SendResult;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@SpringBootApplication
@EnableScheduling
public class ProducerApplication{
    @Autowired
    private KafkaTemplate<String, UserActionEvent> kafkaTemplate;

    @Value("${kafka.default.topic}")
    private String topic;

    @Bean
    public NewTopic topicTest() {
        return new NewTopic(topic, 3, (short) 3);
    }

    public void sendMessage(UserActionEvent event) {
        ListenableFuture<SendResult<String, UserActionEvent>> future = kafkaTemplate.send(topic, event.getUserId(), event);
        future.addCallback(new ListenableFutureCallback<>() {

            @Override
            public void onSuccess(SendResult<String, UserActionEvent> result) {
                System.out.println("Sent userActionEvent =[" + event.getAction() +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send userActionEvent=["
                        + event.getAction() + "] due to : " + ex.getMessage());
            }
        });
    }


    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Scheduled(fixedDelay = 100)
    public void scheduledSend() {
        sendMessage(RandomDataGenerator.randomUserEvent());
//
//        UserActionEvent event = new UserActionEvent();
//        event.setUserId("11");
//        event.setAction("test");
//        sendMessage(event);
    }
}
