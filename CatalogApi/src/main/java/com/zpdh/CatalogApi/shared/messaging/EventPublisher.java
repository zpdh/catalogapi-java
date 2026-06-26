package com.zpdh.CatalogApi.shared.messaging;

import com.zpdh.CatalogApi.shared.config.RabbitMQConfig;
import com.zpdh.CatalogApi.shared.messaging.payload.EventPayload;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class EventPublisher {
    private final RabbitTemplate rabbitTemplate;

    public EventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public <E extends EventPayload> void publish(String routingKey, E payload) {
        DomainEvent<E> event = new DomainEvent<>(routingKey, payload, Instant.now().toString());

        rabbitTemplate.convertAndSend(RabbitMQConfig.EXCHANGE, routingKey, event);
    }
}

