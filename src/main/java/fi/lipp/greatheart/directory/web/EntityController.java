package fi.lipp.greatheart.directory.web;

import fi.lipp.greatheart.directory.domain.EntityEntity;
import fi.lipp.greatheart.directory.dto.EntityDto;
import fi.lipp.greatheart.directory.dto.EntityTypeDto;
import fi.lipp.greatheart.directory.dto.EnumDto;
import fi.lipp.greatheart.directory.dto.EnumTypeDto;
import fi.lipp.greatheart.directory.service.services.EntityService;
import fi.lipp.greatheart.directory.service.services.EntityTypeService;
import fi.lipp.greatheart.directory.service.services.EnumService;
import fi.lipp.greatheart.directory.service.services.EnumTypeService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("entities")
public class EntityController {
    @Autowired
    private EntityService entityService;

    @Autowired
    private EntityTypeService entityTypeService;

    @Autowired
    EnumTypeService enumTypeService;

    @Autowired
    EnumService enumService;

    @GetMapping(value = {"/mainTypes"})
    public ResponseEntity<List<EntityTypeDto>> findAll() {
        return new ResponseEntity<>(entityTypeService.findMainEntities(), HttpStatus.OK);
    }

    @PostMapping(value = "/addEntity")
    public ResponseEntity<Response<EntityEntity>> addEntity(@RequestBody EntityDto dto,
                                                            @RequestParam("entityType") String entityTypeId) {
        return Response
                .EXECUTE_RAW(() -> entityService.save(dto, Long.valueOf(entityTypeId)))
                .makeResponse();
    }

    @PostMapping(value = "/updateEntity")
    public ResponseEntity<Response<EntityEntity>> updateEntity(@RequestBody Map<String, Object> toUpdate,
                                                               @RequestParam Long entityId) {
        return Response
                .EXECUTE_RAW(() -> entityService.updateFields(entityId, toUpdate))
                .makeResponse();
    }

    @PostMapping(value = "/addTransactions")
    public ResponseEntity<Response<Boolean>> addTransactions(@RequestBody Map<String, Object[]> toUpdate,
                                                             @RequestParam Long entityId) {
        return Response
                .EXECUTE_RAW(() -> entityService.addValuesToArray(entityId, toUpdate))
                .makeResponse();
    }

    @PostMapping(value = "/addEnum")
    public ResponseEntity<String> addEnum(@RequestBody EnumDto dto,
                                          @RequestParam String enumTypeId) {
        //проверяем,что такой enum существует
        EnumTypeDto enumTypeEntity = enumTypeService.findEnumTypeById(Long.valueOf(enumTypeId));
        if (enumTypeEntity == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        enumService.save(dto, 0L);
        Hibernate.initialize(dto);
        return new ResponseEntity<>(HttpStatus.OK);

    }


    @GetMapping(value = {"/{entityTypeId}/{id}", "/{entityTypeId}"})
    public ResponseEntity<List<EntityDto>> findAll(@PathVariable String entityTypeId, @PathVariable(required = false) Optional<String> id) {
        if (id.isEmpty()) {
            EntityTypeDto entityType = entityTypeService.findById(Long.valueOf(entityTypeId));
            if (entityType == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Long entityId = entityType.getId();
            return new ResponseEntity<>(entityService.findByEntityType(entityId), HttpStatus.OK);
        } else
            return new ResponseEntity<>(
                    Collections.singletonList(
                            entityService.findById(Long.valueOf(id.get()))), HttpStatus.OK);
    }


}
