package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.domain.EntityTypeEntity;
import fi.lipp.greatheart.directory.dto.EntityTypeDto;
import fi.lipp.greatheart.directory.repository.EntityTypeRepository;
import fi.lipp.greatheart.directory.service.mappers.EntityTypeMapper;
import fi.lipp.greatheart.directory.service.services.EntityTypeService;
import fi.lipp.greatheart.directory.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EntityTypeServiceImpl implements EntityTypeService {
    @Autowired
    EntityTypeRepository entityTypeRepository;

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


        //Проверим, что есть title и что значение title есть  necessary_fields
        if (dto.getMain() == null)
            return Response.BAD("Не проставлено значение : является ли сущность  главной или вспомогательной.");

        String title = dto.getTitleField();
        if (title == null || title.isBlank())
            return Response.BAD("Необходимо передать главное поле");

//        if (dto.getNecessaryFields() == null)
//            dto.setNecessaryFields(Collections.singletonList(
//                    StringUtil.capitalize(title.strip())));
//
//        List<String> fields = dto.getNecessaryFields().stream().map(
//                field -> StringUtil.capitalize(field.strip()))
//                .collect(Collectors.toList());
//        if (fields.size() == 0 || !fields.contains(StringUtil.capitalize(title.strip())))
//            dto.getNecessaryFields().add(title);
//
//        dto.setTitleField(StringUtil.capitalize(dto.getTitleField().strip()));
//        dto.setNecessaryFields(dto.getNecessaryFields().stream().map(
//                field -> StringUtil.capitalize(field.strip()))
//                .collect(Collectors.toList()));
//        dto.setName(StringUtil.capitalize(dto.getName().strip()));

        return Response.EXECUTE(() -> entityTypeRepository.save(mapper.convert(dto)));
    }

    @Override
    public EntityTypeDto findById(Long entityTypeId) {
        Optional<EntityTypeEntity> entityType = entityTypeRepository.findById(entityTypeId);
        return entityType.map(x -> mapper.convert(x)).orElse(null);
    }

}
