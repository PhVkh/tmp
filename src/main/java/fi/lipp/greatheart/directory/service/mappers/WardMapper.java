package fi.lipp.greatheart.directory.service.mappers;

import fi.lipp.greatheart.directory.domain.WardEntity;
import fi.lipp.greatheart.directory.dto.WardDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface WardMapper {
    WardDto convert(WardEntity entity);
    WardEntity convert(WardDto dto);
}
