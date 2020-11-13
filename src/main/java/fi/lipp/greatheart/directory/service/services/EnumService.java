package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.dto.EnumDto;

import java.util.List;

public interface EnumService {
    List<EnumDto> findEnumsByEnumTypeId(Long enumTypeId);

    EnumDto findEnumById(Long enumId);

    void save(EnumDto dto, Long id);
}
