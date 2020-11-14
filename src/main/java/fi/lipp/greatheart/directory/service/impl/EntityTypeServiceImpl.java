package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.domain.EntityTypeEntity;
import fi.lipp.greatheart.directory.dto.EntityTypeDto;
import fi.lipp.greatheart.directory.repository.EntityTypeRepository;
import fi.lipp.greatheart.directory.repository.FieldTypeRepository;
import fi.lipp.greatheart.directory.service.mappers.EntityTypeMapper;
import fi.lipp.greatheart.directory.service.services.EntityTypeService;
import fi.lipp.greatheart.directory.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntityTypeServiceImpl implements EntityTypeService {
    @Autowired
    EntityTypeRepository entityTypeRepository;
    @Autowired
    FieldTypeRepository fieldTypeRepository;

    @Autowired
    EntityTypeMapper mapper;

    @Override
    public List<EntityTypeDto> findAll(Pageable pageable) {
        return entityTypeRepository.findAll(pageable)
                .stream().map(x -> mapper.convert(x))
                .collect(Collectors.toList());
    }

    @Override
    public EntityTypeDto findByName(String entityTypeName) {
        Optional<EntityTypeEntity> entityType = entityTypeRepository.findByName(entityTypeName);
        return entityType.map(x -> mapper.convert(x)).orElse(null);
    }

    @Override
    public List<EntityTypeDto> findMainEntities() {
        return entityTypeRepository.findByMainTrue()
                .stream().map(x -> mapper.convert(x))
                .collect(Collectors.toList());
    }

    @Override
    public Response<EntityTypeEntity> save(EntityTypeDto dto) {
        //Проверим, что имя сущности ненулевое
        if (dto.getName() == null || dto.getName().isBlank())
            return Response.BAD("Имя сущности не может быть пустям значением.");
        List<String> entityTypeNames = entityTypeRepository.findAll().stream().map(EntityTypeEntity::getName)
                .map(name -> name.strip().toLowerCase()).
                        collect(Collectors.toList());

        if (entityTypeNames.contains(dto.getName().strip().toLowerCase()))
            return Response.BAD(String.format("Имя сущности %s уже существует.", dto.getName().strip()));

        if (dto.getMain() == null)
            return Response.BAD("Не проставлено значение : является ли сущность  главной или вспомогательной.");

        //Проверим, что есть title
        if (dto.getTitleField() == null)
            return Response.BAD("Title не может быть null");

        //Проверим, что есть necessaryFields
        if (dto.getNecessaryFields() == null)
            return Response.BAD("Тип сущности должен иметь список полей - не может быть null");

        //проверим, что каждое поле имеет знаечние notnull и тип. Причем тип - известен
        for (Map.Entry<String, Object> field : dto.getNecessaryFields().entrySet()) {
            if (field.getValue() == null || !(field.getValue() instanceof Map))
                return Response.BAD(String.format("Поле %s  должно иметь описание", field.getKey()));

            Map json = (Map) field.getValue();

            //проверим, что есть type и notnull
            if (!json.containsKey("type") || !json.containsKey("notnull") || json.containsValue(null))
                return Response.BAD(String.format("Неправильная структура поля %s", field.getKey()));

            String type = (String) json.get("type");
            if (!fieldTypeRepository.existsByType(type))
                return Response.BAD(String.format("Тип %s сущности %s не существует в базу", type, field.getKey()));
        }

        String title = dto.getTitleField();
        if (title == null || title.isBlank())
            return Response.BAD("Необходимо передать главное поле");


        return Response.EXECUTE(() -> entityTypeRepository.save(mapper.convert(dto)));
    }

    @Override
    public EntityTypeDto findById(Long entityTypeId) {
        Optional<EntityTypeEntity> entityType = entityTypeRepository.findById(entityTypeId);
        return entityType.map(x -> mapper.convert(x)).orElse(null);
    }

}
