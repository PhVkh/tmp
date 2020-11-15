package fi.lipp.greatheart.directory.web;

import fi.lipp.greatheart.directory.domain.FieldType;
import fi.lipp.greatheart.directory.service.impl.FieldTypesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class HelpController {

    @Autowired
    FieldTypesServiceImpl fieldTypesService;

    @GetMapping(value = "/fieldTypes")
    public ResponseEntity<Response<List<FieldType>>> getFields() {
        return Response
                .EXECUTE_RAW(() -> fieldTypesService.getAllFieldTypes())
                .makeResponse();
    }
}
