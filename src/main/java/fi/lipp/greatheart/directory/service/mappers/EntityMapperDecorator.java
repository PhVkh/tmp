package fi.lipp.greatheart.directory.service.mappers;

import fi.lipp.greatheart.directory.domain.EntityEntity;
import fi.lipp.greatheart.directory.dto.EntityDto;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

public abstract class EntityMapperDecorator implements EntityMapper {

    @Autowired
    EntityMapper delegate;

    @Override
    public EntityDto convert(EntityEntity entity) {
        EntityDto dto = delegate.convert(entity);

        //проверим, есть ли enum-ы
        for (String key : dto.getJson().keySet()) {
            if (dto.getJson().get(key) instanceof HashMap) {
                java.util.Map<String, Object> map = dto.getJson();
                map.put(key, "!!!!!!!!");
            }
        }
        return dto;

    }
}
