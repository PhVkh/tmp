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
        return mapper.convert(
                repository.findByName(entityTypeName));
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

}
