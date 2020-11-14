package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.domain.EntityEntity;
import fi.lipp.greatheart.directory.domain.EntityTypeEntity;
import fi.lipp.greatheart.directory.dto.EntityDto;
import fi.lipp.greatheart.directory.repository.EntityRepository;
import fi.lipp.greatheart.directory.repository.EntityTypeRepository;
import fi.lipp.greatheart.directory.service.mappers.EntityMapper;
import fi.lipp.greatheart.directory.service.services.EntityService;
import fi.lipp.greatheart.directory.web.Response;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.validator.GenericValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        //находим какие поля должны быть у сущности данного типа (notnull)
        EntityTypeEntity entityTypeEntity = entityType.get();
        Map<String, Object> entityFields = entityTypeEntity.getNecessaryFields();

        List<String> necessaryFields = new ArrayList<>();

        for (Map.Entry<String, Object> stringObjectEntry : entityFields.entrySet()) {
            Map temp = (Map) stringObjectEntry.getValue();
            if ((Boolean) temp.get("notnull"))
                necessaryFields.add(stringObjectEntry.getKey());
        }


        //Проверим, что в json содержатся все необходимые ключи
        if (!entity.getJson().keySet().containsAll(necessaryFields))
            return Response.BAD("Сущность, которую пытаются сохранить не содержит необходимых полей.");


        //Проверим, что значения в ключах ненулевые
        for (String field : necessaryFields) {
            if (entity.getJson().get(field) == null)
                return Response.BAD(String.format("У поля %s нулевой описание", field));
        }


        //Проверям, что нам возвращаются данне нужного типа
        Map<String, Object> fields = dto.getJson();
        for (Map.Entry<String, Object> fieldEntry : fields.entrySet()) {
            String field = fieldEntry.getKey();

            //Проверяем, что fieldValue астится к тому значению, которое там должно лежать

            String fieldTypeAtEntityType = (String) (
                    (Map) entityType.get()
                            .getNecessaryFields().get(field))
                    .get("type");

            switch (fieldTypeAtEntityType) {
                case "string": {

                    String fieldValue = (String) fieldEntry.getValue();
                    if (necessaryFields.contains(field) && fieldValue.isBlank())
                        return Response.BAD(String.format("Поле %s не должно быть пустым", field));

                    break;
                }
                case "number": {
                    String fieldValue = (String) fieldEntry.getValue();
                    if (!NumberUtils.isNumber(fieldValue)) {
                        return Response.BAD(String.format("Поле %s должно быть числом", field));
                    }
                    break;
                }
                case "date": {
                    String fieldValue = (String) fieldEntry.getValue();
                    boolean isDate = false;
                    for (Locale availableLocale : Locale.getAvailableLocales()) {
                        if (GenericValidator.isDate(fieldValue, availableLocale))
                            isDate = true;
                    }
                    if (!isDate)
                        return Response.BAD(String.format("Поле %s имеет неверный формат даты", field));
                    break;
                }
                case "boolean": {
                    String fieldValue = (String) fieldEntry.getValue();
                    if (!Boolean.parseBoolean(fieldValue))
                        return Response.BAD(String.format("Поле %s имеет неверный формат Boolean", field));

                    break;
                }
                case "enum": {
                    System.out.println("enum");
                    break;
                }
                case "phone": {
                    System.out.println("phone");
                    break;
                }
                case "email": {
                    System.out.println("email");
                    break;
                }
                case "entity": {
                    System.out.println("entity");
                    break;
                }
            }
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

    @Override
    public Response<Boolean> deleteEntity(Long entityId) {
        return Response.EXECUTE_RAW(() -> {
            if (entityRepository.findById(entityId).isEmpty()) {
                return Response.BAD(false, "Сущность с id = %d не найдена", entityId);
            }
            entityRepository.deleteById(entityId);
            return Response.OK(true);
        });
    }

    @Transactional
    @Override
    public Response<Boolean> deleteEntityType(Long entityTypeId) {
        return Response.EXECUTE_RAW(() -> {
            if (entityTypeRepository.findById(entityTypeId).isEmpty()) {
                return Response.BAD(false, "Справочник с id = %d не найден", entityTypeId);
            }
            entityRepository.deleteAllByEntityType(entityTypeId);
            entityTypeRepository.deleteById(entityTypeId);
            return Response.OK(true);
        });
    }
}
