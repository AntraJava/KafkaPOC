package com.antra.kafkapoc.consumer.stream;

import com.antra.kafkapoc.consumer.pojo.UserActionEvent;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.LinkedList;
import java.util.PriorityQueue;

@Component
public class UserActionEventProcessor {
    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, UserActionEvent> messageStream = streamsBuilder
                .stream("testTopic2"
                        , Consumed.with(Serdes.String(),UserActionEventSerdes.serdes())
                                .withOffsetResetPolicy(Topology.AutoOffsetReset.LATEST));
        //        KTable<String, Long> wordCounts = messageStream
//                .mapValues((ValueMapper<String, String>) String::toLowerCase)
//                .flatMapValues(value -> Arrays.asList(value.split("\\W+")))
//                .groupBy((key, word) -> word, Grouped.with(STRING_SERDE, STRING_SERDE))
//                .count();
//
//        wordCounts.toStream().to("output-topic");
        Duration windowSize = Duration.ofSeconds(30);

        final Serde<Windowed<String>> windowedStringSerde =
                WindowedSerdes.timeWindowedSerdeFrom(String.class, windowSize.toMillis());

        messageStream
                .groupByKey(Grouped.with(Serdes.String(), UserActionEventSerdes.serdes()))
                .windowedBy(TimeWindows.ofSizeWithNoGrace(windowSize))
                .count(Materialized.as("userRank"));
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
