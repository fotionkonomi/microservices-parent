package al.run.orderservice.business.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OrderDto {

    private Long orderId;
//    private Integer price; Do e shtojme
    private List<OrderLineItemDto> orderLineItems = new ArrayList<>();
}
