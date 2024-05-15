package al.run.notificationservicemodule.services.dto;

import lombok.Data;

import java.util.List;

@Data
public class OrderDto {
    private Long orderId;
    private List<OrderLineItemDto> orderLineItems;
}
