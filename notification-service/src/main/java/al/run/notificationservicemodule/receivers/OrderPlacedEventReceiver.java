package al.run.notificationservicemodule.receivers;

import al.run.notificationservicemodule.events.OrderPlacedEvent;
import al.run.notificationservicemodule.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class OrderPlacedEventReceiver {


    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "notificationTopic", groupId = "notificationId", containerFactory = "listenerContainerFactory")
    public void handleNotification(OrderPlacedEvent orderPlacedEvent) {
        notificationService.sendMail(orderPlacedEvent);
    }
}
