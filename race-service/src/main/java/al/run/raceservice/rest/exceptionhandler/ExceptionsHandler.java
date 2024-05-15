package al.run.raceservice.rest.exceptionhandler;

import al.run.raceservice.business.exceptions.RaceServiceException;
import al.run.raceservice.rest.exceptionhandler.model.HttpErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionsHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(RaceServiceException.class)
    public ResponseEntity<HttpErrorResponse> handleServiceExceptions(RaceServiceException exception, HttpServletRequest request) {
        log.error("Exception from service layer in RACE MICROSERVICE: " + exception);
        return fillHttpErrorResponse(request, exception.getMessageKey(), exception.getHttpStatus());
    }

    private ResponseEntity<HttpErrorResponse> fillHttpErrorResponse(HttpServletRequest request, String localizedMessage, HttpStatus httpStatus) {
        HttpErrorResponse httpErrorResponse = new HttpErrorResponse();
        httpErrorResponse.setLocalizedMessage(localizedMessage);
        httpErrorResponse.setErrorCode(httpStatus.value());
        httpErrorResponse.setPath(request.getRequestURI());
        httpErrorResponse.setMessage("Race MicroService Exception - " + httpStatus.getReasonPhrase());
        return ResponseEntity.status(httpStatus).body(httpErrorResponse);
    }
}
