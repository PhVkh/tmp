package fi.lipp.greatheart.directory._old.service_old.mapper;

import fi.lipp.greatheart.directory._old.domain_old.EmployeeEntity;
import fi.lipp.greatheart.directory._old.service_old.dto.EmployeeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    EmployeeDto convert(EmployeeEntity entity);
    EmployeeEntity convert(EmployeeDto dto);
}
