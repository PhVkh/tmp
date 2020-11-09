package fi.lipp.greatheart.directory.repository;

import fi.lipp.greatheart.directory.domain.EntityTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EntityTypeRepository extends JpaRepository<EntityTypeEntity, Long> {
    Optional<EntityTypeEntity> findByName(String name);

    Optional<EntityTypeEntity> findById(Long id);

    List<EntityTypeEntity> findByMainTrue();
}

