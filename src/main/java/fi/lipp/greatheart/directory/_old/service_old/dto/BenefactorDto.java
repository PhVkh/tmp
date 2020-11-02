package fi.lipp.greatheart.directory._old.service_old.dto;

import lombok.Data;

@Data
public class BenefactorDto {
    private Long id;
    private String name;
    private String dateOfBirth;
    private String registration_details;
    private String payment_details;
    private String email;
    private String phone;
    private String site;
    private String social_networks;
    private String comment;
    private Long categoryId;
}
