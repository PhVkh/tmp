package fi.lipp.greatheart.directory.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
@Table(schema = "catalog", name = "contact")
public class ContactEntity {
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

    @Column(name = "subdivision")
    private String subdivision;

    @Column(name = "position")
    private String position;

    @Column(name = "comment")
    private String comment;

    @Column(name = "start_date_of_relevance")
    private java.sql.Date dateBegin;

    @Column(name = "end_date_of_relevance")
    private java.sql.Date dateEnd;

//    @OneToMany(mappedBy = "userId")
//    private List<OtherContactEntity> contacts;
//
//    @OneToMany(mappedBy = "owner")
//    private List<EmailEntity> emails;
//
//    @OneToMany(mappedBy = "owner")
//    private List<PhoneEntity> phones;
}
