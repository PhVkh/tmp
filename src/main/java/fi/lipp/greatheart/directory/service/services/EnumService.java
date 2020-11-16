package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.domain.EnumEntity;
import fi.lipp.greatheart.directory.dto.EnumDto;
import fi.lipp.greatheart.directory.web.Response;

import java.util.List;

public interface EnumService {
    Response<List<EnumEntity>> findEnumsByEnumTypeId(Long enumTypeId);

    EnumDto findEnumById(Long enumId);

    Response<EnumEntity> save(EnumDto dto, Long id);
}
