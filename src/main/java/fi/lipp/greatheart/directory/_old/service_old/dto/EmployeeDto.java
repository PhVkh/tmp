package fi.lipp.greatheart.directory._old.service_old.dto;

import fi.lipp.greatheart.directory._old.model_old.Availability;
import lombok.Data;

import java.util.Set;

@Data
public class EmployeeDto {
    private Long id;
    private String name;
    private String dateOfBirth;
    private String city;
    private String personalPhone;
    private String personalEmail;
    private String messenger;
    private String workPhone;
    private String workEmail;
    private String workPlace;
    private String workPosition;
    private String fundPosition;
    private String responsibilityArea;
    private String educationUniversity;
    private String educationSpecialization;
    private Set<Availability> availability;
    private String availabilityDescription;
    private String languageProficiency;
    private String hobbies;
    private String commentary;
}
