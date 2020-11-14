package fi.lipp.greatheart.directory.service.services;

import fi.lipp.greatheart.directory.dto.AuditDto;

public interface AuditService {

    void save(AuditDto dto);
}
