package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.dto.EnumDto;
import fi.lipp.greatheart.directory.repository.EnumRepository;
import fi.lipp.greatheart.directory.service.mappers.EnumMapper;
import fi.lipp.greatheart.directory.service.services.EnumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnumServiceImpl implements EnumService {
    @Autowired
    EnumRepository repository;

    @Autowired
    EnumMapper mapper;

    @Override
    public List<EnumDto> findEnumsByEnumTypeId(Long enumTypeId) {
        return repository.findAllByEnumTypeId(enumTypeId).stream()
                .map(x -> mapper.convert(x))
                .collect(Collectors.toList());
    }


    @Override
    public EnumDto findEnumById(Long enumId) {
        return repository.findById(enumId)
                .map(x -> mapper.convert(x))
                .orElse(null);
    }

    @Override
    public void save(EnumDto dto, Long typeId) {
        repository.save(mapper.convert(dto));
    }

}
