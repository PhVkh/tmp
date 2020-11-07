package fi.lipp.greatheart.directory.service.mappers;

import fi.lipp.greatheart.directory.domain.EntityTypeEntity;
import fi.lipp.greatheart.directory.dto.EntityTypeDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityTypeMapper {
    EntityTypeDto convert(EntityTypeEntity entity);

    EntityTypeEntity convert(EntityTypeDto dto);
}
