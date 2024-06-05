package al.run.notificationservicemodule.services;

import al.run.notificationservicemodule.events.OrderPlacedEvent;
import al.run.notificationservicemodule.events.UserChallengeUpdated;

public interface NotificationService {

    void sendMail(OrderPlacedEvent orderPlacedEvent);
    void sendMail(UserChallengeUpdated userChallengeUpdated);
}
