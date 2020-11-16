package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.domain.EnumEntity;
import fi.lipp.greatheart.directory.domain.EnumTypeEntity;
import fi.lipp.greatheart.directory.dto.EnumDto;
import fi.lipp.greatheart.directory.repository.EnumRepository;
import fi.lipp.greatheart.directory.repository.EnumTypeRepository;
import fi.lipp.greatheart.directory.service.mappers.EnumMapper;
import fi.lipp.greatheart.directory.service.services.EnumService;
import fi.lipp.greatheart.directory.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnumServiceImpl implements EnumService {
    @Autowired
    EnumRepository enumRepository;
    @Autowired
    EnumTypeRepository enumTypeRepository;

    @Autowired
    EnumMapper mapper;

    @Override
    public Response<List<EnumEntity>> findEnumsByEnumTypeId(Long enumTypeId) {
        return Response.EXECUTE(() -> enumRepository.findAllByEnumTypeId(enumTypeId));
    }

    @Override
    public EnumDto findEnumById(Long enumId) {
        return enumRepository.findById(enumId)
                .map(x -> mapper.convert(x))
                .orElse(null);
    }

    @Override
    public Response<EnumEntity> save(EnumDto dto, Long typeId) {
        EnumEntity enumEntity = mapper.convert(dto);
        enumEntity.setEnumTypeId(typeId);

        Optional<EnumTypeEntity> entityType = enumTypeRepository.findById(typeId);
        if (entityType.isEmpty())
            return Response.BAD("Типа enum_type с id " + typeId + "не существует в базе.");

        //Проверяем, что нет значений enum в таком enum_type
        List<EnumEntity> enums = enumRepository.findAllByEnumTypeId(typeId);
        if (!enums.contains(enumEntity))
            return Response.EXECUTE(() -> enumRepository.save(enumEntity));
        else
            return Response.BAD("Такой enum уже существует в базе.");
    }

}
