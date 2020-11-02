package fi.lipp.greatheart.directory.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.List;

@Data
public class UserDto {
    @JsonIgnore
    private Long id;
    private String firstName;
    private String lastName;
    private List<EmailDto> emails;
    private List<PhoneDto> phones;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
