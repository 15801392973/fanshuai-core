package com.fanshuai.consumer;

import com.fanshuai.common.KafkaEvent;
import com.fanshuai.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class KafkaReceiverImpl implements KafkaReceiver {
    private KafkaConsumer<String, String> consumer;
    private BlockingQueue<KafkaEvent> blockingQueue;
    private String topic;
    private String groupId;

    public KafkaReceiverImpl(String topic, String groupId) {
        this.topic = topic;
        this.groupId = groupId;

        Map<String, String> consumerMap = KafkaConfig.consumerMap;
        consumerMap.put("group.id", this.groupId);

        Properties prop = new Properties();
        Iterator<String> iterator = consumerMap.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            prop.setProperty(key, consumerMap.get(key));
        }

        consumer = new KafkaConsumer<String, String>(prop);
        consumer.subscribe(Arrays.asList(this.topic), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> collection) {

            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> collection) {

            }
        });

        blockingQueue = new LinkedBlockingQueue<>();

        new Thread(new ReceiveThread()).start();
    }

    public KafkaEvent next() {
        KafkaEvent event = blockingQueue.poll();
        if (null != event) {
            System.out.println("receive message, " + event.toString());
        }
        return event;
    }

    public KafkaEvent next(long timeout) {
        KafkaEvent event = null;
        try {
            event = blockingQueue.poll(timeout, TimeUnit.MILLISECONDS);
            if (null != event) {
                System.out.println("receive message, " + event.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return event;
    }

    private class ReceiveThread implements Runnable {
        public void run() {
            ConsumerRecords<String, String> records = consumer.poll(10000L);
            //消息确认，保证拉取消息时从最新offset拉取
            consumer.commitAsync();
            if (!records.isEmpty()) {
                for (ConsumerRecord<String, String> record : records) {
                    KafkaEvent event = new KafkaEvent();
                    event.setTopic(topic);
                    event.setKey(record.key());
                    event.setValue(record.value());
                    blockingQueue.offer(event);
                }
            }
        }
    }
}
