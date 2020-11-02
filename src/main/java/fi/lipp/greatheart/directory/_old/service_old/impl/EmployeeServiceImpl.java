package fi.lipp.greatheart.directory._old.service_old.impl;

import fi.lipp.greatheart.directory._old.domain_old.EmployeeEntity;
import fi.lipp.greatheart.directory._old.repository_old.EmployeeRepository;
import fi.lipp.greatheart.directory._old.service_old.EmployeeService;
import fi.lipp.greatheart.directory._old.service_old.dto.EmployeeDto;
import fi.lipp.greatheart.directory._old.service_old.exceptions.EntityNotFoundException;
import fi.lipp.greatheart.directory._old.service_old.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {

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
