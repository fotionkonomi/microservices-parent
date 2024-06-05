package al.run.challengeservice.business.service;

import al.run.challengeservice.business.dto.ChallengeDto;
import al.run.challengeservice.business.dto.RegisterUserToChallengeDto;
import al.run.challengeservice.business.dto.UserChallengeDto;
import al.run.challengeservice.business.events.RunSavedEvent;
import al.run.challengeservice.persistence.entity.UserChallenge;

public interface ChallengeService {

    ChallengeDto saveChallenge(ChallengeDto challengeDto);

    UserChallengeDto registerUserToChallenge(RegisterUserToChallengeDto registerUserToChallengeDto);

    void checkChallengeEndAndUpdate();

    void checkChallengeStartAndUpdate();

    void trackUpdate(RunSavedEvent runSavedEvent);

}
