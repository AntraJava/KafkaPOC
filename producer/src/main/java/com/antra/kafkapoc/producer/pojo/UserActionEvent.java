package com.antra.kafkapoc.producer.pojo;

import lombok.Data;

@Data
public class UserActionEvent {
    private String userId;
    private String action;
    private Object actionData;
}
