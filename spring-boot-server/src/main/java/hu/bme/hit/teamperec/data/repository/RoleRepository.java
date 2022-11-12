package hu.bme.hit.teamperec.data.repository;

import java.util.Optional;

import hu.bme.hit.teamperec.data.entity.Role;
import hu.bme.hit.teamperec.data.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
