package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.domain.WardEntity;
import fi.lipp.greatheart.directory.dto.WardDto;
import fi.lipp.greatheart.directory.repository.WardsRepository;
import fi.lipp.greatheart.directory.service.mappers.WardMapper;
import fi.lipp.greatheart.directory.service.services.WardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class WardServiceImpl implements WardService {

    @Autowired
    WardsRepository repository;

    @Autowired
    WardMapper mapper;

    @Override
    public List<WardDto> findAll(Pageable pageable) {
        return repository.findAll(pageable)
                .stream().map(x -> mapper.convert(x))
                .collect(Collectors.toList());
    }

    public void save(WardDto dto) {
        WardEntity entity = mapper.convert(dto);
        repository.save(entity);
//        List<WardEntity> users = repository.findAll();
//        Hibernate.initialize(users);
//        System.out.println(users.size());
    }

}
