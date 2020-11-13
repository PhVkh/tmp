package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.domain.EnumEntity;
import fi.lipp.greatheart.directory.dto.EnumDto;
import fi.lipp.greatheart.directory.web.Response;

public interface EnumService {
    Response findEnumsByEnumTypeId(Long enumTypeId);

    EnumDto findEnumById(Long enumId);

    Response<EnumEntity> save(EnumDto dto, Long id);
}
