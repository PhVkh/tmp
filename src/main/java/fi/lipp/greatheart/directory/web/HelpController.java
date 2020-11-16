package fi.lipp.greatheart.directory.web;

import fi.lipp.greatheart.directory.domain.EntityEntity;
import fi.lipp.greatheart.directory.domain.FieldType;
import fi.lipp.greatheart.directory.dto.StorageAccess;
import fi.lipp.greatheart.directory.service.impl.FieldTypesServiceImpl;
import fi.lipp.greatheart.directory.service.services.EntityService;
import fi.lipp.greatheart.directory.service.services.StorageAccessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class HelpController {

    @Autowired
    FieldTypesServiceImpl fieldTypesService;

    @Autowired
    StorageAccessService storageAccessService;

    @Autowired
    EntityService entityService;

    @GetMapping(value = "/fieldTypes")
    public ResponseEntity<Response<List<FieldType>>> getFields() {
        return Response
                .EXECUTE_RAW(() -> fieldTypesService.getAllFieldTypes())
                .makeResponse();
    }


    @GetMapping(value = "/getRights")
    public ResponseEntity<Response<List<Integer>>> getFields(@RequestParam(name = "login") String login) {
        return Response
                .EXECUTE_RAW(() -> storageAccessService.getAccessEntitiesByLogin(login))
                .makeResponse();
    }


    @PostMapping(value = "/addRights")
    public ResponseEntity<Response<StorageAccess>> addFields(
            @RequestBody StorageAccess param) {
        return Response
                .EXECUTE_RAW(() -> storageAccessService.addAccessEntitiesToLogin(param.getLogin(), param.getEntityListAll()))
                .makeResponse();
    }

    @PostMapping(value = "/removeRights")
    public ResponseEntity<Response<StorageAccess>> removeFields(
            @RequestBody StorageAccess param) {
        return Response
                .EXECUTE_RAW(() -> storageAccessService.removeAccessEntitiesToLogin(param.getLogin(), param.getEntityListAll()))
                .makeResponse();
    }

    @GetMapping("/profile")
    public ResponseEntity<Response<EntityEntity>> getProfile(@RequestParam(name = "login") String login) {
        return entityService.findByLogin(login).makeResponse();
    }


}
