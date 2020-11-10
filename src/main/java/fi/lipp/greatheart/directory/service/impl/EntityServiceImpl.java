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

import java.util.ArrayList;
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


    public Response<EntityEntity> save(EntityDto dto, Long entityTypeId) {
        EntityEntity entity = mapper.convert(dto);
        entity.setEntityType(entityTypeId);

        Optional<EntityTypeEntity> entityType = entityTypeRepository.findById(entityTypeId);
        if (entityType.isEmpty())
            return Response.BAD("Не существует сущности с id = " + entityTypeId);

        //находим какие поля должны быть у сущности данного типа
        List<String> necessaryFields = entityType.get().getNecessaryFields();

        //Проверим, что в json содержатся все необходимые ключи
        if (!entity.getJson().keySet().containsAll(necessaryFields)) {
            List<String> notFound = new ArrayList<>();
            //Найдем поля, которых нет в сущности, но которые должны быть
            for (String field : necessaryFields) {
                if (!entity.getJson().containsKey(field))
                    notFound.add(field);
            }
            return Response.BAD("В данной сущности не хватает следующих необходимых полей : "
                    + notFound.toString());
        }
        List<String> nullFields = new ArrayList<>();
        //Проверим, что значения в ключах ненулевые
        for (String field : necessaryFields) {
            if (entity.getJson().get(field) == null)
                nullFields.add(field);
        }
        if (nullFields.size() > 0)
            return Response.BAD("В данной сущности следующие необходимые поля не заполнены : "
                    + nullFields.toString());

        //вроде все проверили, теперь можно и сохранить
        return Response.EXECUTE(() -> entityRepository.save(entity));
    }

    @Override
    public EntityDto findById(Long id) {
        Optional<EntityEntity> entity = entityRepository.findById(id);

        return entity.map(entityEntity -> mapper.convert(entityEntity)).orElse(null);

    }
}
