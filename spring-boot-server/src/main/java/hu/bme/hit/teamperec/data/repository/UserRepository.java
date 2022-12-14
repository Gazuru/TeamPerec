package hu.bme.hit.teamperec.data.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import hu.bme.hit.teamperec.data.entity.User;
import hu.bme.hit.teamperec.data.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    List<User> findByRolesName(ERole role);
}
