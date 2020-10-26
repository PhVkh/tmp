package fi.lipp.greatheart.directory.service.mapper;

import fi.lipp.greatheart.directory.domain.BenefactorEntity;
import fi.lipp.greatheart.directory.service.dto.BenefactorDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BenefactorMapper {
    BenefactorDto convert(BenefactorEntity entity);
    BenefactorEntity convert(BenefactorDto dto);
}
