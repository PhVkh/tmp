package fi.lipp.greatheart.directory.service.mappers;

import fi.lipp.greatheart.directory.domain.EmailEntity;
import fi.lipp.greatheart.directory.domain.OtherContactEntity;
import fi.lipp.greatheart.directory.domain.PhoneEntity;
import fi.lipp.greatheart.directory.domain.UserEntity;
import fi.lipp.greatheart.directory.dto.EmailDto;
import fi.lipp.greatheart.directory.dto.OtherContactDto;
import fi.lipp.greatheart.directory.dto.PhoneDto;
import fi.lipp.greatheart.directory.dto.UserDto;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDto convert(UserEntity entity, @Context CycleAvoidingMappingContext context);

    UserEntity convert(UserDto dto, @Context CycleAvoidingMappingContext context);

    PhoneDto convert(PhoneEntity entity, @Context CycleAvoidingMappingContext context);

    PhoneEntity convert(PhoneDto dto, @Context CycleAvoidingMappingContext context);

    EmailDto convert(EmailEntity entity, @Context CycleAvoidingMappingContext context);

    EmailEntity convert(EmailDto dto, @Context CycleAvoidingMappingContext context);

    OtherContactDto convert(OtherContactEntity entity, @Context CycleAvoidingMappingContext context);

    OtherContactEntity convert(OtherContactDto dto, @Context CycleAvoidingMappingContext context);


}
