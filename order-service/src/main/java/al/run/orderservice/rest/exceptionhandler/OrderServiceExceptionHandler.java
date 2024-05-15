package al.run.orderservice.rest.exceptionhandler;

import al.run.orderservice.business.exceptions.OrderServiceException;
import al.run.orderservice.rest.exceptionhandler.model.HttpErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class OrderServiceExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(OrderServiceException.class)
    public ResponseEntity<HttpErrorResponse> handleServiceExceptions(OrderServiceException exception, HttpServletRequest request) {
        log.error("Exception from service layer in ORDER MICROSERVICE: " + exception);
        return fillHttpErrorResponse(request, exception.getMessageKey(), exception.getHttpStatus());
    }

    private ResponseEntity<HttpErrorResponse> fillHttpErrorResponse(HttpServletRequest request, String localizedMessage, HttpStatus httpStatus) {
        HttpErrorResponse httpErrorResponse = new HttpErrorResponse();
        httpErrorResponse.setLocalizedMessage(localizedMessage);
        httpErrorResponse.setErrorCode(httpStatus.value());
        httpErrorResponse.setPath(request.getRequestURI());
        httpErrorResponse.setMessage("Order MicroService Exception - " + httpStatus.getReasonPhrase());
        return ResponseEntity.status(httpStatus).body(httpErrorResponse);
    }
}
