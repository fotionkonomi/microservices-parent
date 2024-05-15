package al.run.userservice.exception;

import al.run.userservice.exception.common.UserServiceException;
import al.run.userservice.util.MessageConstants;
import org.springframework.http.HttpStatus;

public class EmailViolationException extends UserServiceException {
    public EmailViolationException() {
        super(MessageConstants.EMAIL_VIOLATION_MESSAGE, HttpStatus.CONFLICT);
    }
}
