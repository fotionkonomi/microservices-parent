package al.run.userservice.exception.common;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public abstract class UserServiceException extends RuntimeException {

    private String messageKey;

    private HttpStatus httpStatus;

    public UserServiceException(String messageKey) {
        super(messageKey);
        this.messageKey = messageKey;
        this.httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
    }

    public UserServiceException(String messageKey, HttpStatus httpStatus) {
        super(messageKey);
        this.messageKey = messageKey;
        this.httpStatus = httpStatus;
    }
}