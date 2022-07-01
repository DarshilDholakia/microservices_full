package com.darshil.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

//@FeignClient(
//        value = "notification",
//        path = "api/v1/notification"
//)
@FeignClient("notification")
public interface NotificationClient {

    @PostMapping (path = "api/v1/notification")
    void saveNotificationSentByDarshil(@RequestBody NotificationRequest notificationRequest);
}
