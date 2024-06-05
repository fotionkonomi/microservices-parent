package al.run.challengeservice.business.events.consumer;

import al.run.challengeservice.business.events.RunSavedEvent;
import al.run.challengeservice.business.service.ChallengeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class RunSavedEventListener {

    @Autowired
    private ChallengeService challengeService;

    @KafkaListener(topics = "runAddedTopic", groupId = "challengeId", containerFactory = "listenerContainerFactory")
    public void handleRunAdding(RunSavedEvent runSavedEvent) {
        challengeService.trackUpdate(runSavedEvent);
    }
}
