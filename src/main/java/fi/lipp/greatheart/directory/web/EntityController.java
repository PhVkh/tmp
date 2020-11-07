package fi.lipp.greatheart.directory.web;

import fi.lipp.greatheart.directory.dto.EntityDto;
import fi.lipp.greatheart.directory.dto.EntityTypeDto;
import fi.lipp.greatheart.directory.service.services.EntityService;
import fi.lipp.greatheart.directory.service.services.EntityTypeService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
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

    @GetMapping(value = {"/{entityName}/{id}","/{entityName}" })
    public ResponseEntity<List<EntityDto>> findAll(Pageable pageable, @PathVariable String entityName, @PathVariable(required = false) Optional<String> id) {
        if (id.isEmpty()) {
            Long entityId = entityTypeService.findByName(entityName).getId();
            return new ResponseEntity<>(entityService.findByEntityType(entityId), HttpStatus.OK);
        } else
            return new ResponseEntity<>(
                    Collections.singletonList(
                            entityService.findById(Long.getLong(id.get()))), HttpStatus.OK);
    }


    @GetMapping(value = {"/types"})
    public ResponseEntity<List<EntityTypeDto>> findAll(Pageable pageable) {
        return new ResponseEntity<>(entityTypeService.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> addEntity(@RequestBody EntityDto dto) {
        entityService.save(dto);
        Hibernate.initialize(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
