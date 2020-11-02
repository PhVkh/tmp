package fi.lipp.greatheart.directory._old.service_old;

import fi.lipp.greatheart.directory._old.service_old.dto.EmployeeDto;
import fi.lipp.greatheart.directory._old.service_old.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    void save(EmployeeDto employee);
    List<EmployeeDto> findAll(Pageable pageable);
    void update(EmployeeDto employeeDto) throws EntityNotFoundException;
    void delete(Long id) throws EntityNotFoundException;
}
