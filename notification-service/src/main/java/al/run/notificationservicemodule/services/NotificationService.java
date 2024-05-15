package al.run.notificationservicemodule.services;

import al.run.notificationservicemodule.events.OrderPlacedEvent;

public interface NotificationService {

    void sendMail(OrderPlacedEvent orderPlacedEvent);
}
