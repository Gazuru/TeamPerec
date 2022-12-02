package hu.bme.hit.teamperec.data.repository;

import java.util.UUID;

import hu.bme.hit.teamperec.data.entity.Download;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DownloadRepository extends JpaRepository<Download, UUID> {
}
