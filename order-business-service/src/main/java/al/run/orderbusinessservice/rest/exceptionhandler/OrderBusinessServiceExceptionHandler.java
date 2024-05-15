package al.run.orderbusinessservice.rest.exceptionhandler;

import al.run.orderbusinessservice.business.exceptions.model.HttpErrorDto;
import al.run.orderbusinessservice.business.exceptions.model.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class OrderBusinessServiceExceptionHandler {

    @ExceptionHandler(HttpException.class)
    public ResponseEntity<HttpErrorDto> handleExceptions(HttpException exception) {
        HttpErrorDto httpErrorDto = exception.getHttpErrorDto();
        return ResponseEntity.status(httpErrorDto.getErrorCode()).body(httpErrorDto);
    }
}
