package com.prac.practicum11.rabbitmq;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.text.MessageFormat;

@Component
public class MQSender {
    private final Queue queue;

    private final RabbitTemplate rabbitTemplate;

    public MQSender(Queue queue, RabbitTemplate rabbitTemplate) {
        this.queue = queue;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendMessage(String username, long n, boolean isPrime) {
        String message = MessageFormat.format("Username: {0}, Number: {1}, Is Prime: {2}", username, String.valueOf(n), isPrime);
        message = "{" + message + "}";
        rabbitTemplate.convertAndSend("primes", message);
    }
}
