package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.domain.EntityEntity;
import fi.lipp.greatheart.directory.dto.EntityDto;
import fi.lipp.greatheart.directory.web.Response;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface EntityService {
    List<EntityDto> findAll(Pageable pageable);
    List<EntityDto> findByEntityType(Long entityTypeId);

    Response<EntityEntity> saveEntity(EntityDto dto, Long enumTypeId);

    Response<EntityEntity> updateFields(Long entityId, Map<String, Object> toUpdate);
    Response<Boolean> addValuesToArray(Long entityId, Map<String, Object[]> values);
    EntityDto findById(Long id);
}
