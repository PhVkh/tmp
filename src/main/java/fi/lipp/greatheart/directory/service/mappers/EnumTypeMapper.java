package fi.lipp.greatheart.directory.service.mappers;

import fi.lipp.greatheart.directory.domain.EnumTypeEntity;
import fi.lipp.greatheart.directory.dto.EnumTypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnumTypeMapper {
    EnumTypeEntity convert(EnumTypeDto dto);
    EnumTypeDto convert(EnumTypeEntity entity);
}
