package pl.betweenthelines.szopp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.betweenthelines.szopp.domain.Category;
import pl.betweenthelines.szopp.domain.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findAllByCategory(Category category);

    List<Product> findAllByNameContainingIgnoreCase(String name);

}