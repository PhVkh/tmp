package fi.lipp.greatheart.directory.service;

import fi.lipp.greatheart.directory.service.dto.BenefactorDto;
import fi.lipp.greatheart.directory.service.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BenefactorService {

    void save(BenefactorDto dto);
    List<BenefactorDto> findAll(Pageable pageable);
    void update(BenefactorDto dto) throws EntityNotFoundException;
    void delete(Long id) throws EntityNotFoundException;
}
