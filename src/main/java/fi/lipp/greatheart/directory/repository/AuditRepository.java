package fi.lipp.greatheart.directory.repository;

import fi.lipp.greatheart.directory.domain.AuditEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditRepository extends JpaRepository<AuditEntity, Long> {
}
