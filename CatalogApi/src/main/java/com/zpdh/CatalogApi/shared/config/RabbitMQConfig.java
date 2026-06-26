package com.zpdh.CatalogApi.shared.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {
    public static final String EXCHANGE = "catalogapi.events";

    public static final String QUEUE_USERS = "catalogapi.users";
    public static final String QUEUE_CATEGORIES = "catalogapi.categories";
    public static final String QUEUE_PRODUCTS = "catalogapi.products";

    public static final String ROUTING_USER = "user.#";
    public static final String ROUTING_CATEGORY = "category.#";
    public static final String ROUTING_PRODUCT = "product.#";

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGE);
    }

    @Bean
    public Queue userQueue() {
        return new Queue(QUEUE_USERS, true);
    }

    @Bean
    public Queue categoryQueue() {
        return new Queue(QUEUE_CATEGORIES, true);
    }

    @Bean
    public Queue productQueue() {
        return new Queue(QUEUE_PRODUCTS, true);
    }

    @Bean
    public Binding userBinding(Queue userQueue, TopicExchange exchange) {
        return BindingBuilder.bind(userQueue).to(exchange).with(ROUTING_USER);
    }

    @Bean
    public Binding categoryBinding(Queue categoryQueue, TopicExchange exchange) {
        return BindingBuilder.bind(categoryQueue).to(exchange).with(ROUTING_CATEGORY);
    }

    @Bean
    public Binding productBinding(Queue productQueue, TopicExchange exchange) {
        return BindingBuilder.bind(productQueue).to(exchange).with(ROUTING_PRODUCT);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(
        ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(messageConverter());
        return template;
    }
}
