package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.domain.EnumTypeEntity;
import fi.lipp.greatheart.directory.dto.EnumTypeDto;
import fi.lipp.greatheart.directory.repository.EnumTypeRepository;
import fi.lipp.greatheart.directory.service.mappers.EnumTypeMapper;
import fi.lipp.greatheart.directory.service.services.EnumTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnumTypeServiceImpl implements EnumTypeService {
    @Autowired
    EnumTypeRepository repository;
    @Autowired
    EnumTypeMapper mapper;

    @Override
    public List<EnumTypeDto> findAllEnumTypes() {
        return null;
    }

    @Override
    public EnumTypeDto findEnumTypeById(Long id) {
        Optional<EnumTypeEntity> enumTypeEntity = repository.findById(id);
        return enumTypeEntity.map(typeEntity -> mapper.convert(typeEntity)).orElse(null);
    }

}
