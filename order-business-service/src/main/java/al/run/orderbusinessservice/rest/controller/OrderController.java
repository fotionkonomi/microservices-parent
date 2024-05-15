package al.run.orderbusinessservice.rest.controller;

import al.run.orderbusinessservice.business.dto.OrderDto;
import al.run.orderbusinessservice.business.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/order-bs")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping("/make")
    public ResponseEntity<OrderDto> makeOrder(@RequestBody OrderDto orderDto, @RequestHeader("loggedInUser") Long loggedInUser) {
        return ResponseEntity.ok(orderService.makeOrder(orderDto.getOrderLineItems(), loggedInUser));
    }
}
