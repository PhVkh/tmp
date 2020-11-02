package fi.lipp.greatheart.directory.service.mappers;

import fi.lipp.greatheart.directory.domain.EmailEntity;
import fi.lipp.greatheart.directory.domain.PhoneEntity;
import fi.lipp.greatheart.directory.domain.UserEntity;
import fi.lipp.greatheart.directory.dto.EmailDto;
import fi.lipp.greatheart.directory.dto.PhoneDto;
import fi.lipp.greatheart.directory.dto.UserDto;
import jdk.jfr.Name;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto convert(UserEntity entity, @Context CycleAvoidingMappingContext context);

    UserEntity convert(UserDto dto, @Context CycleAvoidingMappingContext context);

    PhoneDto convert(PhoneEntity entity, @Context CycleAvoidingMappingContext context);

    PhoneEntity convert(PhoneDto dto, @Context CycleAvoidingMappingContext context);

    EmailDto convert(EmailEntity entity, @Context CycleAvoidingMappingContext context);

    EmailEntity convert(EmailDto dto, @Context CycleAvoidingMappingContext context);

}
