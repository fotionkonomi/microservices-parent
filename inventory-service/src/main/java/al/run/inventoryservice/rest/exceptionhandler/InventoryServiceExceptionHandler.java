package al.run.inventoryservice.rest.exceptionhandler;

import al.run.inventoryservice.business.exceptions.InventoryServiceException;
import al.run.inventoryservice.rest.exceptionhandler.model.HttpErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class InventoryServiceExceptionHandler {

    @org.springframework.web.bind.annotation.ExceptionHandler(InventoryServiceException.class)
    public ResponseEntity<HttpErrorResponse> handleServiceExceptions(InventoryServiceException exception, HttpServletRequest request) {
        log.error("Exception from service layer in INVENTORY MICROSERVICE: " + exception);
        return fillHttpErrorResponse(request, exception.getMessageKey(), exception.getHttpStatus(), exception.getClass().getSimpleName());
    }

    private ResponseEntity<HttpErrorResponse> fillHttpErrorResponse(HttpServletRequest request, String localizedMessage, HttpStatus httpStatus, String exceptionName) {
        HttpErrorResponse httpErrorResponse = new HttpErrorResponse();
        httpErrorResponse.setLocalizedMessage(localizedMessage);
        httpErrorResponse.setErrorCode(httpStatus.value());
        httpErrorResponse.setPath(request.getRequestURI());
        httpErrorResponse.setMessage("Inventory MicroService Exception - " + httpStatus.getReasonPhrase());
        httpErrorResponse.setExceptionName(exceptionName);
        return ResponseEntity.status(httpStatus).body(httpErrorResponse);
    }
}
