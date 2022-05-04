package com.antra.kafkapoc.producer;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Bean
    public NewTopic topicTest() {
        return new NewTopic("testTopic", 3, (short) 2);
    }

    public void sendMessage(String msg) {
        ListenableFuture<SendResult<String, String>> future = kafkaTemplate.send("testTopic", msg, msg);
        future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

            @Override
            public void onSuccess(SendResult<String, String> result) {
                System.out.println("Sent message=[" + msg +
                        "] with offset=[" + result.getRecordMetadata().offset() + "]");
            }
            @Override
            public void onFailure(Throwable ex) {
                System.out.println("Unable to send message=["
                        + msg + "] due to : " + ex.getMessage());
            }
        });
    }


    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        sendMessage("1");
        sendMessage("2");
        sendMessage("3");
        sendMessage("4");
        sendMessage("5");
        sendMessage("6");
    }
}
