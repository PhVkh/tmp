package fi.lipp.greatheart.directory.dto;

import fi.lipp.greatheart.directory.enums.Category;
import fi.lipp.greatheart.directory.enums.LegalType;
import fi.lipp.greatheart.directory.enums.PartnershipType;
import lombok.Data;

@Data
public class PartnersDto {
    String name;
    LegalType legalType;
    String registrationDetails;
    String paymentDetails;
    PartnershipType partnershipType;
    Category category;
    String comment;


}
