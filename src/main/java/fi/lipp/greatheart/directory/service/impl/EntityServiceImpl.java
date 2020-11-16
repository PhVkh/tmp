package fi.lipp.greatheart.directory.service.impl;

import com.google.i18n.phonenumbers.PhoneNumberUtil;
import fi.lipp.greatheart.directory.domain.EntityEntity;
import fi.lipp.greatheart.directory.domain.EntityTypeEntity;
import fi.lipp.greatheart.directory.dto.EntityDto;
import fi.lipp.greatheart.directory.repository.EntityRepository;
import fi.lipp.greatheart.directory.repository.EntityTypeRepository;
import fi.lipp.greatheart.directory.repository.EnumRepository;
import fi.lipp.greatheart.directory.repository.EnumTypeRepository;
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
    EnumTypeRepository enumTypeRepository;

    @Autowired
    EnumRepository enumRepository;

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
        List<String> notNullFields = getNotNullFieldsOfEntityType(entityTypeId);

        //Проверим, что в json содержатся все необходимые ключи
        if (!entity.getJson().keySet().containsAll(notNullFields))
            return Response.BAD("Сущность, которую пытаются сохранить не содержит необходимых полей.");

        //Проверим, что значения в ключах ненулевые
        for (String field : notNullFields) {
            if (entity.getJson().get(field) == null)
                return Response.BAD(String.format("У поля %s нулевой описание", field));
        }


        //Проверям, что нам возвращаются данне нужного типа
        Map<String, Object> fields = dto.getJson();
        for (Map.Entry<String, Object> fieldEntry : fields.entrySet()) {

            //Проверяем, что fieldValue астится к тому значению, которое там должно лежать

            Response<Boolean> response = isFieldCorrect(entityTypeId, fieldEntry.getKey(), fieldEntry.getValue());
            if (!response.isSuccess())
                return Response.BAD(response.getMessage());

            System.out.println();
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

        List<String> fields = getAllFieldsOfEntityType(entity.getEntityType());

        for (Map.Entry<String, Object> stringObjectEntry : toUpdate.entrySet()) {

            Response<Boolean> response = isFieldCorrect(entity.getEntityType(), stringObjectEntry.getKey(), stringObjectEntry.getValue());

            if (!response.isSuccess())
                return Response.BAD(response.getMessage());
        }


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

    private List<String> getNotNullFieldsOfEntityType(Long entityTypeId) {
        Optional<EntityTypeEntity> entityTypeEntity = entityTypeRepository.findById(entityTypeId);
        if (entityTypeEntity.isEmpty())
            throw new IllegalArgumentException("Не существует сущности такого типа! " +
                    "Попытка получить ненулевые поля для сущности");

        Map<String, Object> entityFields = entityTypeEntity.get().getNecessaryFields();


        List<String> notNullFields = new ArrayList<>();

        for (Map.Entry<String, Object> stringObjectEntry : entityFields.entrySet()) {
            Map temp = (Map) stringObjectEntry.getValue();
            if ((Boolean) temp.get("notnull"))
                notNullFields.add(stringObjectEntry.getKey());
        }

        return notNullFields;
    }

    private List<String> getAllFieldsOfEntityType(Long entityTypeId) {
        Optional<EntityTypeEntity> entityTypeEntity = entityTypeRepository.findById(entityTypeId);
        if (entityTypeEntity.isEmpty())
            throw new IllegalArgumentException("Не существует сущности такого типа! " +
                    "Попытка получить ненулевые поля для сущности");

        Map<String, Object> entityFields = entityTypeEntity.get().getNecessaryFields();


        return entityFields.keySet().stream().collect(Collectors.toList());
    }

    private Response<Boolean> isFieldCorrect(Long entityTypeId, String fieldName, Object fieldValueToCheck) {
        Optional<EntityTypeEntity> entityTypeEntityOptional = entityTypeRepository.findById(entityTypeId);
        if (entityTypeEntityOptional.isEmpty())
            throw new IllegalArgumentException("Не существует сущности такого типа! " +
                    "Попытка получить ненулевые поля для сущности");

        EntityTypeEntity entityTypeEntity = entityTypeEntityOptional.get();

        String fieldTypeAtEntityType = (String) (
                (Map) entityTypeEntity.getNecessaryFields().get(fieldName))
                .get("type");


        List<String> notNullFields = getNotNullFieldsOfEntityType(entityTypeId);

        switch (fieldTypeAtEntityType) {
            case "string": {
                String fieldValue = (String) fieldValueToCheck;
                if (notNullFields.contains(fieldName) && fieldValue.isBlank())
                    return Response.BAD(String.format("Значение поля %s не должно быть пустым.", fieldName));

                break;
            }
            case "number": {
                String fieldValue = (String) fieldValueToCheck;

                if (notNullFields.contains(fieldName) && fieldValue.isBlank())
                    return Response.BAD(String.format("Значение поля %s не должно быть пустым.", fieldName));


                if (!NumberUtils.isNumber(fieldValue)) {
                    return Response.BAD(String.format("Значение поля %s должно являться числом", fieldName));
                }
                break;
            }
            case "date": {
                String fieldValue = (String) fieldValueToCheck;

                if (notNullFields.contains(fieldName) && fieldValue.isBlank())
                    return Response.BAD(String.format("Значение поля %s не должно быть пустым.", fieldName));


                boolean isDate = false;
                for (Locale availableLocale : Locale.getAvailableLocales()) {
                    if (GenericValidator.isDate(fieldValue, availableLocale))
                        isDate = true;
                }
                if (!isDate)
                    return Response.BAD(String.format("Значение поля %s должно являться датой.", fieldName));
                break;
            }
            case "boolean": {
                String fieldValue = (String) fieldValueToCheck;

                if (notNullFields.contains(fieldName) && fieldValue.isBlank())
                    return Response.BAD(String.format("Значение поля %s не должно быть пустым.", fieldName));

                if (!Boolean.parseBoolean(fieldValue))
                    return Response.BAD(String.format("Значение поля %s должно являться True False.", fieldName));

                break;
            }
            case "enum": {
                //проверим, что есть такой тип enum-ов
                Map<String, Object> complexField = (Map<String, Object>) fieldValueToCheck;
                if (!complexField.keySet().containsAll(Arrays.asList("enum_type", "id")))
                    return Response.BAD(String.format("Перечисление %s не содержит обязательного поля." +
                            "Обязательные поля  \"enum_type\"  \"id\" ", fieldName));

                if (!enumTypeRepository.existsById(Long.valueOf(String.valueOf(complexField.get("enum_type")))))
                    return Response.BAD(String.format("Перечисления с типом %s не зарегесрировано.", (complexField.get("enum_type"))));

                if (!enumRepository.existsById(Long.valueOf(String.valueOf(complexField.get("id")))))
                    return Response.BAD(String.format("Перечисления с id %s не зарегесрировано.", (complexField.get("id"))));

                break;
            }
            case "phone": {
                PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

                if (notNullFields.contains(fieldName) && ((String) fieldValueToCheck).isBlank())
                    return Response.BAD(String.format("Значение поля %s не должно быть пустым.", fieldName));


                if (!phoneNumberUtil.isPossibleNumber(((String) fieldValueToCheck), "RU"))
                    return Response.BAD(String.format("Значение поля %s не является номером телефона РФ", fieldName));
                break;
            }
            case "email": {

                if (notNullFields.contains(fieldName) && ((String) fieldValueToCheck).isBlank())
                    return Response.BAD(String.format("Значение поля %s не должно быть пустым.", fieldName));


                if (!GenericValidator.isEmail((String) fieldValueToCheck))
                    return Response.BAD(String.format("Значение поля %s не является email-ом.", fieldName));
                break;
            }
            case "entity": {

                Map<String, Object> complexField = (Map<String, Object>) fieldValueToCheck;
                if (!complexField.keySet().containsAll(Arrays.asList("entity_type", "id")))
                    return Response.BAD(String.format("Вложенная сущность %s не содержит обязательного поля." +
                            "Обязательные поля  \"enum_type\"  \"id\" ", fieldName));

                if (!entityTypeRepository.existsById(Long.valueOf(String.valueOf(complexField.get("entity_type")))))
                    return Response.BAD(String.format("Типа вложенной сущности %s не зарегесрировано.", (complexField.get("enum_type"))));

                if (!entityRepository.existsById(Long.valueOf(String.valueOf(complexField.get("id")))))
                    return Response.BAD(String.format(" вложенной сущности с id = %s не зарегесрировано.", (complexField.get("enum_type"))));
                break;
            }
        }
        return Response.OK(Boolean.TRUE);
    }


}
