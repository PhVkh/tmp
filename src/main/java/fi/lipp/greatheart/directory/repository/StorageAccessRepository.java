package fi.lipp.greatheart.directory.repository;

import fi.lipp.greatheart.directory.dto.StorageAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StorageAccessRepository extends JpaRepository<StorageAccess, String> {

    Optional<StorageAccess> findByLogin(String login);
}
