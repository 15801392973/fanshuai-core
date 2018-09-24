package com.fanshuai.common;

public class KafkaEvent {
    private String topic;
    private String key;
    private String value;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String toString() {
        return "kafkaEvent[topic= " + topic + ", key=" + key + ", value=" + value + "]";
    }
}
