package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.domain.BenefactorEntity;
import fi.lipp.greatheart.directory.repository.BenefactorRepository;
import fi.lipp.greatheart.directory.service.BenefactorService;
import fi.lipp.greatheart.directory.service.dto.BenefactorDto;
import fi.lipp.greatheart.directory.service.exceptions.EntityNotFoundException;
import fi.lipp.greatheart.directory.service.mapper.BenefactorMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BenefactorServiceImpl implements BenefactorService {
    @Autowired
    private BenefactorRepository repository;

    @Autowired
    private BenefactorMapper mapper;

    public void save(BenefactorDto dto) {
        repository.save(mapper.convert(dto));
    }

    public List<BenefactorDto> findAll(Pageable pageable) {
        return repository.findAll(pageable).stream().map(mapper::convert).collect(Collectors.toList());
    }

    public void update(BenefactorDto benefactorDto) throws EntityNotFoundException {
        if (repository.findById(benefactorDto.getId()).isPresent()) {
            repository.save(mapper.convert(benefactorDto));
        } else {
            throw new EntityNotFoundException();
        }
    }

    public void delete(Long id) throws EntityNotFoundException {
        Optional<BenefactorEntity> entity = repository.findById(id);
        if (entity.isPresent()) {
            entity.ifPresent(repository::delete);
        } else {
            throw new EntityNotFoundException();
        }
    }
}
