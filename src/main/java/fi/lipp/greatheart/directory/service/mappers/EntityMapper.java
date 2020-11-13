package fi.lipp.greatheart.directory.service.mappers;

import fi.lipp.greatheart.directory.domain.EntityEntity;
import fi.lipp.greatheart.directory.domain.EntityTypeEntity;
import fi.lipp.greatheart.directory.domain.EnumEntity;
import fi.lipp.greatheart.directory.dto.EntityDto;
import fi.lipp.greatheart.directory.repository.EntityRepository;
import fi.lipp.greatheart.directory.repository.EntityTypeRepository;
import fi.lipp.greatheart.directory.repository.EnumRepository;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Mapper(componentModel = "spring")
public abstract class EntityMapper {
    @Autowired
    EnumRepository enumRepository;

    @Autowired
    EntityRepository entityRepository;

    @Autowired
    EntityTypeRepository entityTypeRepository;

    @Mapping(source = "json", target = "json", qualifiedByName = "complicatedJsonToSimple")
    public abstract EntityDto convert(EntityEntity entity);

    public abstract EntityEntity convert(EntityDto dto);


    @Named("complicatedJsonToSimple")
    public Map<String, Object> reformatJson(Map<String, Object> json) {
        //найдем вложенную сущность
        for (String key : json.keySet()) {
            if (json.get(key) instanceof HashMap) {
                HashMap<String, Object> structure = (HashMap<String, Object>) json.get(key);
                String type = (String) structure.get("type");

                //enum скрываем
                if (type.equals("enum")) {
                    Optional<EnumEntity> structureEntity = enumRepository.findById(
                            ((Integer) structure.get("id")).longValue());
                    if (structureEntity.isPresent()) {
                        String value = structureEntity.get().getName();
                        json.put(key, value);
                    } else {
                        //TODO : бросить эксепшн
                    }
                } else if (!type.equals("entity")) {
                    //TODO : бросить эксепшн
                }
            }
        }

        return json;
    }

    @AfterMapping
    void addTitle(EntityEntity entity, @MappingTarget EntityDto dto) {
        Optional<EntityTypeEntity> entityType = entityTypeRepository.findById(entity.getEntityType());
        entityType.ifPresent(entityTypeEntity -> dto.setTitle(entityTypeEntity.getTitleField()));
        //TODO : кинуть экспешн, если entityType не представлен
    }
}
