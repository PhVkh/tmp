package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.dto.WardDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WardService {
    List<WardDto> findAll(Pageable pageable);
    void save(WardDto ward);
}
