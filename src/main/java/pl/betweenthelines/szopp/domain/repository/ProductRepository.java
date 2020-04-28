package pl.betweenthelines.szopp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.betweenthelines.szopp.domain.Category;
import pl.betweenthelines.szopp.domain.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

//    @Override
//    @Lock(PESSIMISTIC_WRITE)
//    Optional<Product> findById(@NonNull Long aLong); //TODO: Lock on product (quantity must be correct)

    List<Product> findAllByCategory(Category category);

    List<Product> findAllByNameContainingIgnoreCase(String name);

}