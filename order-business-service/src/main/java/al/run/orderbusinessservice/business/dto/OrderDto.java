package al.run.orderbusinessservice.business.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {
    private Long orderId;
    private List<OrderLineItemDto> orderLineItems = new ArrayList<>();
}
