package fi.lipp.greatheart.directory.domain;

import fi.lipp.greatheart.directory.enums.HealthStatus;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(schema = "catalog", name = "wards")
public class WardEntity {
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

    @Column(name = "diagnosis")
    private String diagnosis;

    @OneToMany(mappedBy = "userId")
    private List<OtherContactEntity> contacts;

    @OneToMany(mappedBy = "owner")
    private List<EmailEntity> emails;

    @OneToMany(mappedBy = "owner")
    private List<PhoneEntity> phones;

    @Enumerated(EnumType.STRING)
    private HealthStatus healthStatus;

}
