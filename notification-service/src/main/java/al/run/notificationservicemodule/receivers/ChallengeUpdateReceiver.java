package al.run.notificationservicemodule.receivers;

import al.run.notificationservicemodule.events.UserChallengeUpdated;
import al.run.notificationservicemodule.services.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ChallengeUpdateReceiver {
    @Autowired
    private NotificationService notificationService;

    @KafkaListener(topics = "userChallengeUpdated", groupId = "notificationId", containerFactory = "listenerContainerFactoryChallenge")
    public void handleNotification(UserChallengeUpdated userChallengeUpdated) {
        notificationService.sendMail(userChallengeUpdated);
    }
}
