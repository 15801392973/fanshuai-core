package com.fanshuai.consumer;

import com.fanshuai.common.KafkaEvent;

public interface KafkaEventProcessor {
    void processEvent(KafkaEvent event);
}
