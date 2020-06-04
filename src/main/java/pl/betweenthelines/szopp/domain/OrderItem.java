package pl.betweenthelines.szopp.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_order_item")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @OneToOne
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Column(name = "price")
    private BigDecimal price;

    @Setter
    @Column(name = "quantity")
    private int quantity;

    public boolean isProduct(Product product) {
        return this.product.equals(product);
    }

    public void incrementQuantity() {
        quantity = quantity + 1;
    }

    public void decrementQuantity(int count) {
        quantity = quantity - count;
    }

    public BigDecimal getTotalPrice() {
        return price.multiply(BigDecimal.valueOf(quantity));
    }

}
