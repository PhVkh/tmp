package fi.lipp.greatheart.directory.web;

import fi.lipp.greatheart.directory.dto.WardDto;
import fi.lipp.greatheart.directory.service.services.WardService;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("wards")
public class WardController {
    @Autowired
    private WardService service;

    @GetMapping()
    public ResponseEntity<List<WardDto>> findAll(Pageable pageable) {
        return new ResponseEntity<>(service.findAll(pageable), HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<String> addWard(@RequestBody WardDto dto) {
        service.save(dto);
        Hibernate.initialize(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
