package fi.lipp.greatheart.directory._old.service_old.mapper;

import fi.lipp.greatheart.directory._old.domain_old.BenefactorEntity;
import fi.lipp.greatheart.directory._old.service_old.dto.BenefactorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BenefactorMapper {
    BenefactorDto convert(BenefactorEntity entity);
    BenefactorEntity convert(BenefactorDto dto);
}
