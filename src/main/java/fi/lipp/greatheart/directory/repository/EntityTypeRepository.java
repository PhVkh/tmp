package fi.lipp.greatheart.directory.repository;

import fi.lipp.greatheart.directory.domain.EntityTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EntityTypeRepository extends JpaRepository<EntityTypeEntity, Long> {
    EntityTypeEntity findByName(String name);
    List<String> findNecessaryFieldsById(Long id);
}
