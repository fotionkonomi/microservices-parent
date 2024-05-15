package al.run.orderservice.business.service;

import al.run.orderservice.business.dto.OrderDto;

public interface IOrderService {

    OrderDto saveOrder(OrderDto orderDto);

    void deleteOrder(Long orderId);

    OrderDto getOrderById(Long orderId);
}
