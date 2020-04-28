package pl.betweenthelines.szopp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.betweenthelines.szopp.domain.Customer;
import pl.betweenthelines.szopp.domain.Order;
import pl.betweenthelines.szopp.domain.OrderStatus;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByIdAndStatus(Long id, OrderStatus status);

    List<Order> findAllByCustomerAndStatusNot(Customer customer, OrderStatus status);

    List<Order> findAllByStatus(OrderStatus status);

    List<Order> findAllByStatusNot(OrderStatus status);

}
