package com.fanshuai;

import com.rabbitmq.client.Address;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class AbstractRabbitBuilder {
    protected ConnectionFactory connectionFactory = null;
    protected Connection connection;
    protected Channel channel;
    protected Address[] addresses;

    public void init(Address[] addresses) {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setVirtualHost(RabbitMQConfig.DEFAULT_VHOST);
        connectionFactory.setUsername(RabbitMQConfig.DEFAULT_USER);
        connectionFactory.setPassword(RabbitMQConfig.DEFAULT_PASSWORD);

        connectionFactory.setHost(RabbitMQConfig.DEFAULT_HOST);
        connectionFactory.setPort(RabbitMQConfig.DEFAULT_PORT);
        connectionFactory.setConnectionTimeout(30000);
        connectionFactory.setAutomaticRecoveryEnabled(true);

        try {
            if (null != addresses) {
                connection = connectionFactory.newConnection(addresses);
            } else {
                connection = connectionFactory.newConnection();
            }

            channel = connection.createChannel();
            channel.exchangeDeclare(RabbitMQConfig.DEFAULT_EXCHANGE, "topic");
            this.addresses = addresses;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
