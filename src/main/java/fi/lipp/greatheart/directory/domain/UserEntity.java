package fi.lipp.greatheart.directory.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Entity
@Data
@Table(name = "user", schema = "catalog")
public class UserEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "date_of_birth")
    private Date bd;

    @Column(name = "city")
    private String city;

    @Column(name = "work_place")
    private String workPlace;

    @Column(name = "work_position")
    private String workPosition;

    @Column(name = "fund_position")
    private String fundPosition;

    @OneToMany(mappedBy = "userId")
    private List<OtherContactEntity> contacts;

    @OneToMany(mappedBy = "owner")
    private List<EmailEntity> emails;

    @OneToMany(mappedBy = "owner")
    private List<PhoneEntity> phones;

    @ElementCollection(targetClass = Availability.class)
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_availability", schema = "catalog", joinColumns = @JoinColumn(name = "user_id"))
    private Set<Availability> availability;

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}
