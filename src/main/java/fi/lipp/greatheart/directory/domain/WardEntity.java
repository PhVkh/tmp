package fi.lipp.greatheart.directory.domain;

import fi.lipp.greatheart.directory.model.Condition;
import fi.lipp.greatheart.directory.model.Request;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

//TODO : запросы - блокчейн

@Data
@Entity
@Table(name = "ward")
public class WardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "date_of_birth")
    private String dateOfBirth;
    @Column(name = "health_status_id")
    @Enumerated(EnumType.STRING)
    private Condition condition;
    @Column(name = "diagnosis")
    private String diagnosis;
    @Column(name = "phone_main")
    private String personalPhone;
    @Column(name = "phone_backup")
    private String backupPhone;
    @Column(name = "email")
    private String personalEmail;
    @Column(name = "messenger")
    private String messenger;
    @Column(name = "profession")
    private String profession;
    @Column(name = "hobbies")
    private String hobbies;
    @Column(name = "comment")
    private String commentary;
//    @ElementCollection
//    private List<Request> requests;


}
