package pl.betweenthelines.szopp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.betweenthelines.szopp.domain.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
