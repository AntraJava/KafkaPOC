package com.antra.kafkapoc.consumer.stream;

import com.antra.kafkapoc.consumer.pojo.UserActionEvent;
import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.LinkedList;
import java.util.PriorityQueue;

@Component
public class UserActionEventProcessor {

    @Value("${kafka.default.topic}")
    private String topic;
    Duration windowSize = Duration.ofSeconds(30);

    @Autowired
    public void buildPipeline(StreamsBuilder streamsBuilder) {
        KStream<String, UserActionEvent> messageStream = streamsBuilder
                .stream(topic
                        , Consumed.with(Serdes.String(), UserActionEventSerdes.serdes())
                                .withOffsetResetPolicy(Topology.AutoOffsetReset.LATEST));
// // You can also choose to use Branch if messages can be put into different streams.
// // USER_ACTION_OPTIONS = {"Click","Type","Refresh","Upload","GoBack","ScrollUp","ScrollDown"};

//        BranchedKStream<String, UserActionEvent> activeUserStream = messageStream.split();
//        activeUserStream
//                .branch(
//                        (key, v) -> true, // condition
//                        Branched.withConsumer(ks -> processCategoryEvent(ks)))
//                .branch(
//                        (key, v) -> true, // condition
//                        Branched.withConsumer(ks -> processAllUserEvent(ks)));
        KStream<String, UserActionEvent> userStream =  messageStream.filter((k,v)-> true);
        KStream<String, UserActionEvent> categoryStream =  messageStream.filter((k,v)-> true);
        this.processAllUserEvent(userStream);
        this.processCategoryEvent(categoryStream);
    }
    private void processCategoryEvent(KStream<String, UserActionEvent> ks) {
        ks.map((key, value) -> new KeyValue<>(value.getAction(), value))
                .groupByKey( Grouped.with(Serdes.String(), UserActionEventSerdes.serdes()))
                .windowedBy(TimeWindows.ofSizeWithNoGrace(windowSize))
                .count(Materialized.as("categoryRank"));
    }
    private void processAllUserEvent(KStream<String, UserActionEvent> ks) {
        ks.groupByKey(Grouped.with(Serdes.String(), UserActionEventSerdes.serdes()))
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
