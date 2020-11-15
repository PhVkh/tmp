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
            String field = fieldEntry.getKey();

            //Проверяем, что fieldValue астится к тому значению, которое там должно лежать

            String fieldTypeAtEntityType = (String) (
                    (Map) entityType.get()
                            .getNecessaryFields().get(field))
                    .get("type");

            switch (fieldTypeAtEntityType) {
                case "string": {

                    String fieldValue = (String) fieldEntry.getValue();
                    if (notNullFields.contains(field) && fieldValue.isBlank())
                        return Response.BAD(String.format("Поле %s не должно быть пустым", field));

                    break;
                }
                case "number": {
                    String fieldValue = (String) fieldEntry.getValue();

                    if (notNullFields.contains(field) && fieldValue.isBlank())
                        return Response.BAD(String.format("Поле %s не должно быть пустым", field));

                    if (!NumberUtils.isNumber(fieldValue)) {
                        return Response.BAD(String.format("Поле %s должно быть числом", field));
                    }
                    break;
                }
                case "date": {
                    String fieldValue = (String) fieldEntry.getValue();

                    if (notNullFields.contains(field) && fieldValue.isBlank())
                        return Response.BAD(String.format("Поле %s не должно быть пустым", field));

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

                    if (notNullFields.contains(field) && fieldValue.isBlank())
                        return Response.BAD(String.format("Поле %s не должно быть пустым", field));

                    if (!Boolean.parseBoolean(fieldValue))
                        return Response.BAD(String.format("Поле %s имеет неверный формат Boolean", field));

                    break;
                }
                case "enum": {
                    //проверим, что есть такой тип enum-ов
                    Map<String, Object> complexField = (Map<String, Object>) fieldEntry.getValue();
                    if (!complexField.keySet().containsAll(Arrays.asList("enum_type", "id")))
                        return Response.BAD(String.format("Нерверная структура поля %s", field));

                    if (!enumTypeRepository.existsById(Long.valueOf(String.valueOf(complexField.get("enum_type")))))
                        return Response.BAD(String.format(
                                "Типа Enum-а с id =  %s не существует",
                                complexField.get("enum_type")));
                    if (!enumRepository.existsById(Long.valueOf(String.valueOf(complexField.get("id")))))
                        return Response.BAD(String.format(
                                "Enum-а с id =  %s не существует",
                                complexField.get("id")));

                    break;
                }
                case "phone": {
                    PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();

                    if (notNullFields.contains(field) && ((String) fieldEntry.getValue()).isBlank())
                        return Response.BAD(String.format("Поле %s не должно быть пустым", field));


                    if (!phoneNumberUtil.isPossibleNumber(((String) fieldEntry.getValue()), "RU"))
                        return Response.BAD("Неверный формат номера телефона");
                    break;
                }
                case "email": {
                    if (!GenericValidator.isEmail((String) fieldEntry.getValue()))
                        return Response.BAD("Неверный формат email");
                    break;
                }
                case "entity": {
                    System.out.println("entity");

                    Map<String, Object> complexField = (Map<String, Object>) fieldEntry.getValue();
                    if (!complexField.keySet().containsAll(Arrays.asList("entity_type", "id")))
                        return Response.BAD(String.format("Нерверная структура поля %s", field));

                    if (!entityTypeRepository.existsById(Long.valueOf(String.valueOf(complexField.get("enum_type")))))
                        return Response.BAD(String.format(
                                "Типа Entity с id =  %s не существует",
                                complexField.get("enum_type")));
                    if (!enumTypeRepository.existsById(Long.valueOf(String.valueOf(complexField.get("id")))))
                        return Response.BAD(String.format(
                                "Entity с id =  %s не существует",
                                complexField.get("id")));

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


        List<String> notNullValues = getNotNullFieldsOfEntityType(entity.getEntityType());

        for (Map.Entry<String, Object> stringObjectEntry : toUpdate.entrySet()) {

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

//    private boolean isFieldCorrect(Long entityTypeId, String fieldName, Object fieldValueToCheck) {
//        Optional<EntityTypeEntity> entityTypeEntityOptional = entityTypeRepository.findById(entityTypeId);
//        if (entityTypeEntityOptional.isEmpty())
//            throw new IllegalArgumentException("Не существует сущности такого типа! " +
//                    "Попытка получить ненулевые поля для сущности");
//
//        EntityTypeEntity entityTypeEntity = entityTypeEntityOptional.get();
//
//        String fieldTypeAtEntityType = (String) (
//                (Map) entityTypeEntity.getNecessaryFields().get(fieldName))
//                .get("type");
//
//        switch (fieldTypeAtEntityType) {
//            case "string": {
//                String fieldValue = (String) fieldEntry.getValue();
//                if (notNullFields.contains(field) && fieldValue.isBlank())
//                    return Response.BAD(String.format("Поле %s не должно быть пустым", field));
//
//                break;
//            }
//            case "number": {
//                String fieldValue = (String) fieldEntry.getValue();
//
//                if (notNullFields.contains(field) && fieldValue.isBlank())
//                    return Response.BAD(String.format("Поле %s не должно быть пустым", field));
//
//                if (!NumberUtils.isNumber(fieldValue)) {
//                    return Response.BAD(String.format("Поле %s должно быть числом", field));
//                }
//                break;
//            }
//            case "date": {
//                String fieldValue = (String) fieldEntry.getValue();
//
//                if (notNullFields.contains(field) && fieldValue.isBlank())
//                    return Response.BAD(String.format("Поле %s не должно быть пустым", field));
//
//                boolean isDate = false;
//                for (Locale availableLocale : Locale.getAvailableLocales()) {
//                    if (GenericValidator.isDate(fieldValue, availableLocale))
//                        isDate = true;
//                }
//                if (!isDate)
//                    return Response.BAD(String.format("Поле %s имеет неверный формат даты", field));
//                break;
//            }
//            case "boolean": {
//                String fieldValue = (String) fieldEntry.getValue();
//
//                if (notNullFields.contains(field) && fieldValue.isBlank())
//                    return Response.BAD(String.format("Поле %s не должно быть пустым", field));
//
//                if (!Boolean.parseBoolean(fieldValue))
//                    return Response.BAD(String.format("Поле %s имеет неверный формат Boolean", field));
//
//                break;
//            }
//            case "enum": {
//                //проверим, что есть такой тип enum-ов
//                Map<String, Object> complexField = (Map<String, Object>) fieldEntry.getValue();
//                if (!complexField.keySet().containsAll(Arrays.asList("enum_type", "id")))
//                    return Response.BAD(String.format("Нерверная структура поля %s", field));
//
//                if (!enumTypeRepository.existsById(Long.valueOf(String.valueOf(complexField.get("enum_type")))))
//                    return Response.BAD(String.format(
//                            "Типа Enum-а с id =  %s не существует",
//                            complexField.get("enum_type")));
//                if (!enumRepository.existsById(Long.valueOf(String.valueOf(complexField.get("id")))))
//                    return Response.BAD(String.format(
//                            "Enum-а с id =  %s не существует",
//                            complexField.get("id")));
//
//                break;
//            }
//            case "phone": {
//                PhoneNumberUtil phoneNumberUtil = PhoneNumberUtil.getInstance();
//
//                if (notNullFields.contains(field) && ((String) fieldEntry.getValue()).isBlank())
//                    return Response.BAD(String.format("Поле %s не должно быть пустым", field));
//
//
//                if (!phoneNumberUtil.isPossibleNumber(((String) fieldEntry.getValue()), "RU"))
//                    return Response.BAD("Неверный формат номера телефона");
//                break;
//            }
//            case "email": {
//                if (!GenericValidator.isEmail((String) fieldEntry.getValue()))
//                    return Response.BAD("Неверный формат email");
//                break;
//            }
//            case "entity": {
//                System.out.println("entity");
//
//                Map<String, Object> complexField = (Map<String, Object>) fieldEntry.getValue();
//                if (!complexField.keySet().containsAll(Arrays.asList("entity_type", "id")))
//                    return Response.BAD(String.format("Нерверная структура поля %s", field));
//
//                if (!entityTypeRepository.existsById(Long.valueOf(String.valueOf(complexField.get("enum_type")))))
//                    return Response.BAD(String.format(
//                            "Типа Entity с id =  %s не существует",
//                            complexField.get("enum_type")));
//                if (!enumTypeRepository.existsById(Long.valueOf(String.valueOf(complexField.get("id")))))
//                    return Response.BAD(String.format(
//                            "Entity с id =  %s не существует",
//                            complexField.get("id")));
//
//                break;
//            }
//        }
//
//    }
}
