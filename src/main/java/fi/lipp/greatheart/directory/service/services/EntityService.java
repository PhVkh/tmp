package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.domain.EntityEntity;
import fi.lipp.greatheart.directory.dto.EntityDto;
import fi.lipp.greatheart.directory.web.Response;
import org.springframework.data.domain.Pageable;

import javax.swing.text.html.parser.Entity;
import java.util.List;

public interface EntityService {
    List<EntityDto> findAll(Pageable pageable);
    List<EntityDto> findByEntityType(Long entityTypeId);
    Response<EntityEntity> save(EntityDto dto, Long enumTypeId);

    EntityDto findById(Long id);
}
