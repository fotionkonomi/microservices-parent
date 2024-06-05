package al.run.challengeservice.business.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public class ChallengeServiceException extends RuntimeException {

    @Getter
    @Setter
    private String messageKey;

    @Getter
    @Setter
    private HttpStatus httpStatus;

    public ChallengeServiceException(String messageKey, HttpStatus httpStatus) {
        super(messageKey);
        this.messageKey = messageKey;
        this.httpStatus = httpStatus;
    }
}
