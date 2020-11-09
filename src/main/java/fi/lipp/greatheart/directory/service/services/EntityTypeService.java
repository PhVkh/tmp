package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.dto.EntityTypeDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EntityTypeService {
    List<EntityTypeDto> findAll(Pageable pageable);

    EntityTypeDto findByName(String entityTypeName);

    List<EntityTypeDto> findMainEntities();

    void save(EntityTypeDto dto);
}
