package fi.lipp.greatheart.directory.repository;

import fi.lipp.greatheart.directory.domain.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {

    Page<EmployeeEntity> findAll(Pageable pageable);
}
