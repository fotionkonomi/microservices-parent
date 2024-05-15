package al.run.orderservice.business.exceptions;

import al.run.orderservice.business.exceptions.messages.MessageConstants;
import org.springframework.http.HttpStatus;

public class OrderObjectBadRequestException extends OrderServiceException {
    public OrderObjectBadRequestException() {
        super(String.format(MessageConstants.ORDER_NOT_FORMED_CORRECTLY), HttpStatus.BAD_REQUEST);
    }
}
