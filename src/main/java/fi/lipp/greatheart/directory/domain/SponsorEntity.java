package fi.lipp.greatheart.directory.domain;

import fi.lipp.greatheart.directory.model.PartnerCategory;
import fi.lipp.greatheart.directory.model.PartnerType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;

// TODO : Финансовые поступления - блокчейн
@Data
@Entity
@Table(name = "sponsor")
public class SponsorEntity {
    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "name")
    String name;
    @Column(name = "category_id")
    PartnerCategory category;
    @Column(name = "registration_details")
    String regDetails;
    @Column(name = "payment_details")
    String paymentDetails;
    @Column(name = "contact_ids")
    @OneToMany
    List<ContactPersonEntity> contacts;
    @Column(name = "phone")
    private String phone;
    @Column(name = "email")
    private String email;
    @Column(name = "social_networks")
    private String socialNetworks;
    @Column(name = "site")
    private String website;
    @Column(name = "partner_type_id")
    private PartnerType type;
    @Column(name = "comment")
    private String commentary;
}

