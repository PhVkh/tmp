package fi.lipp.greatheart.directory.service.mapper;

import fi.lipp.greatheart.directory.domain.EmployeeEntity;
import fi.lipp.greatheart.directory.service.dto.EmployeeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {

    EmployeeDto convert(EmployeeEntity entity);
    EmployeeEntity convert(EmployeeDto dto);
}
