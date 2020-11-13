package fi.lipp.greatheart.directory.repository;

import fi.lipp.greatheart.directory.domain.EnumEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnumRepository extends JpaRepository<EnumEntity, Long> {
    Optional<EnumEntity> findById(Long id);
    List<EnumEntity> findAllByEnumTypeId(Long id);
}
