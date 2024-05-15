package al.run.inventoryservice.business.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

public abstract class InventoryServiceException extends RuntimeException {

    /**
     * Stores the message that will be sent to the consumer of our web services
     */
    @Getter
    @Setter
    private String messageKey;

    @Getter
    @Setter
    private HttpStatus httpStatus;

    public InventoryServiceException(String messageKey, HttpStatus httpStatus) {
        super(messageKey);
        this.messageKey = messageKey;
        this.httpStatus = httpStatus;
    }
}
