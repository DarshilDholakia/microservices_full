package com.darshil.notification;

import com.darshil.clients.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("api/v1/notification")
public record NotificationController(NotificationService notificationService) {

    @PostMapping
    public void saveNotificationSentByDarshil(@RequestBody NotificationRequest notificationRequest) {
        log.info("new notification registration {}", notificationRequest);
        notificationService.saveNotificationSentByDarshil(notificationRequest);
    }
}
