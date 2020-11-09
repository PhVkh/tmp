package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.dto.EnumTypeDto;

import java.util.List;

public interface EnumTypeService {

    List<EnumTypeDto> findAllEnumTypes();

    EnumTypeDto findEnumTypeById(Long id);

    EnumTypeDto findEnumTypeByEngName(String engName);
}
