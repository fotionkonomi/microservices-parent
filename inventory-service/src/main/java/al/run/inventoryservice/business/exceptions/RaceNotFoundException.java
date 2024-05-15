package al.run.inventoryservice.business.exceptions;

import al.run.inventoryservice.business.exceptions.messages.MessageConstants;
import org.springframework.http.HttpStatus;

public class RaceNotFoundException extends InventoryServiceException {
    public RaceNotFoundException(Long raceId) {
        super(String.format(MessageConstants.RACE_NOT_FOUND_MESSAGE, raceId), HttpStatus.NOT_FOUND);
    }
}
