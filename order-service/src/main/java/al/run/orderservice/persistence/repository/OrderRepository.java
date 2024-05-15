package al.run.orderservice.persistence.repository;

import al.run.orderservice.persistence.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
