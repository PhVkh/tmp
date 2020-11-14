package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.domain.EnumTypeEntity;
import fi.lipp.greatheart.directory.dto.EnumTypeDto;
import fi.lipp.greatheart.directory.repository.EnumTypeRepository;
import fi.lipp.greatheart.directory.service.mappers.EnumTypeMapper;
import fi.lipp.greatheart.directory.service.services.EnumTypeService;
import fi.lipp.greatheart.directory.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EnumTypeServiceImpl implements EnumTypeService {
    @Autowired
    EnumTypeRepository repository;
    @Autowired
    EnumTypeMapper mapper;

    @Override
    public Response<List<EnumTypeDto>> findAllEnumTypes() {
        return Response.EXECUTE(() -> repository.findAll()
                .stream().map(x -> mapper.convert(x))
                .collect(Collectors.toList()));
    }

    @Override
    public Response<EnumTypeDto> findEnumTypeById(Long id) {
        Optional<EnumTypeEntity> enumTypeEntity = repository.findById(id);
        return Response.EXECUTE(() ->
                enumTypeEntity.map(typeEntity -> mapper.convert(typeEntity)).orElse(null));
    }

    /**
     * Метод сохранения типа enum-а в базу c проверками :
     * на неповторение имени сущности  + на наличие имени сущности
     *
     * @param dto dto сущности типа enum-а
     * @return Ответ об успешном/неуспешном сохранении сущности
     */
    @Override
    public Response<EnumTypeEntity> save(EnumTypeDto dto) {
        //Проверим, что в dto есть name
        if (dto.getName() == null || dto.getName().isBlank())
            return Response.BAD("Имя сущности не может быть пустым");

        //Проверим, что нет enum_type  с таким же именем
        List<EnumTypeEntity> enumTypes = repository.findAll();
        for (EnumTypeEntity enumType : enumTypes) {
            if (enumType.getName().
                    toLowerCase().strip().equals(
                    dto.getName().toLowerCase().strip()))
                return Response.BAD("Enum_type c именем " + dto.getName() + " уже существует.");
        }

        return Response.EXECUTE(() -> repository.save(
                mapper.convert(dto)));
    }

}
