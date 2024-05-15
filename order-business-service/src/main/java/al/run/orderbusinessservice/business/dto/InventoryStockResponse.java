package al.run.orderbusinessservice.business.dto;

import lombok.Data;

@Data
public class InventoryStockResponse {
    private Long raceId;
    private boolean isInStock;
}
