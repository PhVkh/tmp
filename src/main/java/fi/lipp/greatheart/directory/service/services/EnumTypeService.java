package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.dto.EnumTypeDto;
import fi.lipp.greatheart.directory.web.Response;

public interface EnumTypeService {

    Response findAllEnumTypes();

    EnumTypeDto findEnumTypeById(Long id);
}
