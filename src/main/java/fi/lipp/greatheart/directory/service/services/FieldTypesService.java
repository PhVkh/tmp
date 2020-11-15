package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.domain.FieldType;
import fi.lipp.greatheart.directory.web.Response;

import java.util.List;

public interface FieldTypesService {

    Response<List<FieldType>> getAllFieldTypes();
}
