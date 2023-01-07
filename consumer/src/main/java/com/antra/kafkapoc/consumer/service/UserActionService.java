package com.antra.kafkapoc.consumer.service;

import com.antra.kafkapoc.consumer.api.CategoryRankItem;
import com.antra.kafkapoc.consumer.api.UserRankItem;

import java.util.PriorityQueue;

public interface UserActionService {
    PriorityQueue<UserRankItem> getTopNActiveUser(int n);
    PriorityQueue<CategoryRankItem> getEventCategory();
}
