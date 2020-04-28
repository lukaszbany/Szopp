package pl.betweenthelines.szopp.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.GenerationType.IDENTITY;

@ToString
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
    private List<Category> childCategories = new ArrayList<>();

    @OneToMany(cascade = ALL, orphanRemoval = true, mappedBy = "category")
    private List<Product> products = new ArrayList<>();

    @Builder
    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
