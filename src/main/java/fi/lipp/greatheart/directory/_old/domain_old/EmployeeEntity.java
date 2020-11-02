package fi.lipp.greatheart.directory._old.domain_old;

import fi.lipp.greatheart.directory._old.model_old.Availability;
import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity
@Table(name = "employee")
public class EmployeeEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "date_of_birth")
    private String dateOfBirth;

    @Column(name = "city_of_residence")
   private String city;

    @Column(name = "phone_personal")
    private String personalPhone;

    @Column(name = "email_personal")
    private String personalEmail;

    @Column(name = "messenger")
    private String messenger;

    @Column(name = "phone_work")
    private String workPhone;

    @Column(name = "email_work")
    private String workEmail;

    @Column(name = "place_of_work")
    private String workPlace;

    @Column(name = "position_at_work")
    private String workPosition;

    @Column(name = "position_at_fund")
    private String fundPosition;

    @Column(name = "area_of_responsibility")
    private String responsibilityArea;

    @Column(name = "education_university")
    private String educationUniversity;

    @Column(name = "education_specialty")
    private String educationSpecialization;

    @Column(name = "employee_availability_ids")
    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<Availability> availability;

    @Column(name = "availability_comment")
    private String availabilityDescription;

    @Column(name = "level_foreign_languages")
    private String languageProficiency;

    @Column(name = "hobbies")
    private String hobbies;

    @Column(name = "comment")
    private String commentary;
}
