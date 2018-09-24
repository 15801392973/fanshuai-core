package com.fanshuai.producer;

import java.util.Map;

public interface KafkaSender {
    void sendMessage(String topic, String key, String value);
    void sendMessage(String topic, Map<String, String> messages);
}
