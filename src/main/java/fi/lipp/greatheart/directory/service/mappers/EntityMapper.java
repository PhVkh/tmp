package fi.lipp.greatheart.directory.service.mappers;

import fi.lipp.greatheart.directory.domain.EntityEntity;
import fi.lipp.greatheart.directory.dto.EntityDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityMapper {
    EntityDto convert(EntityEntity entity);
    EntityEntity convert(EntityDto dto);
}
