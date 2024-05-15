package al.run.orderservice.business.service.impl;

import al.run.orderservice.business.dto.OrderDto;
import al.run.orderservice.business.exceptions.OrderNotFoundException;
import al.run.orderservice.business.exceptions.OrderObjectBadRequestException;
import al.run.orderservice.business.service.IOrderService;
import al.run.orderservice.persistence.model.Order;
import al.run.orderservice.persistence.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements IOrderService {

    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    @Override
    public OrderDto saveOrder(OrderDto orderDto) {
        if (CollectionUtils.isEmpty(orderDto.getOrderLineItems())) {
            throw new OrderObjectBadRequestException();
        }
        Order mappedEntityOrder = modelMapper.map(orderDto, Order.class);
        Order savedOrder = orderRepository.save(mappedEntityOrder);
        return modelMapper.map(savedOrder, OrderDto.class);
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId) {
        orderRepository.deleteById(orderId);
    }

    @Override
    public OrderDto getOrderById(Long orderId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new OrderNotFoundException(orderId));
        return modelMapper.map(order, OrderDto.class);
    }

}
