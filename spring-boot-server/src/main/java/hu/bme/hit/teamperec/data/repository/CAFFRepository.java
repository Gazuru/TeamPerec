package hu.bme.hit.teamperec.data.repository;

import java.util.List;
import java.util.UUID;

import hu.bme.hit.teamperec.data.entity.CAFF;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CAFFRepository extends JpaRepository<CAFF, UUID> {
    List<CAFF> findAllByUploaderIdOrderByUploadedAtDesc(UUID uploaderId);
}
