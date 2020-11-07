package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.dto.EntityDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EntityService {
    List<EntityDto> findAll(Pageable pageable);
    List<EntityDto> findByEntityType(Long entityTypeId);
    void save(EntityDto dto);

    EntityDto findById(Long id);
}
