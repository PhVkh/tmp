package fi.lipp.greatheart.directory._old.domain_old;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Data
@Entity
@Table(name = "contact")
public class ContactPersonEntity {
    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
//    @Column(name = "phone")
//    private String phone;
    @Column(name = "messenger")
    private String messenger;
    @Column(name = "email")
    private String email;
    @Column(name = "subdivision")
    private String subdivision;
//    @Column(name = "comment")
//    private String comment;
    @Column(name = "start_date_of_relevance")
    private Date start;
    @Column(name = "end_date_of_relevance")
    private Date end;
//    @Column(name = "comment")
//    private String commentary;
}
