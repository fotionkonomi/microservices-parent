package al.run.notificationservicemodule.services.dto;

import lombok.Data;

@Data
public class OrderLineItemDto {
    private Long id;
    private Long raceId;
    private Long userId;
    private int quantity;
    private Integer price;
}
