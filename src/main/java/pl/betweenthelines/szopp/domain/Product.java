package pl.betweenthelines.szopp.domain;

import lombok.*;
import pl.betweenthelines.szopp.exception.SzoppException;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

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
    @Column(name = "short_description")
    private String shortDescription;

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

    @Setter
    @Column(name = "is_active")
    private boolean isActive;

    @OneToMany(mappedBy = "product")
    private List<ProductImage> images = new ArrayList<>();

    public boolean isAvailable() {
        return inStock > 0;
    }

    public void decrementStock(int count) {
        if (count > this.inStock) {
            throw new SzoppException();
        }

        this.inStock = this.inStock - count;
    }

}
