package al.run.userservice.exception;

import al.run.userservice.exception.common.UserServiceException;
import al.run.userservice.util.MessageConstants;
import org.springframework.http.HttpStatus;

public class UsernameViolationException extends UserServiceException {

    public UsernameViolationException() {
        super(MessageConstants.USERNAME_VIOLATION_MESSAGE, HttpStatus.CONFLICT);
    }
}
