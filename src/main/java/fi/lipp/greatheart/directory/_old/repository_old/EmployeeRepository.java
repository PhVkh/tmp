package fi.lipp.greatheart.directory._old.repository_old;

import fi.lipp.greatheart.directory._old.domain_old.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Page<EmployeeEntity> findAll(Pageable pageable);
}
