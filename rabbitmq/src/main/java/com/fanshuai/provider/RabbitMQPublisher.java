package com.fanshuai.provider;

import com.fanshuai.AbstractRabbitBuilder;
import com.fanshuai.RabbitMQConfig;
import com.rabbitmq.client.Address;

public class RabbitMQPublisher extends AbstractRabbitBuilder implements EventPublisher {

    public RabbitMQPublisher(String host, String port) {
        Address address = new Address(host, Integer.valueOf(port));
        Address[] addresses = new Address[] {address};
        init(addresses);
    }

    public void publish(String routingKey, String event) {
        try {
            super.channel.basicPublish(RabbitMQConfig.DEFAULT_EXCHANGE, routingKey, null, event.getBytes());
            System.out.println("send mq message, event=" + event + ", routingkey = " + routingKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
