package fi.lipp.greatheart.directory.dto;

import fi.lipp.greatheart.directory.enums.Availability;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    private String firstName;
    private String lastName;
    private String middleName;
    private Date bd;
    private String city;
    private String workPlace;
    private String workPosition;
    private String fundPosition;
    private Set<Availability> availability;
    private List<EmailDto> emails;
    private List<PhoneDto> phones;
    private List<OtherContactDto> contacts;

}
