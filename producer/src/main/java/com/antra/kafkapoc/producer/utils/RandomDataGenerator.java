package com.antra.kafkapoc.producer.utils;

import com.antra.kafkapoc.producer.pojo.UserActionEvent;
import com.github.javafaker.Faker;

public class RandomDataGenerator {
    static Faker faker = new Faker();
    static String[] USER_ACTION_OPTIONS = {"Click","Type","Refresh","Upload","GoBack","ScrollUp","ScrollDown"};
    public static UserActionEvent randomUserEvent() {
        UserActionEvent event = new UserActionEvent();
        event.setUserId(faker.numerify("##"));
        event.setAction(faker.options().option(USER_ACTION_OPTIONS));
        if (event.getAction().equals("Upload")) {
            event.setActionData("location:s3://"+faker.file().fileName());
        } else {
            event.setActionData("page:http://mywebsite.com/god/" + faker.internet().slug()+"/"+ faker.ancient().god()+".html");
        }
        return event;
    }

    public static void main(String[] args) {
        System.out.println(randomUserEvent());
        System.out.println(randomUserEvent());
        System.out.println(randomUserEvent());
        System.out.println(randomUserEvent());
        System.out.println(randomUserEvent());
        System.out.println(randomUserEvent());
        System.out.println(randomUserEvent());
        System.out.println(randomUserEvent());
        System.out.println(randomUserEvent());
        System.out.println(randomUserEvent());
    }
}
