package pl.betweenthelines.szopp.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;


@Getter
@NoArgsConstructor
@Entity
@Table(name = "app_category")
public class Category {

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
    @ManyToOne
    @JoinColumn(name = "parent_category_id")
    private Category parentCategory;

    @OneToMany(cascade = ALL, orphanRemoval = true, mappedBy = "parentCategory")
    private List<Category> childCategories;

    @OneToMany(cascade = ALL, orphanRemoval = true, mappedBy = "category")
    private List<Product> products;

    @Builder
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
