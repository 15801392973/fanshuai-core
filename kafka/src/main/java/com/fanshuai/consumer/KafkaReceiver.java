package com.fanshuai.consumer;

import com.fanshuai.common.KafkaEvent;

public interface KafkaReceiver {
    KafkaEvent next();
    KafkaEvent next(long timeout);
}
