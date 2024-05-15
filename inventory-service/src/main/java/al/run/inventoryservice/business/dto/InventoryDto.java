package al.run.inventoryservice.business.dto;

import lombok.Data;

@Data
public class InventoryDto {
    private Long id;
    private Long raceId;
    private Integer quantity;
}
