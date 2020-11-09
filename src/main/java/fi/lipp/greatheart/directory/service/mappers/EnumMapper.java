package fi.lipp.greatheart.directory.service.mappers;

import fi.lipp.greatheart.directory.domain.EnumEntity;
import fi.lipp.greatheart.directory.dto.EnumDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EnumMapper {
    EnumDto convert(EnumEntity enumEntity);
    EnumEntity convert(EnumDto enumDto);
}
