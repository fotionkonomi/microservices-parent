package al.run.orderbusinessservice.business.service;

import al.run.orderbusinessservice.business.dto.OrderDto;
import al.run.orderbusinessservice.business.dto.OrderLineItemDto;

import java.util.List;

public interface OrderService {
    OrderDto makeOrder(List<OrderLineItemDto> orderLineItems, Long loggedInUser);

    void deleteOrder(Long orderId);
}
