package al.run.orderservice.business.exceptions;

import al.run.orderservice.business.exceptions.messages.MessageConstants;
import org.springframework.http.HttpStatus;

public class OrderNotFoundException extends OrderServiceException {
    public OrderNotFoundException(Long orderId) {
        super(String.format(MessageConstants.ORDER_NOT_FOUND, orderId), HttpStatus.NOT_FOUND);
    }
}
