package com.fanshuai.producer;

import com.fanshuai.config.KafkaConfig;
import org.apache.commons.collections.MapUtils;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

public class KafkaSenderImpl implements KafkaSender {
    private Producer<String, String> producer;

    public KafkaSenderImpl() {
        Properties properties = new Properties();
        Iterator<String> iterator = KafkaConfig.producerMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            String value = KafkaConfig.producerMap.get(key);
            properties.setProperty(key, value);
        }
        producer = new KafkaProducer<String, String>(properties);
    }

    public void sendMessage(String topic, String key, String value) {
        System.out.println("send kafka message, topic=" + topic + ", key=" + key + ", value=" + value);
        producer.send(new ProducerRecord<>(topic, key, value));
    }

    public void sendMessage(String topic, Map<String, String> messages) {
        if (MapUtils.isNotEmpty(messages)) {
            for (Map.Entry<String, String> entry : messages.entrySet()) {
                producer.send(new ProducerRecord<>(topic, entry.getKey(), entry.getValue()));
            }
        }
    }
}
