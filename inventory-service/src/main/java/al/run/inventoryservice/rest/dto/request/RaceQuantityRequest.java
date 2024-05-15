package al.run.inventoryservice.rest.dto.request;

import lombok.Data;

@Data
public class RaceQuantityRequest {
    private Long raceId;
    private int quantity;
}
