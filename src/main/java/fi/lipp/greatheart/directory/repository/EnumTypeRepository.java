package fi.lipp.greatheart.directory.repository;

import fi.lipp.greatheart.directory.domain.EnumTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EnumTypeRepository extends JpaRepository<EnumTypeEntity, Long> {
    Optional<EnumTypeEntity> findById(Long id);
}
