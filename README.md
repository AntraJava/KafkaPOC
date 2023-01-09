# Apache Kafka Stream Demo

# As of 1/9/2023, this demo will NOT work on Apple Chips(M1/M2) due to dependency issue.

## Use Windows or Linux 

## Producer
 - Keep putting User Action Events into the Kafka topic
 - User Action Events includes - user_id, action_type, action_data

## Consumer
 - Stream Application based on Springboot, Spring kafka, kafka-Stream.
 - Consuming messages from the topic - create stream data process
 - stream will be duplicated into two.
 - 1 stream used for ranking the user activities in a timed-window (30 seconds)
 - the other stream used for ranking activity types in a timed-window (30 seconds)