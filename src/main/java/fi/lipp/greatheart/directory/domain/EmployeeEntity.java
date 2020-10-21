package fi.lipp.greatheart.directory.domain;

import fi.lipp.greatheart.directory.model.Availability;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "EMPLOYEES")
public class EmployeeEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
//    @NotBlank(message = "")
    private String name;
//    @NotBlank(message = "")
    private String dateOfBirth;
//    @NotBlank(message = "")
    private String city;
//    @NotBlank(message = "")
    private String personalPhone;
    private String personalEmail;
    private String messenger;
    private String workPhone;
    private String workEmail;
//    @NotBlank(message = "")
    private String workPlace;
//    @NotBlank(message = "")
    private String workPosition;
//    @NotBlank(message = "")
    private String fundPosition;
    private String responsibilityArea;
    private String educationUniversity;
    private String educationSpecialization;
//    @NotBlank(message = "")
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<Availability> availability;
    private String availabilityDescription;
    private String languageProficiency;
    private String hobbies;
    private String commentary;
}
