package al.run.notificationservicemodule.services;

import al.run.notificationservicemodule.events.OrderPlacedEvent;
import al.run.notificationservicemodule.services.dto.OrderDto;
import al.run.notificationservicemodule.services.dto.RaceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final RestTemplate restTemplate;

    @Override
    public void sendMail(OrderPlacedEvent orderPlacedEvent) {
        StringBuilder body = new StringBuilder();
        ResponseEntity<OrderDto> orderDtoResponseEntity = restTemplate.getForEntity("http://order-service/api/order", OrderDto.class, orderPlacedEvent.getOrderId());
        if (orderDtoResponseEntity.getStatusCode().is2xxSuccessful()) {
            OrderDto orderDto = orderDtoResponseEntity.getBody();
            orderDto.getOrderLineItems().forEach(orderLineItemDto -> {

            });
        }


    }

}
