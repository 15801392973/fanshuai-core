package com.fanshuai.consumer;

public interface EventConsumer {
    void consumeEvent(String queueName, EventHandler handler);
}
