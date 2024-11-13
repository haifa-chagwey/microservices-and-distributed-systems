package com.haifachagwey.notification.rabbitmq;

import com.haifachagwey.clients.notification.NotificationRequest;
import com.haifachagwey.notification.NotificationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;


@AllArgsConstructor
@Slf4j
@Component
public class NotificationConsumer {

    private final NotificationService notificationService;

//    When a message comes to  a queue, RabbitMQ will be notified and call the send function of notification service
    @RabbitListener(queues = "${rabbitmq.queues.notification}")
    public void consumer(NotificationRequest notificationRequest) {
        log.info("Consumed {} from the queue", notificationRequest);
        notificationService.send(notificationRequest);
    }
}
