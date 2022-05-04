package com.antra.kafkapoc.consumer.pojo;

import lombok.Data;

@Data
public class UserActionEvent {
    private String userId;
    private String action;
    private Object actionData;
}
