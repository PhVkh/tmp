package fi.lipp.greatheart.directory.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.lipp.greatheart.directory.domain.Role;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class UserDto {
    @JsonIgnore
    private Long id;
    private String firstName;
    private String lastName;
    private List<EmailDto> emails;
    private List<PhoneDto> phones;
    private Set<Role> roles;

    @Override
    public String toString() {
        return "UserDto{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
