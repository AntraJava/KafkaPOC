package com.antra.kafkapoc.consumer.stream;

import com.antra.kafkapoc.consumer.pojo.UserActionEvent;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

@Component
public class UserActionEventProcessor {

    private static final Serde<String> USER_ACTION_EVENT_SERDE = Serdes.String();

    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, UserActionEvent> messageStream = streamsBuilder
                .stream("testTopic2", Consumed.with(Serdes.String(),UserActionEventSerdes.serdes()));
//
//        KTable<String, Long> wordCounts = messageStream
//                .mapValues((ValueMapper<String, String>) String::toLowerCase)
//                .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
//                .groupBy((key, word) -> word, Grouped.with(STRING_SERDE, STRING_SERDE))
//                .count();
//
//        wordCounts.toStream().to("output-topic");
        messageStream
//                .filter()
                .mapValues(event -> 1L)
                .groupByKey(Grouped.with(Serdes.String(), Serdes.Long()))
                .aggregate(() -> 0L,
                        (key, value, aggregate) -> aggregate + value,
                        Materialized.with(Serdes.String(), Serdes.Long())
                        .as("userRank"));
    }
}

class UserActionEventSerdes {
    public static Serde<UserActionEvent> serdes() {
        JsonDeserializer<UserActionEvent> deserializer = new JsonDeserializer<>(UserActionEvent.class);
        deserializer.setRemoveTypeHeaders(false);
        deserializer.addTrustedPackages("*");
        deserializer.setUseTypeMapperForKey(true);

        JsonSerializer<UserActionEvent> serializer = new JsonSerializer<>();
        return Serdes.serdeFrom(serializer, deserializer);
    }
}
