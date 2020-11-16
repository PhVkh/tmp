package fi.lipp.greatheart.directory.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import fi.lipp.greatheart.directory.domain.EntityEntity;
import fi.lipp.greatheart.directory.domain.EntityTypeEntity;
import fi.lipp.greatheart.directory.dto.AuditDto;
import fi.lipp.greatheart.directory.dto.EntityDto;
import fi.lipp.greatheart.directory.dto.EntityTypeDto;
import fi.lipp.greatheart.directory.security.CustomUserDetails;
import fi.lipp.greatheart.directory.service.services.AuditService;
import fi.lipp.greatheart.directory.service.services.EntityService;
import fi.lipp.greatheart.directory.service.services.EntityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
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
    private AuditService auditService;

    private AuditDto buildAuditDto(HttpServletRequest request, Optional<CustomUserDetails> userDetails, Object requestBody) {
        ObjectMapper mapper = new ObjectMapper();
        AuditDto auditDto = new AuditDto();
        auditDto.setUser(userDetails.isPresent() ? userDetails.get().getUsername() : "no user");
        auditDto.setTime(LocalDateTime.now());
        try {
            auditDto.setRequestBody(mapper.writeValueAsString(requestBody));
        } catch (Exception e) {
            auditDto.setRequestBody(""); //допустим, передали null в случае гет запроса
        }
        auditDto.setRequestType(request.getMethod());
        auditDto.setPath(request.getRequestURI());
        return auditDto;
    }

    @GetMapping(value = {"/mainTypes"})
    public ResponseEntity<List<EntityTypeDto>> findAll(@AuthenticationPrincipal Optional<CustomUserDetails> user,
                                                       HttpServletRequest request) {
        AuditDto audit = buildAuditDto(request, user, null);
        auditService.save(audit);
        return new ResponseEntity<>(entityTypeService.findMainEntities(), HttpStatus.OK);
    }

    @GetMapping("/mainType")
    public ResponseEntity<Response<EntityTypeDto>> getEntityType(
            @RequestParam Long entityTypeId,
            @AuthenticationPrincipal Optional<CustomUserDetails> user,
            HttpServletRequest request) {
        AuditDto audit = buildAuditDto(request, user, null);
        auditService.save(audit);
        return Response.EXECUTE(() -> entityTypeService.findById(entityTypeId)).makeResponse();
    }

    @PostMapping(value = "/addEntity")
    public ResponseEntity<Response<EntityEntity>> addEntity(@RequestBody EntityDto dto,
                                                            @RequestParam("entityType") String entityTypeId,
                                                            @AuthenticationPrincipal Optional<CustomUserDetails> user,
                                                            HttpServletRequest request) {
        AuditDto audit = buildAuditDto(request, user, dto);
        auditService.save(audit);
        return Response
                .EXECUTE_RAW(() -> entityService.saveEntity(dto, Long.valueOf(entityTypeId)))
                .makeResponse();
    }

    @PostMapping(value = "/addEntityType")
    public ResponseEntity<Response<EntityTypeEntity>> addEntityType(@RequestBody EntityTypeDto dto) {
        return Response.EXECUTE_RAW(() -> entityTypeService.save(dto)).makeResponse();
    }

    @PostMapping(value = "/updateEntity")
    public ResponseEntity<Response<EntityEntity>> updateEntity(@RequestBody Map<String, Object> toUpdate,
                                                               @RequestParam Long entityId,
                                                               @AuthenticationPrincipal Optional<CustomUserDetails> user,
                                                               HttpServletRequest request) {
        AuditDto audit = buildAuditDto(request, user, toUpdate);
        auditService.save(audit);
        return Response
                .EXECUTE_RAW(() -> entityService.updateFields(entityId, toUpdate))
                .makeResponse();
    }

    @PostMapping(value = "/addTransactions")
    public ResponseEntity<Response<Boolean>> addTransactions(@RequestBody Map<String, Object[]> toUpdate,
                                                             @RequestParam Long entityId,
                                                             @AuthenticationPrincipal Optional<CustomUserDetails> user,
                                                             HttpServletRequest request) {
        AuditDto audit = buildAuditDto(request, user, toUpdate);
        auditService.save(audit);
        return Response
                .EXECUTE_RAW(() -> entityService.addValuesToArray(entityId, toUpdate))
                .makeResponse();
    }


    @GetMapping(value = {"/{entityTypeId}/{id}", "/{entityTypeId}"})
    public ResponseEntity<List<EntityDto>> findAll(@PathVariable String entityTypeId,
                                                   @PathVariable(required = false) Optional<Long> id,
                                                   @AuthenticationPrincipal Optional<CustomUserDetails> user,
                                                   HttpServletRequest request) {
        AuditDto audit = buildAuditDto(request, user, null);
        auditService.save(audit);
        if (id.isEmpty()) {
            EntityTypeDto entityType = entityTypeService.findById(Long.valueOf(entityTypeId));
            if (entityType == null)
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            Long entityId = entityType.getId();
            return new ResponseEntity<>(entityService.findByEntityType(entityId), HttpStatus.OK);
        } else
            return new ResponseEntity<>(
                    Collections.singletonList(
                            entityService.findById(id.get())), HttpStatus.OK);
    }

    @DeleteMapping("/deleteEntity")
    public ResponseEntity<Response<Boolean>> deleteEntity(@RequestParam Long id) {
        return entityService.deleteEntity(id).makeResponse();
    }

    @DeleteMapping("/deleteEntityType")
    public ResponseEntity<Response<Boolean>> deleteEntityType(@RequestParam Long id) {
        return entityService.deleteEntityType(id).makeResponse();
    }


}
