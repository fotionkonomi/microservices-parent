package al.run.challengeservice.business.exception;

import org.springframework.http.HttpStatus;

public class ChallengeNotFoundException extends ChallengeServiceException{
    public ChallengeNotFoundException(Long challengeId) {
        super(String.format(MessageConstants.CHALLENGE_NOT_FOUND, challengeId), HttpStatus.NOT_FOUND);
    }
}
