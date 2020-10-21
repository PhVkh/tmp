package fi.lipp.greatheart.directory.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "PARTNERS")
public class PartnerEntity {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    //    @NotBlank(message = "")
    private String name;
    //    @NotBlank(message = "")
    private PartnerType type;
    //    @NotBlank(message = "")
    private String registrationDetails;
    private String paymentDetails;
    private Image logo;
    //    @NotBlank(message = "")
    private List<ContactPerson> contacts;
    //    @NotBlank(message = "")
    private String phone;
    //    @NotBlank(message = "")
    private String email;
    private String socialNetworks;
    //    @NotBlank(message = "")
    private String website;
    //    @NotBlank(message = "")
    private ParthnershipSphere sphere;
    //    @NotBlank(message = "")
    private Category category;
    private String commentary;
}
