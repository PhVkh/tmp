package fi.lipp.greatheart.directory.web;

import fi.lipp.greatheart.directory.dto.EntityDto;
import fi.lipp.greatheart.directory.dto.EntityTypeDto;
import fi.lipp.greatheart.directory.dto.EnumDto;
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
    public ResponseEntity<String> addEntity(@RequestBody EntityDto dto) {
        entityService.save(dto);
        Hibernate.initialize(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping(value = "/addEnum")
    public ResponseEntity<String> addEnum(@RequestBody EnumDto dto) {
        enumService.save(dto);
        Hibernate.initialize(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping(value = {"/{entityName}/{id}", "/{entityName}"})
    public ResponseEntity<List<EntityDto>> findAll(@PathVariable String entityName, @PathVariable(required = false) Optional<String> id) {
        if (id.isEmpty()) {
            EntityTypeDto entityType =  entityTypeService.findByName(entityName);
            if(entityType  == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Long entityId = entityType.getId();
            return new ResponseEntity<>(entityService.findByEntityType(entityId), HttpStatus.OK);
        } else
            return new ResponseEntity<>(
                    Collections.singletonList(
                            entityService.findById(Long.valueOf(id.get()))), HttpStatus.OK);
    }



}
