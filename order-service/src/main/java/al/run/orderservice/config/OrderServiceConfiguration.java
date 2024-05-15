package al.run.orderservice.config;

import al.run.orderservice.business.dto.OrderDto;
import al.run.orderservice.persistence.model.Order;
import al.run.orderservice.persistence.model.OrderLineItem;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OrderServiceConfiguration {

    @Bean
    public ModelMapper modelMapper() {
//        ModelMapper modelMapper = new ModelMapper();
//        modelMapper.typeMap(Order.class, OrderDto.class)
//                .addMappings(mapper -> mapper.map(src -> src.getOrderLineItems().stream().mapToInt(OrderLineItem::getPrice).sum(), OrderDto::setPrice));
        return new ModelMapper();
    }
}
