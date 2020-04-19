package pl.betweenthelines.szopp.security.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.betweenthelines.szopp.security.domain.Role;
import pl.betweenthelines.szopp.security.domain.RoleName;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(RoleName name);

}
