package al.run.orderservice.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "order_t")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;

    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItem> orderLineItems = new ArrayList<>();
}
