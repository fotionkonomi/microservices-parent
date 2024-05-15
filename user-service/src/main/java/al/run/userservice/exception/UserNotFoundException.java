package al.run.userservice.exception;

import al.run.userservice.exception.common.UserServiceException;
import al.run.userservice.util.MessageConstants;

public class UserNotFoundException extends UserServiceException {
    public UserNotFoundException() {
        super(MessageConstants.MSG_USER_NOT_FOUND_EXCEPTION);
    }

}
