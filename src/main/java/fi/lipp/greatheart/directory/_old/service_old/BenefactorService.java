package fi.lipp.greatheart.directory._old.service_old;

import fi.lipp.greatheart.directory._old.service_old.dto.BenefactorDto;
import fi.lipp.greatheart.directory._old.service_old.exceptions.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BenefactorService {

    void save(BenefactorDto dto);
    List<BenefactorDto> findAll(Pageable pageable);
    void update(BenefactorDto dto) throws EntityNotFoundException;
    void delete(Long id) throws EntityNotFoundException;
}
