package pl.betweenthelines.szopp.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pl.betweenthelines.szopp.exception.InvalidOrderStatusException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static java.math.BigDecimal.ZERO;
import static javax.persistence.GenerationType.IDENTITY;
import static pl.betweenthelines.szopp.domain.OrderStatus.NEW;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_order")
public class Order {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OrderStatus status = NEW;

    @Column(name = "date_created")
    private Date dateCreated = new Date();

    @Setter
    @Column(name = "date_sent")
    private Date dateSent;

    @Setter
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Setter
    @OneToOne(mappedBy = "order")
    private ShipmentAddress shipmentAddress;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> orderItems = new ArrayList<>();

    public void statusTransition(OrderStatus orderStatus) {
        if (!canBeChangedTo(orderStatus)) {
            throw new InvalidOrderStatusException();
        }

        this.status = orderStatus;
    }

    private boolean canBeChangedTo(OrderStatus status) {
        return OrderStatus.ALLOWED_TRANSITIONS.get(this.status)
                .contains(status);
    }

    public BigDecimal getTotalPrice() {
        return orderItems.stream()
                .map(OrderItem::getTotalPrice)
                .reduce(ZERO, BigDecimal::add);
    }

}
