package com.darshil.notification;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class NotificationConfig {

    // the below gives us access to the values in app.yml file, and we're saving them into the below String variables
    @Value("${rabbitmq.exchanges.internal}")
    private String internalExchange; // value for this is coming from the app.yml file using the above annotation

    @Value("${rabbitmq.queue.notification}")
    private String notificationQueue;

    @Value("${rabbitmq.routing-keys.internal-notification}")
    private String internalNotificationRoutingKey;

    // DEFINING THE EXCHANGE
    @Bean
    public TopicExchange internalTopicExchange() {
        return new TopicExchange(this.internalExchange);
    }

    // DEFINING THE QUEUE
    @Bean
    public Queue notificationQueue() {
        return new Queue(this.notificationQueue);
    }

    // DEFINING THE BINDING BETWEEN QUEUE AND EXCHANGE WITH THE ROUTING KEY FIELD
    @Bean
    public Binding internalToNotificationBinding() {
        return BindingBuilder
                .bind(notificationQueue())
                .to(internalTopicExchange())
                .with(this.internalNotificationRoutingKey);
    }

    public String getInternalExchange() {
        return internalExchange;
    }

    public String getNotificationQueue() {
        return notificationQueue;
    }

    public String getInternalNotificationRoutingKey() {
        return internalNotificationRoutingKey;
    }
}
