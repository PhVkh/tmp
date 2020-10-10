package fi.lipp.greatheart.directory.service;

import fi.lipp.greatheart.directory.domain.EmployeeEntity;
import fi.lipp.greatheart.directory.repository.EmployeeRepository;
import fi.lipp.greatheart.directory.service.dto.EmployeeDto;
import fi.lipp.greatheart.directory.service.exceptions.EntityNotFoundException;
import fi.lipp.greatheart.directory.service.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class EmployeeService {

    @Autowired private EmployeeRepository repository;
    @Autowired private EmployeeMapper mapper;

    public void save(EmployeeDto employee) {
        repository.save(mapper.convert(employee));
    }

    public List<EmployeeDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).stream().map(mapper::convert).collect(Collectors.toList());
    }

    public void update(EmployeeDto employeeDto) throws EntityNotFoundException {
        if (repository.findById(employeeDto.getId()).isPresent()) {
            repository.save(mapper.convert(employeeDto));
        } else {
            throw new EntityNotFoundException();
        }
    }

    public void delete(Long id) throws EntityNotFoundException {
        Optional<EmployeeEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            entity.ifPresent(repository::delete);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
