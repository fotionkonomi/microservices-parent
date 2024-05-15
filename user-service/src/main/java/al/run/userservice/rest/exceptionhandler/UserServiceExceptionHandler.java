package al.run.userservice.rest.exceptionhandler;

import al.run.userservice.exception.common.UserServiceException;
import al.run.userservice.rest.exceptionhandler.dto.HttpErrorResponse;
import al.run.userservice.rest.exceptionhandler.util.ExceptionMessageUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
@RequiredArgsConstructor
public class UserServiceExceptionHandler {

    private final ExceptionMessageUtil messageUtil;

    @org.springframework.web.bind.annotation.ExceptionHandler(UserServiceException.class)
    public ResponseEntity<HttpErrorResponse> handleServiceExceptions(UserServiceException exception, HttpServletRequest request) {
        log.error("Exception from service layer: " + exception);
        return fillHttpErrorResponse(request, messageUtil.getLocalizedMessage(exception.getMessage()), exception.getHttpStatus());
    }

    @org.springframework.web.bind.annotation.ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<HttpErrorResponse> handleBadCredentialsException(BadCredentialsException exception, HttpServletRequest request) {
        log.error("Exception while trying to log in: " + exception);
        return fillHttpErrorResponse(request, messageUtil.getLocalizedMessage(exception.getMessage()), HttpStatus.FORBIDDEN);
    }

    private ResponseEntity<HttpErrorResponse> fillHttpErrorResponse(HttpServletRequest request, String localizedMessage, HttpStatus httpStatus) {
        HttpErrorResponse httpErrorResponse = new HttpErrorResponse();
        httpErrorResponse.setLocalizedMessage(localizedMessage);
        httpErrorResponse.setErrorCode(httpStatus.value());
        httpErrorResponse.setPath(request.getRequestURI());
        httpErrorResponse.setMessage("UserService - " + httpStatus.getReasonPhrase());
        return ResponseEntity.status(httpStatus).body(httpErrorResponse);
    }
}