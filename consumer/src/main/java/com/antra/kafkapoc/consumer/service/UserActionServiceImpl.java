package com.antra.kafkapoc.consumer.service;

import com.antra.kafkapoc.consumer.api.UserRankItem;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

@Service
public class UserActionServiceImpl implements UserActionService {
    @Autowired
    StreamsBuilderFactoryBean factoryBean;

    public PriorityQueue<UserRankItem> getTopNActiveUser(int n) {
        KafkaStreams kafkaStreams = factoryBean.getKafkaStreams();
        var counts = kafkaStreams.store(
                StoreQueryParameters.fromNameAndType("userRank", QueryableStoreTypes.timestampedWindowStore())
        );
        PriorityQueue<UserRankItem> result = new PriorityQueue<>(Comparator.comparing(UserRankItem::getCount));
        counts.fetchAll(Instant.now().minus(Duration.ofSeconds(30)), Instant.now())
                .forEachRemaining(action -> {
                    result.add(new UserRankItem(String.valueOf(action.key.key()), (Long)(action.value.value())));
                    if (result.size() > n) {
                        System.out.println(result.remove());
                    }
                });

        return result;
    }
}
