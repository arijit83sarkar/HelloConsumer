package com.raven;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

public class Consumer {
    private static final String HOST = "localhost";
    private static final String QUEUE = "OWL-POST";

    public static void main(String[] args) {
        // CREATE A CONNECTION FACTORY
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST);

        try {
            // CREATE A CONNECTION
            Connection connection = connectionFactory.newConnection();

            // CREATE A CHANNEL FROM CONNECTION
            Channel channel = connection.createChannel();

            // ASSIGN A QUEUE TO CHANNEL
            channel.queueDeclare(QUEUE, false, false, false, null);
            System.out.println(" Waiting for message...");

            DeliverCallback deliverCallback = (consumeMsg, delivery) -> {
                String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
                System.out.println(" Message received : '" + message + "'");
            };

            channel.basicConsume(QUEUE, true, deliverCallback, consumeMsg -> {
            });
        } catch (Exception e) {
            System.out.println(e.getMessage() + ", " + e.getStackTrace());
        }
    }
}
