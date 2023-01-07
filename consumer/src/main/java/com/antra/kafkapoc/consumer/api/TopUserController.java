package com.antra.kafkapoc.consumer.api;

import com.antra.kafkapoc.consumer.service.UserActionService;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.errors.InvalidStateStoreException;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.apache.kafka.streams.state.ReadOnlyWindowStore;
import org.apache.kafka.streams.state.TimestampedKeyValueStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

@RestController
public class TopUserController {

    @Autowired
    UserActionService userActionService;

    @GetMapping("/topN/{n}")
    public List<UserRankItem> getWordCount(@PathVariable int n) {
        return userActionService.getTopNActiveUser(n).stream().sorted(Comparator.comparing(UserRankItem::getCount).reversed()).collect(Collectors.toList());
    }

    @ExceptionHandler(InvalidStateStoreException.class)
    public String notReady() {
        return "Stream is starting...";
    }

}
