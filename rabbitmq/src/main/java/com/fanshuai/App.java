package com.fanshuai;

import com.fanshuai.consumer.EventHandler;
import com.fanshuai.consumer.RabbitMQConsumer;
import com.fanshuai.provider.RabbitMQPublisher;
import org.apache.commons.collections.CollectionUtils;

import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class App 
{
    private static String host = "localhost";
    private static String port = "5672";
    private static String defaultQueueName = "default_queue";

    public static void main( String[] args ) {
        List<String> routingKeys = Arrays.asList("r1", "r2", "r3");
        Consumer consumer = new Consumer(routingKeys);
        consumer.setHandler(new EventHandler() {
            @Override
            public void handle(String event) {
                System.out.println("handled:" + event);
            }
        });
        new Thread(consumer).start();

        Producer producer = new Producer(routingKeys);
        new Thread(producer).start();
    }

    private static class Producer implements Runnable {
        private List<String> routingKeys;
        private RabbitMQPublisher publisher;

        public Producer(List<String> routingKeys) {
            this.routingKeys = routingKeys;
            publisher = new RabbitMQPublisher(host, port);
        }

        public void run() {
            String message = "hello world";

            if (CollectionUtils.isNotEmpty(routingKeys)) {
                for (String key : routingKeys) {
                    publisher.publish(key, message);
                }
            }
        }
    }

    private static class Consumer implements Runnable {
        private List<String> routingKeys;
        private RabbitMQConsumer consumer;
        private EventHandler handler;

        public Consumer(List<String> routingKeys) {
            this.routingKeys = routingKeys;
            consumer = new RabbitMQConsumer(host, port);
            consumer.bindKeys(defaultQueueName, routingKeys);
        }

        public void setHandler(EventHandler handler) {
            this.handler = handler;
        }

        public void run() {
            consumer.consumeEvent(defaultQueueName, handler);
        }
    }
}
