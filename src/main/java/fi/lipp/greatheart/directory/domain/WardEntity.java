package fi.lipp.greatheart.directory.domain;

import fi.lipp.greatheart.directory.model.Condition;
import fi.lipp.greatheart.directory.model.Request;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

@Data
@Entity
@Table(name = "WARDS")
public class WardEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //    @NotBlank(message = "")
    private String name;
    private String dateOfBirth;
    //    @NotBlank(message = "")
    private Condition condition;
    //    @NotBlank(message = "")
    private String diagnosis;
    //    @NotBlank(message = "")
    private String personalPhone;
    private String personalPhone2;
    private String personalEmail;
    private String messenger;
    private String profession;
    private String hobbies;
    private String commentary;
    @ElementCollection
    private List<Request> requests;


}
