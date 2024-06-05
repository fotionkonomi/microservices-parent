package al.run.challengeservice.scheduler;

import al.run.challengeservice.business.service.ChallengeService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    private final ChallengeService challengeService;

    @Scheduled(fixedRate = 20000)
    public void checkIfChallengesEnded() {
        challengeService.checkChallengeStartAndUpdate();
        challengeService.checkChallengeEndAndUpdate();
    }

}
