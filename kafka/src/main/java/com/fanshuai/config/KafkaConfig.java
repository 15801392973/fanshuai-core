package com.fanshuai.config;

import java.util.HashMap;
import java.util.Map;

public class KafkaConfig {
    public static final Map<String, String> producerMap = new HashMap<>();
    public static final Map<String, String> consumerMap = new HashMap<>();

    private static final String BOOTSRAP_SERVER = "127.0.0.1:9092";

    static {
        producerMap.put("bootstrap.servers", BOOTSRAP_SERVER);
        producerMap.put("acks", "all");
        producerMap.put("retries", "0");
        producerMap.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        producerMap.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        consumerMap.put("bootstrap.servers", BOOTSRAP_SERVER);
        consumerMap.put("enable.auto.commit", "true");
        consumerMap.put("auto.commit.interval.ms", "1000");
        consumerMap.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        consumerMap.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
    }
}
