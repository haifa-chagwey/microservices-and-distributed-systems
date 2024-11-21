package com.haifachagwey.clients.notification;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

// @FeignClient(name="notification", path = "api/v1/notifications")
//@FeignClient(name="notification", url = "http://localhost:8082")
@FeignClient(name="notification", url = "${clients.notification.url}")
public interface NotificationClient {
    @PostMapping
    void sendNotification(@RequestBody NotificationRequest notificationRequest);
}
