package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.domain.EnumTypeEntity;
import fi.lipp.greatheart.directory.dto.EnumTypeDto;
import fi.lipp.greatheart.directory.web.Response;

import java.util.List;

public interface EnumTypeService {

    Response<List<EnumTypeDto>> findAllEnumTypes();

    Response<EnumTypeDto> findEnumTypeById(Long id);

    Response<EnumTypeEntity> save(EnumTypeDto dto);
}
