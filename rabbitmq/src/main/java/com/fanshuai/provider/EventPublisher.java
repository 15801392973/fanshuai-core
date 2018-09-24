package com.fanshuai.provider;

public interface EventPublisher {

    void publish(String routingKey, String event);
}
