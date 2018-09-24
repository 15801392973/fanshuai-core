package com.fanshuai.consumer;

import com.fanshuai.AbstractRabbitBuilder;
import com.fanshuai.RabbitMQConfig;
import com.rabbitmq.client.*;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

public class RabbitMQConsumer extends AbstractRabbitBuilder implements EventConsumer {

    private String queueName;
    private List<String> routingKeys;

    public RabbitMQConsumer(String host, String port) {
        Address address = new Address(host, Integer.valueOf(port));
        Address[] addresses = new Address[] {address};
        super.init(addresses);
    }

    public void bindKeys(String queueName, List<String> routingKeys) {
        try {
            this.queueName = queueName;
            super.channel.queueDeclare(queueName, true, false, false, null);

            if (CollectionUtils.isNotEmpty(routingKeys)) {
                this.routingKeys = routingKeys;

                for (String routingKey : routingKeys) {
                    super.channel.queueBind(queueName, RabbitMQConfig.DEFAULT_EXCHANGE, routingKey);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void consumeEvent(String queueName, EventHandler handler) {
        DefaultConsumer consumer = new DefaultConsumer(super.channel) {
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                String event = new String(body);
                String routingKey = envelope.getRoutingKey();

                if (routingKeys.contains(routingKey)) {
                    handler.handle(event);
                    System.out.println("handle event, event=" + event + ", routing key=" + routingKey + ", exchange=" + envelope.getExchange());
                }
            }
        };
        try {
            super.channel.basicConsume(queueName, true, consumer);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
