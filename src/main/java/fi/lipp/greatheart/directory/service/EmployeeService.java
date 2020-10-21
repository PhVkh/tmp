package fi.lipp.greatheart.directory.service;

import fi.lipp.greatheart.directory.service.dto.EmployeeDto;
import fi.lipp.greatheart.directory.service.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeeService {

    void save(EmployeeDto employee);
    List<EmployeeDto> findAll(Pageable pageable);
    void update(EmployeeDto employeeDto) throws EntityNotFoundException;
    void delete(Long id) throws EntityNotFoundException;
}
