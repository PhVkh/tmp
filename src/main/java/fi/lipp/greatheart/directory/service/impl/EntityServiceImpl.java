package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.domain.EntityEntity;
import fi.lipp.greatheart.directory.dto.EntityDto;
import fi.lipp.greatheart.directory.repository.EntityRepository;
import fi.lipp.greatheart.directory.repository.EntityTypeRepository;
import fi.lipp.greatheart.directory.service.mappers.EntityMapper;
import fi.lipp.greatheart.directory.service.services.EntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntityServiceImpl implements EntityService {
    @Autowired
    EntityRepository entityRepository;
    @Autowired
    EntityTypeRepository entityTypeRepository;

    @Autowired
    EntityMapper mapper;

    @Override
    public List<EntityDto> findAll(Pageable pageable) {
        return entityRepository.findAll(pageable)
                .stream().map(x -> mapper.convert(x))
                .collect(Collectors.toList());
    }

    @Override
    public List<EntityDto> findByEntityType(Long entityTypeId) {
        return entityRepository.findByEntityType(entityTypeId)
                .stream().map(x -> mapper.convert(x))
                .collect(Collectors.toList());
    }

    public void save(EntityDto dto) {
        EntityEntity entity = mapper.convert(dto);
        entity.setEntityType(1L);
        List<String> fields = entityTypeRepository.findNecessaryFieldsById(1L).getNecessaryFields();
        entityRepository.save(entity);

    }

    @Override
    public EntityDto findById(Long id) {
        Optional<EntityEntity> entity = entityRepository.findById(id);

        if(entity.isPresent())
            return mapper.convert(entity.get());

        return null;
    }
}
