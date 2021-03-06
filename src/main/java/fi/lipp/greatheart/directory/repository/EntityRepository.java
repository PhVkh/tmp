package fi.lipp.greatheart.directory.repository;

import fi.lipp.greatheart.directory.domain.EntityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntityRepository extends JpaRepository<EntityEntity, Long> {
    List<EntityEntity> findByEntityType(Long entityTypeId);
    Optional<EntityEntity> findById(Long id);
    void deleteAllByEntityType(Long entityTypeId);
}
