package hu.bme.hit.teamperec.data.repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import hu.bme.hit.teamperec.data.entity.CAFF;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CAFFRepository extends JpaRepository<CAFF, UUID> {
    List<CAFF> findAllByUploaderIdOrderByUploadedAtDesc(UUID uploaderId);

    Set<CAFF> findAllByUploaderUsernameOrderByUploadedAtDesc(String username);

    Set<CAFF> findAllByNameOrderByUploadedAtDesc(String name);
}
