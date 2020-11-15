package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.domain.FieldType;
import fi.lipp.greatheart.directory.repository.FieldTypeRepository;
import fi.lipp.greatheart.directory.service.services.FieldTypesService;
import fi.lipp.greatheart.directory.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FieldTypesServiceImpl implements FieldTypesService {

    @Autowired
    FieldTypeRepository fieldTypeRepository;

    @Override
    public Response<List<FieldType>> getAllFieldTypes() {
        return Response.EXECUTE(() -> fieldTypeRepository.findAll());
    }
}
