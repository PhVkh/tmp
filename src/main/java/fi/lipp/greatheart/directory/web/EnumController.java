package fi.lipp.greatheart.directory.web;

import fi.lipp.greatheart.directory.domain.EnumEntity;
import fi.lipp.greatheart.directory.domain.EnumTypeEntity;
import fi.lipp.greatheart.directory.dto.EnumDto;
import fi.lipp.greatheart.directory.dto.EnumTypeDto;
import fi.lipp.greatheart.directory.service.services.EnumService;
import fi.lipp.greatheart.directory.service.services.EnumTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("enums")
public class EnumController {

    @Autowired
    EnumTypeService enumTypeService;

    @Autowired
    EnumService enumService;

    @PostMapping(value = "/addEnum")
    public ResponseEntity<Response<EnumEntity>> addEnum(@RequestBody EnumDto dto,
                                                        @RequestParam(name = "enumTypeId") Long enumTypeId) {
        return Response
                .EXECUTE_RAW(() -> enumService.save(dto, enumTypeId))
                .makeResponse();
    }

    @PostMapping(value = "/addEnumType")
    public ResponseEntity<Response<EnumTypeEntity>> addEnumType(@RequestBody EnumTypeDto dto
    ) {
        return Response
                .EXECUTE_RAW(() -> enumTypeService.save(dto))
                .makeResponse();
    }


    @GetMapping(value = "/types")
    public ResponseEntity<Response<List<EnumTypeDto>>> getAllEnumTypes() {
        return Response.EXECUTE_RAW(() -> enumTypeService.findAllEnumTypes()).makeResponse();
    }

    @GetMapping(value = "/elements")
    public ResponseEntity<Response<List<EnumEntity>>> getAllEnumTypes(@RequestParam(name = "enumTypeId") Long typeId) {
        return Response.EXECUTE_RAW(() -> enumService.findEnumsByEnumTypeId(typeId)).makeResponse();
    }


}
