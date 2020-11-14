package fi.lipp.greatheart.directory.service.mappers;

import fi.lipp.greatheart.directory.domain.AuditEntity;
import fi.lipp.greatheart.directory.dto.AuditDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AuditMapper {

    AuditEntity convert(AuditDto dto);
    AuditDto convert(AuditEntity entity);
}
