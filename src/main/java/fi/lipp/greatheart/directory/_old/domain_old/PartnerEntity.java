package fi.lipp.greatheart.directory._old.domain_old;

import fi.lipp.greatheart.directory._old.model_old.ParthnershipSphere;
import fi.lipp.greatheart.directory._old.model_old.PartnerCategory;
import fi.lipp.greatheart.directory._old.model_old.PartnerType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

//TODO : логотип

@Data
@Entity
@Table(name = "partner")
public class PartnerEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "partner_type_id")
    private PartnerType type;
    @Column(name = "registration_details")
    private String registrationDetails;
    @Column(name = "payment_details")
    private String paymentDetails;
    //private Image logo;
    @Column(name = "contact_ids")
    @OneToMany
    private List<ContactPersonEntity> contacts;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "social_networks")
    private String socialNetworks;
    @Column(name = "site")
    private String website;
    @ElementCollection
    @Column(name = "sphere_of_partnership_ids")
    private List<ParthnershipSphere> sphere;
    @Column(name = "category_id")
    private PartnerCategory category;
    @Column(name = "comment")
    private String commentary;
}
