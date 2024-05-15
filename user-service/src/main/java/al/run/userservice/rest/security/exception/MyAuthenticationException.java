package al.run.userservice.rest.security.exception;

import al.run.userservice.rest.security.constant.SecurityConstants;
import org.springframework.security.core.AuthenticationException;

public class MyAuthenticationException extends AuthenticationException {
    public MyAuthenticationException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public MyAuthenticationException() {
        super(SecurityConstants.TOKEN_INVALID);
    }
}
