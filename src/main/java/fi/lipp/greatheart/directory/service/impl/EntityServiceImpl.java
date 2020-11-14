package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.domain.EntityEntity;
import fi.lipp.greatheart.directory.domain.EntityTypeEntity;
import fi.lipp.greatheart.directory.dto.EntityDto;
import fi.lipp.greatheart.directory.repository.EntityRepository;
import fi.lipp.greatheart.directory.repository.EntityTypeRepository;
import fi.lipp.greatheart.directory.service.mappers.EntityMapper;
import fi.lipp.greatheart.directory.service.services.EntityService;
import fi.lipp.greatheart.directory.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
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

    public Response<EntityEntity> saveEntity(EntityDto dto, Long entityTypeId) {
        EntityEntity entity = mapper.convert(dto);
        entity.setEntityType(entityTypeId);

        Optional<EntityTypeEntity> entityType = entityTypeRepository.findById(entityTypeId);
        if (entityType.isEmpty())
            return Response.BAD("Типа сущности с id " + entityTypeId + "не существует в базе.");

        //находим какие поля должны быть у сущности данного типа
        List<String> necessaryFields = entityType.get().getNecessaryFields();

        //Проверим, что в json содержатся все необходимые ключи
        if (!entity.getJson().keySet().containsAll(necessaryFields))
            throw new IllegalArgumentException();

        //Проверим, что значения в ключах ненулевые
        for (String field : necessaryFields) {
            if (entity.getJson().get(field) == null)
                return Response.BAD("ошибка");
        }

        //вроде все проверили, теперь можно и сохранить
        return Response.EXECUTE(() -> entityRepository.save(entity));
    }

    @Override
    public Response<EntityEntity> updateFields(Long entityId, Map<String, Object> toUpdate) {
        Optional<EntityEntity> entityOptional = entityRepository.findById(entityId);
        if (entityOptional.isEmpty()) {
            return Response.BAD("Не найдена запись в справочнике (id : %d)", entityId);
        }
        EntityEntity entity = entityOptional.get();
        toUpdate.forEach((key, value) -> entity.getJson().put(key, value));
        return Response.EXECUTE(() -> entityRepository.save(entity));
    }

    @Override
    public Response<Boolean> addValuesToArray(Long entityId, Map<String, Object[]> valuesToAdd) {
        Optional<EntityEntity> entityOptional = entityRepository.findById(entityId);
        if (entityOptional.isEmpty()) {
            return Response.BAD("Не найдена запись в справочнике (id : %d)", entityId);
        }
        EntityEntity entity = entityOptional.get();
        valuesToAdd.forEach((key, values) -> {
            if (entity.getJson().containsKey(key)) {
                ((ArrayList) entity.getJson().get(key)).addAll(Arrays.asList(values));
            } else {
                entity.getJson().put(key, values);
            }
        });
        return Response.EXECUTE(() -> {
            entityRepository.save(entity);
            return true;
        });
    }

    @Override
    public EntityDto findById(Long id) {
        Optional<EntityEntity> entity = entityRepository.findById(id);

        return entity.map(entityEntity -> mapper.convert(entityEntity)).orElse(null);

    }
}
