package com.antra.kafkapoc.consumer.api;

import com.antra.kafkapoc.consumer.service.UserActionService;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;

import java.util.stream.Collectors;

@RestController
public class TopUserController {

    @Autowired
    UserActionService userActionService;

    @GetMapping("/topN/{n}")
    public List<UserRankItem> getActiveUser(@PathVariable int n) {
        return userActionService.getTopNActiveUser(n).stream().sorted(Comparator.comparing(UserRankItem::getCount).reversed()).collect(Collectors.toList());
    }

    @GetMapping("/event_rank")
    public List<CategoryRankItem> getTopNClick() {
        return userActionService.getEventCategory().stream().sorted(Comparator.comparing(CategoryRankItem::getCount).reversed()).collect(Collectors.toList());
    }


    @ExceptionHandler(InvalidStateStoreException.class)
    public String notReady() {
        return "Stream is starting...";
    }

}
