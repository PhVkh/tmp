package fi.lipp.greatheart.directory.service.impl;

import fi.lipp.greatheart.directory.dto.AuditDto;
import fi.lipp.greatheart.directory.repository.AuditRepository;
import fi.lipp.greatheart.directory.service.mappers.AuditMapper;
import fi.lipp.greatheart.directory.service.services.AuditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired private AuditRepository repository;
    @Autowired private AuditMapper mapper;

    @Override
    public void save(AuditDto auditDto) {
        repository.save(mapper.convert(auditDto));
    }
}
