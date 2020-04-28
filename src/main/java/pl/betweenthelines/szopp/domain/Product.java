package pl.betweenthelines.szopp.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;

import static javax.persistence.GenerationType.IDENTITY;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "app_product")
public class Product {

    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id")
    private Long id;

    @Setter
    @Column(name = "name")
    private String name;

    @Setter
    @Column(name = "description")
    private String description;

    @Setter
    @Column(name = "price")
    private BigDecimal price;

    @Setter
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @Setter
    @Column(name = "in_stock")
    private int inStock;

    public boolean isAvailable() {
        return inStock > 0;
    }

}
