package al.run.orderservice.persistence.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "order_line_item")
@Data
public class OrderLineItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long raceId;
    private Long userId;
    private int quantity;
    private Integer price;
}
