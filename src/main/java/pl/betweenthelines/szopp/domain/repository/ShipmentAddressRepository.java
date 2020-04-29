package pl.betweenthelines.szopp.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.betweenthelines.szopp.domain.ShipmentAddress;

@Repository
public interface ShipmentAddressRepository extends JpaRepository<ShipmentAddress, Long> {

}