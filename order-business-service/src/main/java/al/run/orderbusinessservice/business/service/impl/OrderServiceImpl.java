package al.run.orderbusinessservice.business.service.impl;

import al.run.orderbusinessservice.business.dto.*;
import al.run.orderbusinessservice.business.event.OrderPlacedEvent;
import al.run.orderbusinessservice.business.exceptions.model.HttpErrorDto;
import al.run.orderbusinessservice.business.rollback.RollbackUtil;
import al.run.orderbusinessservice.business.service.OrderService;
import com.google.gson.Gson;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final RestTemplate restTemplate;
    private final Gson gson;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;

    @Override
    public OrderDto makeOrder(List<OrderLineItemDto> orderLineItems, Long loggedInUser) {
        OrderDto orderDtoAfterSaving = null;
        try {
            InventoryStockRequest inventoryStockRequest = new InventoryStockRequest();
            inventoryStockRequest.setRaceQuantitiesRequests(orderLineItems.stream().map(orderLineItemDto -> new RaceQuantityRequest(orderLineItemDto.getRaceId(), orderLineItemDto.getQuantity())).toList());
            ResponseEntity<InventoryStockResponse[]> inventoryStockResponses = restTemplate.postForEntity("http://inventory-service/api/inventory/stockAvailability",
                    inventoryStockRequest,
                    InventoryStockResponse[].class);
            orderLineItems.removeIf(orderLineItem -> !Arrays.stream(inventoryStockResponses.getBody())
                    .filter(inventoryStockResponse -> orderLineItem.getRaceId().equals(inventoryStockResponse.getRaceId())).findFirst().get().isInStock());
            orderLineItems.forEach(orderLineItemDto -> orderLineItemDto.setUserId(loggedInUser));
            OrderDto orderDto = new OrderDto();
            orderDto.setOrderLineItems(orderLineItems);

            ResponseEntity<OrderDto> orderResponse = restTemplate.postForEntity("http://order-service/api/order/save", orderDto, OrderDto.class);
            orderDtoAfterSaving = orderResponse.getBody();
            InventoryStockRequest inventoryStockRequestForDeductingCorrespondingStock = new InventoryStockRequest();
            inventoryStockRequestForDeductingCorrespondingStock.setRaceQuantitiesRequests(orderLineItems.stream().map(orderLineItemDto -> new RaceQuantityRequest(orderLineItemDto.getRaceId(), orderLineItemDto.getQuantity())).toList());

            restTemplate.postForEntity("http://inventory-service/api/inventory/deduct/stock", inventoryStockRequestForDeductingCorrespondingStock, Void.class);

            kafkaTemplate.send("notificationTopic", new OrderPlacedEvent(orderDtoAfterSaving.getOrderId(), loggedInUser));
            return orderResponse.getBody();
        } catch (HttpServerErrorException e) {
            HttpErrorDto httpErrorDto = gson.fromJson(e.getResponseBodyAs(String.class), HttpErrorDto.class);
            throw RollbackUtil.rollback(httpErrorDto, this, orderDtoAfterSaving.getOrderId());
        } catch (RestClientException e) {
            HttpErrorDto httpErrorDto = new HttpErrorDto();
            httpErrorDto.setErrorCode(HttpStatus.BAD_GATEWAY.value());
            httpErrorDto.setMessage(e.getMessage());
            httpErrorDto.setExceptionName(e.getClass().getSimpleName());
            if (orderDtoAfterSaving != null) {
                throw RollbackUtil.rollback(httpErrorDto, this, orderDtoAfterSaving.getOrderId());
            }
            throw e;
        }

    }

    @Override
    public void deleteOrder(Long orderId) {
        restTemplate.delete("http://order-service/api/order/delete/" + orderId);
    }
}
