package com.darshil.notification.rabbitmq;

import com.darshil.clients.notification.NotificationRequest;
import com.darshil.notification.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@AllArgsConstructor
public class NotificationConsumer {
    private final NotificationService notificationService;

    @RabbitListener(queues = "${rabbitmq.queue.notification}")
    public void consumer(NotificationRequest notificationRequest) {
        log.info("Consumed {} from queue ", notificationRequest);
        notificationService.saveNotificationSentByDarshil(notificationRequest);
    }
}
