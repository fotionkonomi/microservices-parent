package al.run.inventoryservice.rest.dto.response;

import lombok.Data;

@Data
public class InventoryStockResponse {
    private Long raceId;
    private boolean isInStock;
}
