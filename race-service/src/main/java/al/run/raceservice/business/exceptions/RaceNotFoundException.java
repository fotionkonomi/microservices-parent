package al.run.raceservice.business.exceptions;

import al.run.raceservice.business.exceptions.messages.MessageConstants;
import org.springframework.http.HttpStatus;

public class RaceNotFoundException extends RaceServiceException {
    public RaceNotFoundException(Long raceId) {
        super(String.format(MessageConstants.RACE_NOT_FOUND, raceId), HttpStatus.NOT_FOUND);
    }
}
