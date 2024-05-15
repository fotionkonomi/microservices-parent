package al.run.inventoryservice.business.exceptions;

import al.run.inventoryservice.business.exceptions.messages.MessageConstants;
import org.springframework.http.HttpStatus;

public class NotEnoughStockException extends InventoryServiceException{

    public NotEnoughStockException(Long raceId) {
        super(String.format(MessageConstants.NOT_ENOUGH_STOCK_MESSAGE, raceId), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
