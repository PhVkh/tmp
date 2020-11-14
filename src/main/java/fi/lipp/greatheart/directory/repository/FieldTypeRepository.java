package fi.lipp.greatheart.directory.repository;

import fi.lipp.greatheart.directory.domain.FieldType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FieldTypeRepository extends JpaRepository<FieldType, String> {
    Boolean existsByType(String type);
}
