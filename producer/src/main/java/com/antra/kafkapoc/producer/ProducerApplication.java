package com.antra.kafkapoc.producer;

import com.antra.kafkapoc.producer.pojo.UserActionEvent;
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
    private KafkaTemplate<String, UserActionEvent> kafkaTemplate;

    @Bean
    public NewTopic topicTest() {
        return new NewTopic("testTopic", 3, (short) 2);
    }

    public void sendMessage(UserActionEvent event) {
        ListenableFuture<SendResult<String, UserActionEvent>> future = kafkaTemplate.send("testTopic", event.getUserId(), event);
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

    @Override
    public void run(String... args) throws Exception {
        UserActionEvent event = new UserActionEvent();
        event.setAction("upload file");
        event.setUserId("1001");
        event.setActionData("filelocation=s3://123//123.txt");
        sendMessage(event);
    }
}
