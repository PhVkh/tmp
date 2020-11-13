package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.domain.EntityTypeEntity;
import fi.lipp.greatheart.directory.dto.EntityTypeDto;
import fi.lipp.greatheart.directory.repository.EntityTypeRepository;
import fi.lipp.greatheart.directory.service.mappers.EntityTypeMapper;
import fi.lipp.greatheart.directory.service.services.EntityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntityTypeServiceImpl implements EntityTypeService {
    @Autowired
    EntityTypeRepository repository;

    @Autowired
    EntityTypeMapper mapper;

    @Override
    public List<EntityTypeDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .stream().map(x -> mapper.convert(x))
                .collect(Collectors.toList());
    }

    @Override
    public EntityTypeDto findByName(String entityTypeName) {
        Optional<EntityTypeEntity> entityType = repository.findByName(entityTypeName);
        return entityType.map(x -> mapper.convert(x)).orElse(null);
    }

    @Override
    public List<EntityTypeDto> findMainEntities() {
        return repository.findByMainTrue()
                .stream().map(x -> mapper.convert(x))
                .collect(Collectors.toList());
    }

    @Override
    public void save(EntityTypeDto dto) {
        EntityTypeEntity entity = mapper.convert(dto);
        repository.save(entity);
    }

    @Override
    public EntityTypeDto findById(Long entityTypeId) {
        Optional<EntityTypeEntity> entityType = repository.findById(entityTypeId);
        return entityType.map(x -> mapper.convert(x)).orElse(null);
    }

}
