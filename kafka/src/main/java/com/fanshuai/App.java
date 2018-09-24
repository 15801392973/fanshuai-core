package com.fanshuai;

import com.fanshuai.common.KafkaEvent;
import com.fanshuai.consumer.KafkaReceiver;
import com.fanshuai.consumer.KafkaReceiverImpl;
import com.fanshuai.producer.KafkaSenderImpl;
import com.fanshuai.producer.KafkaSender;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) {
        KafkaSender kafkaSender = new KafkaSenderImpl();
        String topic = "fanshuai";

        kafkaSender.sendMessage(topic, "fanshuai1", "shuai1");
        kafkaSender.sendMessage(topic, "fanshuai2", "shuai2");
        kafkaSender.sendMessage(topic, "fanshuai3", "shuai3");
        kafkaSender.sendMessage(topic, "fanshuai4", "shuai4");
        kafkaSender.sendMessage(topic, "fanshuai5", "shuai5");

        try {
            //Thread.sleep(5000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        KafkaReceiver receiver = new KafkaReceiverImpl("fanshuai", "group.fanshuai");
        while (true) {
            KafkaEvent event = receiver.next(1000L);
            if (null != event) {
                System.out.println("main receive event, " + event);
            }
        }
    }
}
