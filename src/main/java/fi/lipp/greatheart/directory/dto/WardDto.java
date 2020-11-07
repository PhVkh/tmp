package fi.lipp.greatheart.directory.dto;

import fi.lipp.greatheart.directory.enums.HealthStatus;
import lombok.Data;

import java.sql.Date;
import java.util.List;

@Data
public class WardDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private Date bd;
    private String diagnosis;
    private HealthStatus healthStatus;
    private List<EmailDto> emails;
    private List<PhoneDto> phones;
    private List<OtherContactDto> contacts;
}
