package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.domain.EntityEntity;
import fi.lipp.greatheart.directory.domain.EntityTypeEntity;
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


    //TODO : какой эксепшен кидать и как его правильно ловить
    public void save(EntityDto dto, Long entityTypeId) {
        EntityEntity entity = mapper.convert(dto);
        entity.setEntityType(entityTypeId);

        Optional<EntityTypeEntity> entityType= entityTypeRepository.findById(entityTypeId);
        if(entityType.isEmpty())
            throw new IllegalArgumentException();

        //находим какие поля должны быть у сущности данного типа
        List<String> necessaryFields = entityType.get().getNecessaryFields();

        //Проверим, что в json содержатся все необходимые ключи
        if(!entity.getJson().keySet().containsAll(necessaryFields))
            throw new IllegalArgumentException();

        //Проверим, что значения в ключах ненулевые
        for (String field : necessaryFields) {
            if (entity.getJson().get(field) == null)
                throw new IllegalArgumentException();
        }

        //вроде все проверили, теперь можно и сохранить
        entityRepository.save(entity);
    }

    @Override
    public EntityDto findById(Long id) {
        Optional<EntityEntity> entity = entityRepository.findById(id);

        return entity.map(entityEntity -> mapper.convert(entityEntity)).orElse(null);

    }
}
